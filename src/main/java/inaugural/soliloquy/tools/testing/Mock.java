package inaugural.soliloquy.tools.testing;

import soliloquy.specs.common.persistence.PersistentValuesHandler;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.common.shared.HasId;
import soliloquy.specs.common.valueobjects.Pair;

import java.util.*;
import java.util.function.Function;

import static inaugural.soliloquy.tools.collections.Collections.listOf;
import static inaugural.soliloquy.tools.collections.Collections.mapOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class Mock {
    public static <T extends HasId> T generateMockWithId(Class<T> clazz, String id) {
        var mock = mock(clazz);
        lenient().when(mock.id()).thenReturn(id);
        return mock;
    }

    @SafeVarargs
    public static <T> List<T> generateMockList(T... values) {
        //noinspection unchecked
        var mockList = (List<T>) mock(List.class);
        lenient().when(mockList.size()).thenReturn(values.length);
        //noinspection SuspiciousMethodCalls
        lenient().when(mockList.contains(any())).thenAnswer(
                invocationOnMock -> Arrays.stream(values)
                        .anyMatch(item -> item == invocationOnMock.getArgument(0)));
        lenient().when(mockList.iterator())
                .thenAnswer(invocationOnMock -> listOf(values).iterator());
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
                .thenAnswer(invocationOnMock -> generateMockMapEntrySet(keyValuePairs));
        lenient().doCallRealMethod().when(mockMap).forEach(any());
        //noinspection unchecked
        lenient().when(mockMap.get((K) any())).thenAnswer(
                invocationOnMock -> map.get((K) invocationOnMock.getArgument(0)));
        return mockMap;
    }

    @SafeVarargs
    public static <V> Function<String, V> generateMockLookupFunction(Pair<String, V>... items) {
        //noinspection unchecked
        var lookupFunction = (Function<String, V>) mock(Function.class);
        lenient().when(lookupFunction.apply(anyString())).thenReturn(null);
        for (Pair<String, V> item : items) {
            lenient().when(lookupFunction.apply(item.getItem1())).thenReturn(item.getItem2());
        }
        return lookupFunction;
    }

    @SafeVarargs
    private static <K, V> Set<Map.Entry<K, V>> generateMockMapEntrySet(
            Pair<K, V>... keyValuePairs) {
        var entryList = new ArrayList<Map.Entry<K, V>>();
        for (var keyValuePair : keyValuePairs) {
            entryList.add(new AbstractMap.SimpleEntry<>(
                    keyValuePair.getItem1(),
                    keyValuePair.getItem2()));
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
            lenient().when(mockHandler.read(value.getItem1())).thenReturn(value.getItem2());
            lenient().when(mockHandler.write(value.getItem2())).thenReturn(value.getItem1());
        }

        return mockHandler;
    }

    /** @noinspection rawtypes */
    public static Pair<PersistentValuesHandler, Map<String, TypeHandler>> generateMockPersistentValuesHandlerWithSimpleHandlers(
            Object[]... valueSets) {
        var mockPersistentValuesHandler = mock(PersistentValuesHandler.class);
        var handlers = new HashMap<String, TypeHandler>();

        for (var valueSet : valueSets) {
            var type = valueSet[0].getClass().getCanonicalName();

            var typeHandler = generateSimpleMockTypeHandler(valueSet);

            lenient().when(mockPersistentValuesHandler.getTypeHandler(type))
                    .thenReturn(typeHandler);
            handlers.put(type, typeHandler);
        }

        return Pair.of(mockPersistentValuesHandler, handlers);
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
        var mockEntities = new HashMap<String, T>();

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
}
