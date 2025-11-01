package inaugural.soliloquy.tools.tests.testing;

import org.junit.jupiter.api.Test;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.common.shared.HasId;
import soliloquy.specs.common.shared.HasUuid;
import soliloquy.specs.gamestate.entities.Item;
import soliloquy.specs.ruleset.entities.ItemType;

import java.util.ArrayList;

import static inaugural.soliloquy.tools.random.Random.*;
import static inaugural.soliloquy.tools.testing.Mock.*;
import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static soliloquy.specs.common.valueobjects.Pair.pairOf;

public class MockTests {
    @Test
    public void testGenerateMockWithId() {
        var id = "Id";

        var mockItem = generateMockWithId(ItemType.class, id);

        assertNotNull(mockItem);
        assertEquals(id, mockItem.id());
    }

    @Test
    public void testGenerateMockSet() {
        var mockSet = generateMockSet(1, 2, 3);

        assertNotNull(mockSet);
        assertEquals(3, mockSet.size());
        assertTrue(mockSet.contains(1));
        assertTrue(mockSet.contains(2));
        assertTrue(mockSet.contains(3));
        assertFalse(mockSet.contains(4));
        verify(mockSet, times(4)).contains(anyInt());
        var collector = new ArrayList<Integer>();
        // NB: Calling forEach twice here to ensure that multiple iteration is possible
        mockSet.forEach(_ -> {});
        //noinspection UseBulkOperation
        mockSet.forEach(collector::add);
        assertEquals(new ArrayList<>() {{
            add(1);
            add(2);
            add(3);
        }}, collector);
    }

    @Test
    public void testGenerateMockList() {
        var mockList = generateMockList(1, 2, 3);

        assertNotNull(mockList);
        assertEquals(3, mockList.size());
        assertEquals(1, mockList.get(0));
        assertEquals(2, mockList.get(1));
        assertEquals(3, mockList.get(2));
        verify(mockList, times(3)).get(anyInt());
        assertTrue(mockList.contains(1));
        assertTrue(mockList.contains(2));
        assertTrue(mockList.contains(3));
        assertFalse(mockList.contains(4));
        var collector = new ArrayList<Integer>();
        // NB: Calling forEach twice here to ensure that multiple iteration is possible
        mockList.forEach(_ -> {});
        //noinspection UseBulkOperation
        mockList.forEach(collector::add);
        assertEquals(new ArrayList<>() {{
            add(1);
            add(2);
            add(3);
        }}, collector);
    }

    @Test
    public void testGenerateMockMap() {
        var mockMap = generateMockMap(pairOf(3, "C"), pairOf(2, "B"), pairOf(1, "A"));

        assertNotNull(mockMap);
        assertEquals(3, mockMap.size());
        assertEquals("A", mockMap.get(1));
        assertEquals("B", mockMap.get(2));
        assertEquals("C", mockMap.get(3));
        // NB: Calling forEach twice here to ensure that multiple iteration is possible
        mockMap.forEach((_, _) -> {});
        var keysInAnyOrder = new ArrayList<>(mockMap.keySet());
        var valuesInAnyOrder = new ArrayList<>(mockMap.values());
        var keysInOrder = new ArrayList<Integer>();
        var valuesInOrder = new ArrayList<String>();
        mockMap.forEach((k, v) -> {
            keysInOrder.add(k);
            valuesInOrder.add(v);
        });
        assertEquals(3, keysInAnyOrder.size());
        assertTrue(keysInAnyOrder.contains(1));
        assertTrue(keysInAnyOrder.contains(2));
        assertTrue(keysInAnyOrder.contains(3));
        assertEquals(3, valuesInAnyOrder.size());
        assertTrue(valuesInAnyOrder.contains("A"));
        assertTrue(valuesInAnyOrder.contains("B"));
        assertTrue(valuesInAnyOrder.contains("C"));
        assertEquals(new ArrayList<Integer>() {{
            add(3);
            add(2);
            add(1);
        }}, keysInOrder);
        assertEquals(new ArrayList<String>() {{
            add("C");
            add("B");
            add("A");
        }}, valuesInOrder);
    }

    @Test
    public void testGenerateMockLookupFunction() {
        var id1 = "id1";
        var id2 = "id2";
        var id3 = "id3";
        var invalidId = "invalidId";
        var value1 = 1;
        var value2 = 2;
        var value3 = 3;

        var lookupFunction = generateMockLookupFunction(
                pairOf(id1, value1),
                pairOf(id2, value2),
                pairOf(id3, value3));

        assertNull(lookupFunction.apply(invalidId));
        assertEquals(value1, lookupFunction.apply(id1));
        assertEquals(value2, lookupFunction.apply(id2));
        assertEquals(value3, lookupFunction.apply(id3));
        verify(lookupFunction).apply(invalidId);
        verify(lookupFunction).apply(id1);
        verify(lookupFunction).apply(id2);
        verify(lookupFunction).apply(id3);
    }

    @Test
    public void testGenerateMockLookupFunctionWithIdByEntities() {
        var id1 = "id1";
        var id2 = "id2";
        var id3 = "id3";
        var invalidId = "invalidId";

        var mockHasId1 = mock(HasId.class);
        var mockHasId2 = mock(HasId.class);
        var mockHasId3 = mock(HasId.class);

        when(mockHasId1.id()).thenReturn(id1);
        when(mockHasId2.id()).thenReturn(id2);
        when(mockHasId3.id()).thenReturn(id3);

        var lookupFunction = generateMockLookupFunctionWithId(mockHasId1, mockHasId2, mockHasId3);

        assertNull(lookupFunction.apply(invalidId));
        assertSame(mockHasId1, lookupFunction.apply(id1));
        assertSame(mockHasId2, lookupFunction.apply(id2));
        assertSame(mockHasId3, lookupFunction.apply(id3));
        verify(lookupFunction).apply(invalidId);
        verify(lookupFunction).apply(id1);
        verify(lookupFunction).apply(id2);
        verify(lookupFunction).apply(id3);
    }

    @Test
    public void testGenerateMockLookupFunctionWithIdByIds() {
        var id1 = "id1";
        var id2 = "id2";
        var id3 = "id3";
        var invalidId = "invalidId";

        var lookupAndEntities = generateMockLookupFunctionWithId(HasId.class, id1, id2, id3);

        assertNotNull(lookupAndEntities);
        assertNotNull(lookupAndEntities.lookup);
        assertNotNull(lookupAndEntities.entities);
        assertEquals(3, lookupAndEntities.entities.size());
        assertNull(lookupAndEntities.lookup.apply(invalidId));
        assertSame(lookupAndEntities.entities.get(0), lookupAndEntities.lookup.apply(id1));
        assertSame(lookupAndEntities.entities.get(1), lookupAndEntities.lookup.apply(id2));
        assertSame(lookupAndEntities.entities.get(2), lookupAndEntities.lookup.apply(id3));
        assertEquals(id1, lookupAndEntities.entities.get(0).id());
        assertEquals(id2, lookupAndEntities.entities.get(1).id());
        assertEquals(id3, lookupAndEntities.entities.get(2).id());
        verify(lookupAndEntities.lookup).apply(invalidId);
        verify(lookupAndEntities.lookup).apply(id1);
        verify(lookupAndEntities.lookup).apply(id2);
        verify(lookupAndEntities.lookup).apply(id3);
    }

    @Test
    public void testGenerateMockLookupFunctionWithIdByUuids() {
        var uuid1 = randomUUID();
        var uuid2 = randomUUID();
        var uuid3 = randomUUID();
        var invalidId = randomUUID();

        var lookupAndEntities = generateMockLookupFunctionWithUuid(HasUuid.class, uuid1, uuid2, uuid3);

        assertNotNull(lookupAndEntities);
        assertNotNull(lookupAndEntities.lookup);
        assertNotNull(lookupAndEntities.entities);
        assertEquals(3, lookupAndEntities.entities.size());
        assertNull(lookupAndEntities.lookup.apply(invalidId));
        assertSame(lookupAndEntities.entities.get(0), lookupAndEntities.lookup.apply(uuid1));
        assertSame(lookupAndEntities.entities.get(1), lookupAndEntities.lookup.apply(uuid2));
        assertSame(lookupAndEntities.entities.get(2), lookupAndEntities.lookup.apply(uuid3));
        assertEquals(uuid1, lookupAndEntities.entities.get(0).uuid());
        assertEquals(uuid2, lookupAndEntities.entities.get(1).uuid());
        assertEquals(uuid3, lookupAndEntities.entities.get(2).uuid());
        verify(lookupAndEntities.lookup).apply(invalidId);
        verify(lookupAndEntities.lookup).apply(uuid1);
        verify(lookupAndEntities.lookup).apply(uuid2);
        verify(lookupAndEntities.lookup).apply(uuid3);
    }

    @Test
    public void testGenerateSimpleMockTypeHandler() {
        var values = new Integer[]{randomInt(), randomInt(), randomInt()};

        var mockIntegerHandler = generateSimpleMockTypeHandler(values);

        assertNotNull(mockIntegerHandler);
        for (var value : values) {
            assertEquals(value, mockIntegerHandler.read(value.toString()));
            assertEquals(value.toString(), mockIntegerHandler.write(value));
        }
    }

    @Test
    public void testGenerateSimpleMockTypeHandlerWithWrittenValues() {
        var values = new Integer[]{randomInt(), randomInt(), randomInt()};
        var writtenValues = new String[]{randomString(), randomString(), randomString()};

        var mockIntegerHandler = generateSimpleMockTypeHandler(
                pairOf(writtenValues[0], values[0]),
                pairOf(writtenValues[1], values[1]),
                pairOf(writtenValues[2], values[2]));

        assertNotNull(mockIntegerHandler);
        for (var i = 0; i < 3; i++) {
            assertEquals(values[i], mockIntegerHandler.read(writtenValues[i]));
            assertEquals(writtenValues[i], mockIntegerHandler.write(values[i]));
        }
    }

    @Test
    public void testGenerateMockPersistenceHandlerWithSimpleHandlers() {
        var ints = new Integer[]{randomInt(), randomInt(), randomInt()};
        var doubles =
                new Double[]{randomDouble(), randomDouble(), randomDouble()};

        var PersistenceHandlerAndHandlers =
                generateMockPersistenceHandlerWithSimpleHandlers(ints, doubles);

        assertNotNull(PersistenceHandlerAndHandlers);
        assertNotNull(PersistenceHandlerAndHandlers.FIRST);
        assertNotNull(PersistenceHandlerAndHandlers.SECOND);
        var integerHandler =
                PersistenceHandlerAndHandlers.SECOND.get(Integer.class.getCanonicalName());
        for (var value : ints) {
            assertEquals(value, integerHandler.read(value.toString()));
            //noinspection unchecked
            assertEquals(value.toString(), integerHandler.write(value));
        }
        var doubleHandler =
                PersistenceHandlerAndHandlers.SECOND.get(Double.class.getCanonicalName());
        for (var value : doubles) {
            assertEquals(value, doubleHandler.read(value.toString()));
            //noinspection unchecked
            assertEquals(value.toString(), doubleHandler.write(value));
        }

    }

    @Test
    public void testGenerateMockEntityAndHandler() {
        var writtenValue = "writtenValue";

        var handlerAndEntity = generateMockEntityAndHandler(Item.class, writtenValue);

        assertNotNull(handlerAndEntity);
        var entity = handlerAndEntity.entity;
        assertNotNull(entity);
        var handler = handlerAndEntity.handler;
        assertNotNull(handler);
        assertEquals(writtenValue, handler.write(null));
        assertSame(entity, handler.read(null));
        assertSame(entity, handler.read(""));
    }

    @Test
    public void testGenerateMockEntitiesAndHandler() {
        var writtenValue1 = "writtenValue1";
        var writtenValue2 = "writtenValue2";

        var handlerAndEntities = generateMockHandlerAndEntities(Item.class,
                new String[]{writtenValue1, writtenValue2});

        assertNotNull(handlerAndEntities);
        assertNotNull(handlerAndEntities.entities);
        assertEquals(2, handlerAndEntities.entities.size());
        assertTrue(handlerAndEntities.entities.containsKey(writtenValue1));
        assertTrue(handlerAndEntities.entities.containsKey(writtenValue2));
        assertNotNull(handlerAndEntities.handler);
        assertEquals(writtenValue1,
                handlerAndEntities.handler.write(handlerAndEntities.entities.get(writtenValue1)));
        assertEquals(writtenValue2,
                handlerAndEntities.handler.write(handlerAndEntities.entities.get(writtenValue2)));
        assertSame(handlerAndEntities.entities.get(writtenValue1),
                handlerAndEntities.handler.read(writtenValue1));
        assertSame(handlerAndEntities.entities.get(writtenValue2),
                handlerAndEntities.handler.read(writtenValue2));
    }

    @Test
    public void testHydrateMockHandler() {
        var val = 123;
        var written = "written";
        @SuppressWarnings("unchecked") var mockHandler =
                (TypeHandler<Integer>) mock(TypeHandler.class);

        hydrateMockHandler(mockHandler, pairOf(val, written));

        assertEquals(val, mockHandler.read(written));
        assertEquals(written, mockHandler.write(val));
    }

    @Test
    public void testGenerateMockStaticProvider() {
        var val = 123;

        var provider = generateMockStaticProvider(val);

        assertEquals(val, provider.provide(randomLong()));
    }
}
