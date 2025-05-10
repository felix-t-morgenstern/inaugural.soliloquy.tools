package inaugural.soliloquy.tools.collections;

import soliloquy.specs.common.valueobjects.Pair;

import java.util.*;
import java.util.function.Supplier;

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
        return new ArrayList<>(Arrays.asList(items));
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

    // NB: This differs from Map.getOrDefault, since the default value is actually added to the map
    // at the provided key
    public static <K, V> V getOrDefaultAndAdd(Map<K, V> map, K key, Supplier<V> defaultSupplier) {
        if (!map.containsKey(key)) {
            var newValue = defaultSupplier.get();
            map.put(key, newValue);
            return newValue;
        }
        else {
            return map.get(key);
        }
    }

    public static <K1, K2, V2> boolean removeChildMapKeyAndChildIfEmpty(Map<K1, Map<K2, V2>> parent,
                                                                     K1 parentKey, K2 childKey) {
        var childMap = parent.get(parentKey);
        if (childMap == null) {
            return false;
        }
        var isRemoved = childMap.remove(childKey) != null;
        if (childMap.isEmpty()) {
            parent.remove(parentKey);
        }
        return isRemoved;
    }
}
