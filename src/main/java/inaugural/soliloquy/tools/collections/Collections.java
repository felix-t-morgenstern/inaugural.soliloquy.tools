package inaugural.soliloquy.tools.collections;

import soliloquy.specs.common.infrastructure.ImmutableMap;
import soliloquy.specs.common.valueobjects.Pair;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
            map.put(item.FIRST, item.SECOND);
        }
        return map;
    }

    public static <K, V> Map<K, V> mapOf(Object... items) {
        var map = new HashMap<K, V>();
        if (items.length % 2 != 0) {
            throw new IllegalArgumentException("Collections.map: items cannot be of odd length");
        }
        for (var i = 0; i < items.length; i += 2) {
            @SuppressWarnings("unchecked") K key = (K) items[i];
            @SuppressWarnings("unchecked") V val = (V) items[i + 1];
            map.put(key, val);
        }
        return map;
    }

    public static <K, V> Map<K, V> mapOf(Stream<Pair<K, V>> items) {
        var map = new HashMap<K, V>();
        items.forEach(p -> map.put(p.FIRST, p.SECOND));
        return map;
    }

    public static <K, V> Map<K, V> mapOf(Map<K, V> map) {
        return new HashMap<>(map);
    }

    public static <K, V> ImmutableMap<K, V> immutable(Map<K, V> map) {
        return new ImmutableMap<>() {
            @Override
            public int size() {
                return map.size();
            }

            @Override
            public boolean isEmpty() {
                return map.isEmpty();
            }

            @Override
            public boolean containsKey(Object o) {
                //noinspection SuspiciousMethodCalls
                return map.containsKey(o);
            }

            @Override
            public boolean containsValue(Object o) {
                //noinspection SuspiciousMethodCalls
                return map.containsValue(o);
            }

            @Override
            public V get(Object o) {
                //noinspection SuspiciousMethodCalls
                return map.get(o);
            }

            @Override
            public Set<K> keySet() {
                return map.keySet();
            }

            @Override
            public Collection<V> values() {
                return map.values();
            }

            @Override
            public Set<ImmutableEntry<K, V>> entrySet() {
                return map.entrySet().stream().map(e -> new ImmutableEntry<K, V>() {
                    @Override
                    public K getKey() {
                        return e.getKey();
                    }

                    @Override
                    public V getValue() {
                        return e.getValue();
                    }
                }).collect(Collectors.toSet());
            }

            @Override
            public boolean equals(Object o) {
                if (o instanceof ImmutableMap<?, ?> iM) {
                    return iM.size() == size() && iM.entrySet().stream().allMatch(
                            iMEntry -> containsKey(iMEntry.getKey()) &&
                                    Objects.equals(iMEntry.getValue(), get(iMEntry.getKey())));
                }
                else {
                    return false;
                }
            }
        };
    }

    @SafeVarargs
    public static <K, V> ImmutableMap<K, V> immutableMapOf(Pair<K, V>... items) {
        return immutable(mapOf(items));
    }

    public static <K, V> Map<K, V> mutable(ImmutableMap<K, V> immutableMap) {
        var map = Collections.<K, V>mapOf();
        immutableMap.entrySet().forEach(e -> map.put(e.getKey(), e.getValue()));
        return map;
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
