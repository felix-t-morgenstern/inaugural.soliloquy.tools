package inaugural.soliloquy.tools.tests.collections;

import org.junit.jupiter.api.Test;

import java.util.*;

import static inaugural.soliloquy.tools.collections.Collections.*;
import static inaugural.soliloquy.tools.testing.Assertions.assertEqualsAndNotSame;
import static inaugural.soliloquy.tools.valueobjects.Pair.pairOf;
import static org.junit.jupiter.api.Assertions.*;

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
        var list = listOf(1, 2, 3);

        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals((Integer) 1, list.get(0));
        assertEquals((Integer) 2, list.get(1));
        assertEquals((Integer) 3, list.get(2));
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
    public void testMapOfFromPairs() {
        var map = mapOf(pairOf("A", 1), pairOf("B", 2), pairOf("C", 3));

        assertNotNull(map);
        assertEquals(3, map.size());
        assertEquals((Integer) 1, map.get("A"));
        assertEquals((Integer) 2, map.get("B"));
        assertEquals((Integer) 3, map.get("C"));
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
}
