package inaugural.soliloquy.tools;

import soliloquy.specs.common.valueobjects.FloatBox;
import soliloquy.specs.gamestate.entities.Deletable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

// TODO: Consider breaking this out into multiple classes
public class Check {

    // ===============================================
    // ===============================================
    // ============ Null and empty checks ============
    // ===============================================
    // ===============================================

    public static <T> T ifNull(T obj, String paramName) {
        if (obj == null) {
            throwException(paramName, "null");
        }
        return obj;
    }

    public static String ifNullOrEmpty(String str, String paramName) {
        if (ifNull(str, paramName).isEmpty()) {
            throwException(paramName, "empty");
        }
        return str;
    }

    public static <T> T[] ifAnyNull(T[] array, String paramName) {
        if (array == null) {
            return null;
        }
        for (var item : array) {
            Check.ifNull(item, "item within " + paramName);
        }
        return array;
    }

    public static <T> T ifNullOrEmptyIfString(T obj, String paramName) {
        if (obj instanceof String) {
            //noinspection unchecked
            return (T) ifNullOrEmpty((String) obj, paramName);
        }
        else {
            return ifNull(obj, paramName);
        }
    }

    public static <T extends Deletable> T ifDeleted(T obj, String paramName) {
        if (obj.isDeleted()) {
            throwException(paramName, "deleted");
        }
        return obj;
    }

    public static <K, V> Map<K, V> ifMapIsNonEmptyWithRealKeysAndValues(Map<K, V> map,
                                                                        String paramName) {
        return ifMapIsNonEmptyWithRealKeysAndValues(map, paramName, k -> v -> {});
    }

    public static <K, V> Map<K, V> ifMapIsNonEmptyWithRealKeysAndValues(Map<K, V> map,
                                                                        String paramName,
                                                                        Function<K, Consumer<V>>
                                                                                itemCheck) {
        Check.ifNull(map, paramName);
        if (map.isEmpty()) {
            throwException(paramName, "empty");
        }
        for (Map.Entry<K, V> entry : map.entrySet()) {
            Check.ifNullOrEmptyIfString(entry.getKey(), "key in " + paramName);
            Check.ifNullOrEmptyIfString(entry.getValue(), "value in " + paramName);
            itemCheck.apply(entry.getKey()).accept(entry.getValue());
        }
        return map;
    }

    // ================================================
    // ================================================
    // ============ Numerical value checks ============
    // ================================================
    // ================================================

    // =======================
    // ==== ifNonNegative ====
    // =======================

    public static short ifNonNegative(short i, String paramName) {
        if (i < 0) {
            throwException(paramName, i, "negative");
        }
        return i;
    }

    public static int ifNonNegative(int i, String paramName) {
        if (i < 0) {
            throwException(paramName, i, "negative");
        }
        return i;
    }

    public static Integer ifNonNegative(Integer i, String paramName) {
        if (i != null && i < 0) {
            throwException(paramName, i, "negative");
        }
        return i;
    }

    public static long ifNonNegative(long i, String paramName) {
        if (i < 0) {
            throwException(paramName, i, "negative");
        }
        return i;
    }

    public static float ifNonNegative(float i, String paramName) {
        if (i < 0) {
            throwException(paramName, i, "negative");
        }
        return i;
    }

    public static double ifNonNegative(double i, String paramName) {
        if (i < 0) {
            throwException(paramName, i, "negative");
        }
        return i;
    }

    // ========================
    // ==== throwOnLteZero ====
    // ========================

    public static short throwOnLteZero(short i, String paramName) {
        return throwOnLteValue(i, (short) 0, paramName);
    }

    public static int throwOnLteZero(int i, String paramName) {
        return throwOnLteValue(i, 0, paramName);
    }

    public static long throwOnLteZero(long i, String paramName) {
        return throwOnLteValue(i, 0L, paramName);
    }

    public static float throwOnLteZero(float i, String paramName) {
        return throwOnLteValue(i, 0f, paramName);
    }

    public static double throwOnLteZero(double i, String paramName) {
        return throwOnLteValue(i, 0d, paramName);
    }

    // ========================
    // ==== throwOnLtValue ====
    // ========================

    public static short throwOnLtValue(short param, short value, String paramName) {
        if (param < value) {
            throwException(paramName, param, "less than " + value);
        }
        return param;
    }

    public static int throwOnLtValue(int param, int value, String paramName) {
        if (param < value) {
            throwException(paramName, param, "less than " + value);
        }
        return param;
    }

    public static long throwOnLtValue(long param, long value, String paramName) {
        if (param < value) {
            throwException(paramName, param, "less than " + value);
        }
        return param;
    }

    public static float throwOnLtValue(float param, float value, String paramName) {
        if (param < value) {
            throwException(paramName, param, "less than " + value);
        }
        return param;
    }

    public static double throwOnLtValue(double param, double value, String paramName) {
        if (param < value) {
            throwException(paramName, param, "less than " + value);
        }
        return param;
    }

    // =========================
    // ==== throwOnLteValue ====
    // =========================

    public static short throwOnLteValue(short param, short value, String paramName) {
        if (param <= value) {
            throwException(paramName, param, "less than or equal to " + value);
        }
        return param;
    }

    public static int throwOnLteValue(int param, int value, String paramName) {
        if (param <= value) {
            throwException(paramName, param, "less than or equal to " + value);
        }
        return param;
    }

    public static long throwOnLteValue(long param, long value, String paramName) {
        if (param <= value) {
            throwException(paramName, param, "less than or equal to " + value);
        }
        return param;
    }

    public static float throwOnLteValue(float param, float value, String paramName) {
        if (param <= value) {
            throwException(paramName, param, "less than or equal to " + value);
        }
        return param;
    }

    public static double throwOnLteValue(double param, double value, String paramName) {
        if (param <= value) {
            throwException(paramName, param, "less than or equal to " + value);
        }
        return param;
    }

    // ========================
    // ==== throwOnGtValue ====
    // ========================

    public static short throwOnGtValue(short param, short value, String paramName) {
        if (param > value) {
            throwException(paramName, param, "greater than " + value);
        }
        return param;
    }

    public static int throwOnGtValue(int param, int value, String paramName) {
        if (param > value) {
            throwException(paramName, param, "greater than " + value);
        }
        return param;
    }

    public static long throwOnGtValue(long param, long value, String paramName) {
        if (param > value) {
            throwException(paramName, param, "greater than " + value);
        }
        return param;
    }

    public static float throwOnGtValue(float param, float value, String paramName) {
        if (param > value) {
            throwException(paramName, param, "greater than " + value);
        }
        return param;
    }

    public static double throwOnGtValue(double param, double value, String paramName) {
        if (param > value) {
            throwException(paramName, param, "greater than " + value);
        }
        return param;
    }

    // =========================
    // ==== throwOnGteValue ====
    // =========================

    public static short throwOnGteValue(short param, short value, String paramName) {
        if (param >= value) {
            throwException(paramName, param, "greater than " + value);
        }
        return param;
    }

    public static int throwOnGteValue(int param, int value, String paramName) {
        if (param >= value) {
            throwException(paramName, param, "greater than " + value);
        }
        return param;
    }

    public static long throwOnGteValue(long param, long value, String paramName) {
        if (param >= value) {
            throwException(paramName, param, "greater than " + value);
        }
        return param;
    }

    public static float throwOnGteValue(float param, float value, String paramName) {
        if (param >= value) {
            throwException(paramName, param, "greater than " + value);
        }
        return param;
    }

    public static double throwOnGteValue(double param, double value, String paramName) {
        if (param >= value) {
            throwException(paramName, param, "greater than " + value);
        }
        return param;
    }

    // ============================
    // ==== throwOnEqualsValue ====
    // ============================

    public static short throwOnEqualsValue(short param, short value, String paramName) {
        if (param == value) {
            throwException(paramName, param, "equal to " + value);
        }
        return param;
    }

    public static int throwOnEqualsValue(int param, int value, String paramName) {
        if (param == value) {
            throwException(paramName, param, "equal to " + value);
        }
        return param;
    }

    public static long throwOnEqualsValue(long param, long value, String paramName) {
        if (param == value) {
            throwException(paramName, param, "equal to " + value);
        }
        return param;
    }

    public static float throwOnEqualsValue(float param, float value, String paramName) {
        if (param == value) {
            throwException(paramName, param, "equal to " + value);
        }
        return param;
    }

    public static double throwOnEqualsValue(double param, double value, String paramName) {
        if (param == value) {
            throwException(paramName, param, "equal to " + value);
        }
        return param;
    }

    // ==========================
    // ==== throwOnSecondLte ====
    // ==========================

    public static void throwOnSecondLte(short first, short second,
                                        String firstParamName, String secondParamName) {
        if (second <= first) {
            throwException(secondParamName + " (" + second + ") cannot be less than or equal to " +
                    firstParamName + " (" + first + ")");
        }
    }

    public static void throwOnSecondLte(int first, int second,
                                        String firstParamName, String secondParamName) {
        if (second <= first) {
            throwException(secondParamName + " (" + second + ") cannot be less than or equal to " +
                    firstParamName + " (" + first + ")");
        }
    }

    public static void throwOnSecondLte(long first, long second,
                                        String firstParamName, String secondParamName) {
        if (second <= first) {
            throwException(secondParamName + " (" + second + ") cannot be less than or equal to " +
                    firstParamName + " (" + first + ")");
        }
    }

    public static void throwOnSecondLte(float first, float second,
                                        String firstParamName, String secondParamName) {
        if (second <= first) {
            throwException(secondParamName + " (" + second + ") cannot be less than or equal to " +
                    firstParamName + " (" + first + ")");
        }
    }

    public static void throwOnSecondLte(double first, double second,
                                        String firstParamName, String secondParamName) {
        if (second <= first) {
            throwException(secondParamName + " (" + second + ") cannot be less than or equal to " +
                    firstParamName + " (" + first + ")");
        }
    }

    // =========================
    // ==== throwOnSecondGt ====
    // =========================

    public static void throwOnSecondGt(short first, short second,
                                       String firstParamName, String secondParamName) {
        if (second > first) {
            throwException(secondParamName + " (" + second + ") cannot be greater than " +
                    firstParamName + " (" + first + ")");
        }
    }

    public static void throwOnSecondGt(int first, int second,
                                       String firstParamName, String secondParamName) {
        if (second > first) {
            throwException(secondParamName + " (" + second + ") cannot be greater than " +
                    firstParamName + " (" + first + ")");
        }
    }

    public static void throwOnSecondGt(long first, long second,
                                       String firstParamName, String secondParamName) {
        if (second > first) {
            throwException(secondParamName + " (" + second + ") cannot be greater than " +
                    firstParamName + " (" + first + ")");
        }
    }

    public static void throwOnSecondGt(float first, float second,
                                       String firstParamName, String secondParamName) {
        if (second > first) {
            throwException(secondParamName + " (" + second + ") cannot be greater than " +
                    firstParamName + " (" + first + ")");
        }
    }

    public static void throwOnSecondGt(double first, double second,
                                       String firstParamName, String secondParamName) {
        if (second > first) {
            throwException(secondParamName + " (" + second + ") cannot be greater than " +
                    firstParamName + " (" + first + ")");
        }
    }

    // =============================
    // ==== Between 0.0 and 1.0 ====
    // =============================

    public static void isBetweenZeroAndOne(float value, String paramName) {
        if (value < 0.0f || value > 1.0f) {
            throwException(paramName, value, "outside of the range of [0.0, 1.0]");
        }
    }

    // =======================================
    // =======================================
    // ============ Other methods ============
    // =======================================
    // =======================================

    // ==================
    // ==== FloatBox ====
    // ==================

    public static FloatBox ifValid(FloatBox f, String paramName) {
        Check.ifNull(f, paramName);
        if (f.LEFT_X > f.RIGHT_X) {
            throwException(String.format("LEFT_X (%f) cannot be greater than RIGHT_X (%f)", f.LEFT_X, f.RIGHT_X));
        }
        if (f.TOP_Y > f.BOTTOM_Y) {
            throwException(String.format("TOP_Y (%f) cannot be greater than BOTTOM_Y (%f)", f.TOP_Y, f.BOTTOM_Y));
        }
        return f;
    }

    // ========================
    // ==== Helper Methods ====
    // ========================

    private static void throwException(String paramName, String violationType) {
        throwException(paramName + " cannot be " + violationType);
    }

    private static void throwException(String paramName, Object value, String violationType) {
        throwException(paramName + " (" + value + ") cannot be " + violationType);
    }

    private static void throwException(String exceptionMessage) {
        @SuppressWarnings("rawtypes") ArrayList<Class> classes = new ArrayList<>() {{
            add(Check.class);
            add(Thread.class);
        }};
        throw new IllegalArgumentException(
                getFirstStackTraceElementNotInClasses(classes) + ": " + exceptionMessage);
    }

    @SuppressWarnings("rawtypes")
    public static String getFirstStackTraceElementNotInClasses(List<Class> classes) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement callingMethod = null;
        for (StackTraceElement stackTraceElement : stackTrace) {
            String stackElementClassName = stackTraceElement.getClassName();
            boolean stackElementInClasses = false;
            for (Class clazz : classes) {
                if (stackElementClassName.equals(clazz.getName())) {
                    stackElementInClasses = true;
                    break;
                }
            }
            if (!stackElementInClasses) {
                callingMethod = stackTraceElement;
                break;
            }
        }
        assert callingMethod != null;
        String className = callingMethod.getClassName();
        String methodName = callingMethod.getMethodName();

        return className + (!methodName.equals("<init>") ? "." + methodName : "");
    }
}
