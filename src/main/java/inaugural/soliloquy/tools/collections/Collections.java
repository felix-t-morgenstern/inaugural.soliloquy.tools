package inaugural.soliloquy.tools.collections;

import soliloquy.specs.common.valueobjects.Pair;

import java.util.*;

public class Collections {
    @SafeVarargs
    public static <T> T[] arrayOf(T... items) {
        return items;
    }

    @SafeVarargs
    public static <T> Set<T> setOf(T... items) {
        return new HashSet<>(Arrays.asList(items));
    }

    public static <T> Set<T> setOf(Collection<T> items) {
        return new HashSet<>(items);
    }

    @SafeVarargs
    public static <T> List<T> listOf(T... items) {
        return new ArrayList<>(setOf(items));
    }

    public static <T> List<T> listOf(Collection<T> list) {
        return new ArrayList<>(list);
    }

    public static <T> List<T> listOf(List<T> list) {
        return new ArrayList<>(list);
    }

    @SafeVarargs
    public static <K, V> Map<K, V> mapOf(Pair<K, V>... items) {
        var map = new HashMap<K, V>();
        for (var item : items) {
            map.put(item.item1(), item.item2());
        }
        return map;
    }

    public static <K, V> Map<K, V> mapOf(Map<K, V> map) {
        return new HashMap<>(map);
    }
}
