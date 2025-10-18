package inaugural.soliloquy.tools.tests.collections;

import org.junit.jupiter.api.Test;
import soliloquy.specs.common.valueobjects.Pair;

import java.util.*;
import java.util.stream.Stream;

import static inaugural.soliloquy.tools.collections.Collections.*;
import static inaugural.soliloquy.tools.testing.Assertions.assertEqualsAndNotSame;
import static org.junit.jupiter.api.Assertions.*;
import static soliloquy.specs.common.valueobjects.Pair.pairOf;

public class CollectionsTests {
    @Test
    public void testArrayOf() {
        var array = arrayOf(123, 456, 789);

        assertNotNull(array);
        assertEquals(3, array.length);
        assertEquals((Integer) 123, array[0]);
        assertEquals((Integer) 456, array[1]);
        assertEquals((Integer) 789, array[2]);
    }

    @Test
    public void testArrayFloats() {
        var array = arrayFloats(1f, 2f, 3f);

        assertNotNull(array);
        assertEquals(3, array.length);
        assertEquals(1f, array[0]);
        assertEquals(2f, array[1]);
        assertEquals(3f, array[2]);
    }

    @Test
    public void testArrayInts() {
        var array = arrayInts(1, 2, 3);

        assertNotNull(array);
        assertEquals(3, array.length);
        assertEquals(1, array[0]);
        assertEquals(2, array[1]);
        assertEquals(3, array[2]);
    }

    @Test
    public void testArrayChars() {
        var array = arrayChars('a', 'b', 'c');

        assertNotNull(array);
        assertEquals(3, array.length);
        assertEquals('a', array[0]);
        assertEquals('b', array[1]);
        assertEquals('c', array[2]);
    }

    @Test
    public void testSetOfFromArray() {
        var set = setOf(1, 2, 3);

        assertNotNull(set);
        assertEquals(3, set.size());
        assertTrue(set.contains(1));
        assertTrue(set.contains(2));
        assertTrue(set.contains(3));
    }

    @Test
    public void testSetOfFromCollection() {
        var originalCollection = new HashSet<>() {{
            add(1);
            add(2);
            add(3);
        }};

        var set = setOf(originalCollection);

        assertNotNull(set);
        assertEqualsAndNotSame(originalCollection, set);
    }

    @Test
    public void testListOfFromArray() {
        var list = listOf(new Integer[]{1, 2, 3});

        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals((Integer) 1, list.get(0));
        assertEquals((Integer) 2, list.get(1));
        assertEquals((Integer) 3, list.get(2));
    }

    @Test
    public void testListOfWithMap() {
        var list = listOf(i -> "#" + i, 1, 2, 3);

        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals("#1", list.get(0));
        assertEquals("#2", list.get(1));
        assertEquals("#3", list.get(2));
    }

    @Test
    public void testListInts() {
        @SuppressWarnings("RedundantArrayCreation") var list = listInts(new int[]{1, 2, 3});

        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals((Integer) 1, list.get(0));
        assertEquals((Integer) 2, list.get(1));
        assertEquals((Integer) 3, list.get(2));
    }

    @Test
    public void testListIntsNull() {
        var list = listInts((int[]) null);

        assertNotNull(list);
        assertTrue(list.isEmpty());
    }

    @Test
    public void testListOfFromCollection() {
        var originalCollection = new Stack<Integer>() {{
            add(1);
            add(2);
            add(3);
        }};

        var list = listOf(originalCollection);

        assertNotNull(list);
        assertEqualsAndNotSame(originalCollection, list);
    }

    @Test
    public void testListOfFromList() {
        var originalList = new ArrayList<Integer>() {{
            add(1);
            add(2);
            add(3);
        }};

        var list = listOf(originalList);

        assertNotNull(list);
        assertEqualsAndNotSame(originalList, list);
    }

    @Test
    public void testListOfStream() {
        var originalList = new ArrayList<Integer>() {{
            add(1);
            add(2);
            add(3);
        }};

        var list = listOf(originalList.stream());

        assertNotNull(list);
        assertEqualsAndNotSame(originalList, list);
    }

    @Test
    public void testMapOfFromPairs() {
        var map = mapOf(pairOf("A", 1), pairOf("B", 2), pairOf("C", 3));

        assertNotNull(map);
        assertEquals(3, map.size());
        assertEquals((Integer) 1, map.get("A"));
        assertEquals((Integer) 2, map.get("B"));
        assertEquals((Integer) 3, map.get("C"));
    }

    @Test
    public void testMapFromItems() {
        var map = mapOf(
                "A", 1,
                "B", 2,
                "C", 3
        );

        assertNotNull(map);
        assertEquals(3, map.size());
        assertEquals(1, map.get("A"));
        assertEquals(2, map.get("B"));
        assertEquals(3, map.get("C"));
    }

    @Test
    public void testMapFromItemsWithInvalidLength() {
        assertThrows(IllegalArgumentException.class, () -> mapOf(
                "A", 1,
                "B", 2,
                "C"
        ));
    }

    @Test
    public void testMapOfFromPairStream() {
        var pairStream = new ArrayList<Pair<String, Integer>>() {{
            add(pairOf("A", 1));
            add(pairOf("B", 2));
            add(pairOf("C", 3));
        }}.stream();

        var map = mapOf(pairStream);

        assertNotNull(map);
        assertEquals(3, map.size());
        assertEquals((Integer) 1, map.get("A"));
        assertEquals((Integer) 2, map.get("B"));
        assertEquals((Integer) 3, map.get("C"));
    }

    @Test
    public void testMapOfStream() {
        var originalMap = new HashMap<String, Integer>() {{
            put("A", 1);
            put("B", 2);
            put("C", 3);
        }};

        var map = mapOfStream(originalMap.entrySet().stream());

        assertNotNull(map);
        assertEqualsAndNotSame(originalMap, map);
    }

    @Test
    public void testMapOfFromMap() {
        var originalMap = new HashMap<String, Integer>() {{
            put("A", 1);
            put("B", 2);
            put("C", 3);
        }};

        var map = mapOf(originalMap);

        assertNotNull(map);
        assertEqualsAndNotSame(originalMap, map);
    }

    @Test
    public void testImmutableMap() {
        var originalMap = new HashMap<String, Integer>() {{
            put("A", 1);
            put("B", 2);
            put("C", 3);
        }};

        var immutableMap = immutable(originalMap);

        assertNotNull(immutableMap);
        assertEquals(originalMap.size(), immutableMap.size());
        assertEquals(originalMap.isEmpty(), immutableMap.isEmpty());
        for (var originalEntry : originalMap.entrySet()) {
            assertTrue(immutableMap.containsKey(originalEntry.getKey()));
            assertEquals(originalEntry.getValue(), immutableMap.get(originalEntry.getKey()));
        }
        for (var immutableEntry : immutableMap.entrySet()) {
            assertTrue(originalMap.containsKey(immutableEntry.getKey()));
            assertEquals(originalMap.get(immutableEntry.getKey()), immutableEntry.getValue());
        }
        assertEquals(originalMap.keySet(), immutableMap.keySet());
        assertEquals(originalMap.values(), immutableMap.values());
        assertEquals(immutable(originalMap), immutableMap);
    }

    @Test
    public void testImmutableMapOfFromPairs() {
        var immutableMap = immutableMapOf(pairOf("A", 1), pairOf("B", 2), pairOf("C", 3));

        assertNotNull(immutableMap);
        assertEquals(3, immutableMap.size());
        assertEquals((Integer) 1, immutableMap.get("A"));
        assertEquals((Integer) 2, immutableMap.get("B"));
        assertEquals((Integer) 3, immutableMap.get("C"));
    }

    @Test
    public void testMutableMap() {
        var immutableMap = immutableMapOf(pairOf("A", 1), pairOf("B", 2), pairOf("C", 3));

        var mutableMap = mutable(immutableMap);
        mutableMap.put("D", 4);

        assertNotNull(mutableMap);
        assertEquals(4, mutableMap.size());
        assertEquals((Integer) 1, mutableMap.get("A"));
        assertEquals((Integer) 2, mutableMap.get("B"));
        assertEquals((Integer) 3, mutableMap.get("C"));
        assertEquals((Integer) 4, mutableMap.get("D"));
    }

    @Test
    public void testGetOrDefaultAndAdd() {
        var map = new HashMap<String, Integer>();
        var key = "key";
        Integer defaultVal = 123;

        var result = getOrDefaultAndAdd(map, key, () -> defaultVal);

        assertEquals(defaultVal, result);
        assertEquals(defaultVal, map.get(key));
    }

    @Test
    public void testRemoveChildMapKeyAndChildIfEmpty() {
        var parentKey = "parentKey";
        var childKey = "childKey";
        var map = new HashMap<String, Map<String, String>>() {{
            put(parentKey, new HashMap<>() {{
                put(childKey, "value");
            }});
        }};

        var result1 = removeChildMapKeyAndChildIfEmpty(map, parentKey, childKey);
        var result2 = removeChildMapKeyAndChildIfEmpty(map, parentKey, childKey);

        assertNull(map.get(parentKey));
        assertTrue(result1);
        assertFalse(result2);
    }

    @Test
    public void testGetFromData() {
        var key = "key";
        var val = 123;
        var data = new HashMap<String, Object>() {{
            put(key, val);
        }};

        int fromData = getFromData(data, key);

        assertEquals(val, fromData);
    }
}
