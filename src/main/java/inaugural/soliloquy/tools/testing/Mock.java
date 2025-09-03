package inaugural.soliloquy.tools.testing;

import inaugural.soliloquy.tools.collections.Collections;
import soliloquy.specs.common.persistence.PersistenceHandler;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.common.shared.HasId;
import soliloquy.specs.common.valueobjects.Pair;
import soliloquy.specs.io.graphics.renderables.providers.StaticProvider;

import java.util.*;
import java.util.function.Function;

import static inaugural.soliloquy.tools.collections.Collections.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static soliloquy.specs.common.valueobjects.Pair.pairOf;

public class Mock {
    public static <T extends HasId> T generateMockWithId(Class<T> clazz, String id) {
        var mock = mock(clazz);
        lenient().when(mock.id()).thenReturn(id);
        return mock;
    }

    @SafeVarargs
    public static <T> Set<T> generateMockSet(T... values) {
        //noinspection unchecked
        var mockSet = (Set<T>) mock(Set.class);
        lenient().when(mockSet.size()).thenReturn(values.length);
        //noinspection SuspiciousMethodCalls
        lenient().when(mockSet.contains(any())).thenAnswer(
                invocation -> Arrays.stream(values)
                        .anyMatch(item -> item == invocation.getArgument(0)));
        lenient().when(mockSet.iterator())
                .thenAnswer(_ -> setOf(values).iterator());
        lenient().doCallRealMethod().when(mockSet).forEach(any());

        return mockSet;
    }

    @SafeVarargs
    public static <T> List<T> generateMockList(T... values) {
        //noinspection unchecked
        var mockList = (List<T>) mock(List.class);
        lenient().when(mockList.size()).thenReturn(values.length);
        lenient().when(mockList.get(anyInt()))
                .thenAnswer(invocation -> values[(int) invocation.getArgument(0)]);
        //noinspection SuspiciousMethodCalls
        lenient().when(mockList.contains(any())).thenAnswer(
                invocation -> Arrays.stream(values)
                        .anyMatch(item -> item == invocation.getArgument(0)));
        lenient().when(mockList.iterator())
                .thenAnswer(_ -> listOf(values).iterator());
        lenient().doCallRealMethod().when(mockList).forEach(any());

        return mockList;
    }

    @SafeVarargs
    public static <K, V> Map<K, V> generateMockMap(Pair<K, V>... keyValuePairs) {
        var map = mapOf(keyValuePairs);
        //noinspection unchecked
        var mockMap = (Map<K, V>) mock(Map.class);
        lenient().when(mockMap.size()).thenReturn(keyValuePairs.length);
        lenient().when(mockMap.entrySet())
                .thenAnswer(_ -> generateMockMapEntrySet(keyValuePairs));
        var keys = Collections.<K>setOf();
        var values = Collections.<V>setOf();
        for (var pair : keyValuePairs) {
            keys.add(pair.FIRST);
            values.add(pair.SECOND);
        }
        lenient().when(mockMap.keySet()).thenAnswer(_ -> keys);
        lenient().when(mockMap.values()).thenAnswer(_ -> values);
        lenient().doCallRealMethod().when(mockMap).forEach(any());
        //noinspection unchecked
        lenient().when(mockMap.get((K) any())).thenAnswer(
                invocation -> map.get((K) invocation.getArgument(0)));
        return mockMap;
    }

    @SafeVarargs
    public static <V> Function<String, V> generateMockLookupFunction(Pair<String, V>... items) {
        //noinspection unchecked
        var lookupFunction = (Function<String, V>) mock(Function.class);
        lenient().when(lookupFunction.apply(anyString())).thenReturn(null);
        for (Pair<String, V> item : items) {
            lenient().when(lookupFunction.apply(item.FIRST)).thenReturn(item.SECOND);
        }
        return lookupFunction;
    }

    @SafeVarargs
    public static <V extends HasId> Function<String, V> generateMockLookupFunctionWithId(
            V... items) {
        //noinspection unchecked
        var lookupFunction = (Function<String, V>) mock(Function.class);
        lenient().when(lookupFunction.apply(anyString())).thenReturn(null);
        for (V item : items) {
            lenient().when(lookupFunction.apply(item.id())).thenReturn(item);
        }
        return lookupFunction;
    }

    public static <V extends HasId> LookupAndEntitiesWithId<V> generateMockLookupFunctionWithId(
            Class<V> clazz, String... ids) {
        var entities = new ArrayList<V>();
        //noinspection unchecked
        var lookupFunction = (Function<String, V>) mock(Function.class);
        lenient().when(lookupFunction.apply(anyString())).thenReturn(null);
        for (var id : ids) {
            var entity = mock(clazz);
            lenient().when(entity.id()).thenReturn(id);
            entities.add(entity);
            lenient().when(lookupFunction.apply(id)).thenReturn(entity);
        }
        var result = new LookupAndEntitiesWithId<V>();
        result.lookup = lookupFunction;
        result.entities = entities;
        return result;
    }

    public static class LookupAndEntitiesWithId<T> {
        public Function<String, T> lookup;
        public List<T> entities;
    }

    @SafeVarargs
    private static <K, V> Set<Map.Entry<K, V>> generateMockMapEntrySet(
            Pair<K, V>... keyValuePairs) {
        var entryList = new ArrayList<Map.Entry<K, V>>();
        for (var keyValuePair : keyValuePairs) {
            entryList.add(new AbstractMap.SimpleEntry<>(
                    keyValuePair.FIRST,
                    keyValuePair.SECOND));
        }
        //noinspection unchecked
        var mockSet = (Set<Map.Entry<K, V>>) mock(Set.class);
        lenient().when(mockSet.iterator()).thenReturn(entryList.iterator());
        lenient().doCallRealMethod().when(mockSet).forEach(any());
        return mockSet;
    }

    public static <T> TypeHandler<T> generateSimpleMockTypeHandler(T[] values) {
        //noinspection unchecked
        var mockHandler = (TypeHandler<T>) mock(TypeHandler.class);

        for (var value : values) {
            lenient().when(mockHandler.write(value)).thenReturn(value.toString());
            lenient().when(mockHandler.read(value.toString())).thenReturn(value);
        }

        return mockHandler;
    }

    @SafeVarargs
    public static <T> TypeHandler<T> generateSimpleMockTypeHandler(Pair<String, T>... values) {
        //noinspection unchecked
        var mockHandler = (TypeHandler<T>) mock(TypeHandler.class);

        for (var value : values) {
            lenient().when(mockHandler.read(value.FIRST)).thenReturn(value.SECOND);
            lenient().when(mockHandler.write(value.SECOND)).thenReturn(value.FIRST);
        }

        return mockHandler;
    }

    /** @noinspection rawtypes */
    public static Pair<PersistenceHandler, Map<String, TypeHandler>> generateMockPersistenceHandlerWithSimpleHandlers(
            Object[]... valueSets) {
        var mockPersistenceHandler = mock(PersistenceHandler.class);
        var handlers = new HashMap<String, TypeHandler>();

        for (var valueSet : valueSets) {
            var type = valueSet[0].getClass().getCanonicalName();

            var typeHandler = generateSimpleMockTypeHandler(valueSet);

            lenient().when(mockPersistenceHandler.getTypeHandler(type))
                    .thenReturn(typeHandler);
            handlers.put(type, typeHandler);
        }

        return pairOf(mockPersistenceHandler, handlers);
    }

    public static <T> HandlerAndEntity<T> generateMockEntityAndHandler(Class<T> clazz,
                                                                       String writtenValue) {
        var mockEntity = mock(clazz);
        //noinspection unchecked
        var mockHandler = (TypeHandler<T>) mock(TypeHandler.class);

        lenient().when(mockHandler.read(anyString())).thenReturn(mockEntity);
        lenient().when(mockHandler.read(null)).thenReturn(mockEntity);
        lenient().when(mockHandler.write(any())).thenReturn(writtenValue);

        return new HandlerAndEntity<>(mockEntity, mockHandler);
    }

    public static class HandlerAndEntity<T> {
        public T entity;
        public TypeHandler<T> handler;

        public HandlerAndEntity(T entity, TypeHandler<T> handler) {
            this.entity = entity;
            this.handler = handler;
        }
    }

    public static <T> HandlerAndEntities<T> generateMockHandlerAndEntities(Class<T> clazz,
                                                                           String[] writtenValues) {
        //noinspection unchecked
        var mockHandler = (TypeHandler<T>) mock(TypeHandler.class);
        Map<String, T> mockEntities = mapOf();

        for (var writtenValue : writtenValues) {
            var mockEntity = mock(clazz);
            lenient().when(mockHandler.read(writtenValue)).thenReturn(mockEntity);
            lenient().when(mockHandler.write(mockEntity)).thenReturn(writtenValue);
            mockEntities.put(writtenValue, mockEntity);
        }

        return new HandlerAndEntities<>(mockHandler, mockEntities);
    }

    public static class HandlerAndEntities<T> {
        public TypeHandler<T> handler;
        public Map<String, T> entities;

        public HandlerAndEntities(TypeHandler<T> handler, Map<String, T> entities) {
            this.handler = handler;
            this.entities = entities;
        }
    }

    @SafeVarargs
    public static <T> void hydrateMockHandler(TypeHandler<T> mockHandler, Pair<T, String>... vals) {
        for (var val : vals) {
            lenient().when(mockHandler.write(val.FIRST)).thenReturn(val.SECOND);
            lenient().when(mockHandler.read(val.SECOND)).thenReturn(val.FIRST);
        }
    }

    public static <T> StaticProvider<T> generateMockStaticProvider(T val) {
        @SuppressWarnings("unchecked")
        var provider = (StaticProvider<T>) mock(StaticProvider.class);
        lenient().when(provider.provide(anyLong())).thenReturn(val);
        return provider;
    }
}
