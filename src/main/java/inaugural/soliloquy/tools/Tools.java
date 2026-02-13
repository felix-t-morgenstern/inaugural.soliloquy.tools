package inaugural.soliloquy.tools;

import soliloquy.specs.common.shared.HasPriority;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

import static inaugural.soliloquy.tools.collections.Collections.listOf;
import static inaugural.soliloquy.tools.collections.Collections.mapOf;

public class Tools {
    private static HashMap<Integer, Float> EXPONENTS_OF_TEN = new HashMap<>();

    public static <T> T defaultIfNull(T val, T theDefault) {
        return val == null ? theDefault : val;
    }

    public static <TBase, TReturn> TReturn defaultIfNull(TBase base, Function<TBase, TReturn> transformBase, TReturn theDefault) {
        if (transformBase == null) {
            throw new IllegalArgumentException("Tools.defaultIfNull: transformBase cannot be null");
        }
        return base == null ? theDefault : transformBase.apply(base);
    }

    public static boolean falseIfNull(Object val) {
        return (boolean) defaultIfNull(val, false);
    }

    public static String emptyIfNull(String string) {
        return string == null ? "" : string;
    }

    public static <T> T provideIfNull(T val, Supplier<T> getDefault) {
        return val == null ? getDefault.get() : val;
    }

    public static String nullIfEmpty(String string) {
        return "".equals(string) ? null : string;
    }

    public static float round(float value, int places) throws IllegalArgumentException {
        Check.ifNonNegative(places, "places");

        float multiplicand;
        //noinspection SuspiciousMethodCalls
        if (EXPONENTS_OF_TEN.containsKey(value)) {
            //noinspection SuspiciousMethodCalls
            multiplicand = EXPONENTS_OF_TEN.get(value);
        }
        else {
            EXPONENTS_OF_TEN.put(places, multiplicand = (float) Math.pow(10, places));
        }

        return Math.round(value * multiplicand) / multiplicand;
    }

    public static String callingClassName() {
        return callingClassName(3);
    }

    public static String callingClassName(int stepsToMoveUp) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        return stackTrace[stepsToMoveUp].getClassName();
    }

    public static <T extends HasPriority> List<T> orderByPriority(Iterable<T> items) {
        Map<Integer, List<T>> groupedByPriority = mapOf();

        items.forEach(item -> {
            if (!groupedByPriority.containsKey(item.priority())) {
                groupedByPriority.put(item.priority(), listOf(item));
            }
            else {
                groupedByPriority.get(item.priority()).add(item);
            }
        });

        List<T> orderedByPriority = listOf();

        var priorities = new ArrayList<>(groupedByPriority.keySet());
        priorities.sort(Collections.reverseOrder());
        priorities.forEach(priority -> orderedByPriority.addAll(groupedByPriority.get(priority)));

        return orderedByPriority;
    }
}
