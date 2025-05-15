package inaugural.soliloquy.tools.tests;

import inaugural.soliloquy.tools.Check;
import org.junit.jupiter.api.Test;
import soliloquy.specs.gamestate.entities.Deletable;

import java.util.HashMap;

import static inaugural.soliloquy.tools.testing.Assertions.assertThrowsWithMessage;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CheckTests {
    @Test
    void testIfNull() {
        final var paramName = "paramName";

        final var input = new Object();
        assertSame(input, Check.ifNull(input, paramName));

        assertThrows(IllegalArgumentException.class, () -> Check.ifNull(null, paramName));
        assertThrowsWithMessage(() -> Check.ifNull(null, paramName), IllegalArgumentException.class,
                "inaugural.soliloquy.tools.tests.CheckTests.lambda$testIfNull$1: " + paramName +
                        " cannot be null");
    }

    @Test
    void testIfNullOrEmpty() {
        final var paramName = "paramName";

        final var input = "input";
        assertSame(input, Check.ifNullOrEmpty(input, paramName));

        assertThrows(IllegalArgumentException.class, () -> Check.ifNullOrEmpty("", paramName));
        try {
            Check.ifNullOrEmpty("", paramName);
            fail();
        }
        catch (IllegalArgumentException e) {
            assertEquals("inaugural.soliloquy.tools.tests.CheckTests.testIfNullOrEmpty: " +
                            paramName + " cannot be empty",
                    e.getMessage());
        }
        catch (Exception e) {
            fail();
        }
    }

    @Test
    void testIfAnyNull() {
        var paramName = "paramName";
        var emptyArray = new Object[0];

        assertNull(Check.ifAnyNull(null, paramName));
        assertSame(emptyArray, Check.ifAnyNull(emptyArray, paramName));
        assertThrowsWithMessage(() -> Check.ifAnyNull(new Object[] {null}, paramName), IllegalArgumentException.class, "inaugural.soliloquy.tools.tests.CheckTests.lambda$testIfAnyNull$0: item within " + paramName + " cannot be null");
    }

    @Test
    void testIfNullOrEmptyIfString() {
        final var paramName = "paramName";

        final var input = "input";
        assertSame(input, Check.ifNullOrEmptyIfString(input, paramName));

        assertThrows(IllegalArgumentException.class,
                () -> Check.ifNullOrEmptyIfString(null, paramName));
        assertThrows(IllegalArgumentException.class,
                () -> Check.ifNullOrEmptyIfString("", paramName));
        assertThrows(IllegalArgumentException.class,
                () -> Check.ifNullOrEmptyIfString((Integer) null, paramName));

        try {
            Check.ifNullOrEmptyIfString(null, paramName);
            fail();
        }
        catch (IllegalArgumentException e) {
            assertEquals("inaugural.soliloquy.tools.tests.CheckTests.testIfNullOrEmptyIfString: " +
                            paramName + " cannot be null",
                    e.getMessage());
        }
        catch (Exception e) {
            fail();
        }

        try {
            Check.ifNullOrEmptyIfString("", paramName);
            fail();
        }
        catch (IllegalArgumentException e) {
            assertEquals("inaugural.soliloquy.tools.tests.CheckTests.testIfNullOrEmptyIfString: " +
                            paramName + " cannot be empty",
                    e.getMessage());
        }
        catch (Exception e) {
            fail();
        }

        try {
            Check.ifNullOrEmptyIfString((Integer) null, paramName);
            fail();
        }
        catch (IllegalArgumentException e) {
            assertEquals("inaugural.soliloquy.tools.tests.CheckTests.testIfNullOrEmptyIfString: " +
                            paramName + " cannot be null",
                    e.getMessage());
        }
        catch (Exception e) {
            fail();
        }
    }

    @Test
    void testIfDeleted() {
        var mockDeleted = mock(Deletable.class);
        when(mockDeleted.isDeleted()).thenReturn(false);
        var paramName = "paramName";

        assertSame(mockDeleted, Check.ifDeleted(mockDeleted, ""));

        when(mockDeleted.isDeleted()).thenReturn(true);

        assertThrowsWithMessage(() -> Check.ifDeleted(mockDeleted, paramName),
                IllegalArgumentException.class,
                "inaugural.soliloquy.tools.tests.CheckTests.lambda$testIfDeleted$0: " + paramName +
                        " cannot be deleted");
    }

    @Test
    void testIfMapIsNonEmptyWithRealKeysAndValuesForValidMap() {
        var map = new HashMap<>() {{
            put("k", "v");
        }};

        assertSame(map, Check.ifMapIsNonEmptyWithRealKeysAndValues(map, "map"));
    }

    @Test
    void testIfMapIsNonEmptyWithRealKeysAndValuesForNullMap() {
        assertThrows(IllegalArgumentException.class, () ->
                Check.ifMapIsNonEmptyWithRealKeysAndValues(null, "map"));

        var paramName = "map";
        try {
            Check.ifMapIsNonEmptyWithRealKeysAndValues(null, paramName);
        }
        catch (IllegalArgumentException e) {
            assertEquals("inaugural.soliloquy.tools.tests.CheckTests." +
                            "testIfMapIsNonEmptyWithRealKeysAndValuesForNullMap: " + paramName +
                            " cannot be null",
                    e.getMessage());
        }
    }

    @Test
    void testIfMapIsNonEmptyWithRealKeysAndValuesForEmptyMap() {
        var map = new HashMap<String, String>();

        assertThrows(IllegalArgumentException.class, () ->
                Check.ifMapIsNonEmptyWithRealKeysAndValues(map, "map"));

        var paramName = "map";
        try {
            Check.ifMapIsNonEmptyWithRealKeysAndValues(map, paramName);
        }
        catch (IllegalArgumentException e) {
            assertEquals("inaugural.soliloquy.tools.tests.CheckTests." +
                            "testIfMapIsNonEmptyWithRealKeysAndValuesForEmptyMap: " + paramName +
                            " cannot be empty",
                    e.getMessage());
        }
    }

    @Test
    void testIfMapIsNonEmptyWithRealKeysAndValuesWithNullKey() {
        var map = new HashMap<String, String>() {{
            put(null, "v");
        }};

        assertThrows(IllegalArgumentException.class, () ->
                Check.ifMapIsNonEmptyWithRealKeysAndValues(map, "map"));

        var paramName = "map";
        try {
            Check.ifMapIsNonEmptyWithRealKeysAndValues(map, paramName);
        }
        catch (IllegalArgumentException e) {
            assertEquals("inaugural.soliloquy.tools.tests.CheckTests" +
                            ".testIfMapIsNonEmptyWithRealKeysAndValuesWithNullKey: key in " +
                            paramName + " cannot be null",
                    e.getMessage());
        }
    }

    @Test
    void testIfMapIsNonEmptyWithRealKeysAndValuesWithEmptyKey() {
        var map = new HashMap<String, String>() {{
            put("", "v");
        }};

        assertThrows(IllegalArgumentException.class, () ->
                Check.ifMapIsNonEmptyWithRealKeysAndValues(map, "map"));

        var paramName = "map";
        try {
            Check.ifMapIsNonEmptyWithRealKeysAndValues(map, paramName);
        }
        catch (IllegalArgumentException e) {
            assertEquals("inaugural.soliloquy.tools.tests.CheckTests" +
                            ".testIfMapIsNonEmptyWithRealKeysAndValuesWithEmptyKey: key in " +
                            paramName + " cannot be empty",
                    e.getMessage());
        }
    }

    @Test
    void testIfMapIsNonEmptyWithRealKeysAndValuesWithNullValue() {
        var map = new HashMap<String, String>() {{
            put("k", null);
        }};

        assertThrows(IllegalArgumentException.class, () ->
                Check.ifMapIsNonEmptyWithRealKeysAndValues(map, "map"));

        var paramName = "map";
        try {
            Check.ifMapIsNonEmptyWithRealKeysAndValues(map, paramName);
        }
        catch (IllegalArgumentException e) {
            assertEquals("inaugural.soliloquy.tools.tests.CheckTests" +
                            ".testIfMapIsNonEmptyWithRealKeysAndValuesWithNullValue: value in " +
                            paramName + " cannot be null",
                    e.getMessage());
        }
    }

    @Test
    void testIfMapIsNonEmptyWithRealKeysAndValuesWithEmptyValue() {
        var map = new HashMap<String, String>() {{
            put("k", "");
        }};

        assertThrows(IllegalArgumentException.class, () ->
                Check.ifMapIsNonEmptyWithRealKeysAndValues(map, "map"));

        var paramName = "map";
        try {
            Check.ifMapIsNonEmptyWithRealKeysAndValues(map, paramName);
        }
        catch (IllegalArgumentException e) {
            assertEquals("inaugural.soliloquy.tools.tests.CheckTests" +
                            ".testIfMapIsNonEmptyWithRealKeysAndValuesWithEmptyValue: value in " +
                            paramName + " cannot be empty",
                    e.getMessage());
        }
    }

    @Test
    void testIfMapIsNonEmptyWithRealKeysAndValuesWithItemCheck() {
        var itemCheckInputs = new HashMap<String, String>();
        var map = new HashMap<String, String>() {{
            put("k", "v");
        }};

        Check.ifMapIsNonEmptyWithRealKeysAndValues(map, "map",
                k -> v -> itemCheckInputs.put(k, v));

        assertEquals(1, itemCheckInputs.size());
        assertTrue(itemCheckInputs.containsKey("k"));
        assertEquals("v", itemCheckInputs.get("k"));
    }

    @Test
    void testIfNonNegativeForShort() {
        final var paramName = "paramName";

        final var input = (short) 123;
        assertEquals(input, Check.ifNonNegative(input, paramName));

        assertThrows(IllegalArgumentException.class,
                () -> Check.ifNonNegative((short) -1, paramName));
        try {
            Check.ifNonNegative(-1, paramName);
        }
        catch (IllegalArgumentException e) {
            assertEquals(
                    "inaugural.soliloquy.tools.tests.CheckTests.testIfNonNegativeForShort: " +
                            paramName + " (" + -1 + ")" + " cannot be negative",
                    e.getMessage());
        }
    }

    @Test
    void testIfNonNegativeForInt() {
        final var paramName = "paramName";

        final var input = 123;
        assertEquals(input, Check.ifNonNegative(input, paramName));

        assertThrows(IllegalArgumentException.class, () -> Check.ifNonNegative(-input, paramName));
        try {
            Check.ifNonNegative(-1, paramName);
        }
        catch (IllegalArgumentException e) {
            assertEquals(
                    "inaugural.soliloquy.tools.tests.CheckTests.testIfNonNegativeForInt: " +
                            paramName + " (" + -1 + ")" + " cannot be negative",
                    e.getMessage());
        }
    }

    @Test
    void testIfNonNegativeForIntClass() {
        final var paramName = "paramName";

        final Integer input = 123;
        assertEquals(input, Check.ifNonNegative(input, paramName));

        assertThrows(IllegalArgumentException.class, () -> Check.ifNonNegative(-input, paramName));
        try {
            Check.ifNonNegative(-1, paramName);
        }
        catch (IllegalArgumentException e) {
            assertEquals(
                    "inaugural.soliloquy.tools.tests.CheckTests.testIfNonNegativeForIntClass: " +
                            paramName + " (" + -1 + ")" + " cannot be negative",
                    e.getMessage());
        }
    }

    @Test
    void testIfNonNegativeForLong() {
        final var paramName = "paramName";

        final var input = 123L;
        assertEquals(input, Check.ifNonNegative(input, paramName));

        assertThrows(IllegalArgumentException.class, () -> Check.ifNonNegative(-1L, paramName));
        try {
            Check.ifNonNegative(-1, paramName);
        }
        catch (IllegalArgumentException e) {
            assertEquals(
                    "inaugural.soliloquy.tools.tests.CheckTests.testIfNonNegativeForLong: " +
                            paramName + " (" + -1 + ")" + " cannot be negative",
                    e.getMessage());
        }
    }

    @Test
    void testIfNonNegativeForFloat() {
        final var paramName = "paramName";

        final var input = 0.123f;
        assertEquals(input, Check.ifNonNegative(input, paramName));

        assertThrows(IllegalArgumentException.class, () -> Check.ifNonNegative(-1f, paramName));
        try {
            Check.ifNonNegative(-1, paramName);
        }
        catch (IllegalArgumentException e) {
            assertEquals(
                    "inaugural.soliloquy.tools.tests.CheckTests.testIfNonNegativeForFloat: " +
                            paramName + " (" + -1 + ")" + " cannot be negative",
                    e.getMessage());
        }
    }

    @Test
    void testIfNonNegativeForDouble() {
        final var paramName = "paramName";

        final var input = 0.123d;
        assertEquals(input, Check.ifNonNegative(input, paramName));

        assertThrows(IllegalArgumentException.class, () -> Check.ifNonNegative(-1d, paramName));
        try {
            Check.ifNonNegative(-1, paramName);
        }
        catch (IllegalArgumentException e) {
            assertEquals(
                    "inaugural.soliloquy.tools.tests.CheckTests.testIfNonNegativeForDouble: " +
                            paramName + " (" + -1 + ")" + " cannot be negative",
                    e.getMessage());
        }
    }

    @Test
    void testThrowOnLteZeroForShort() {
        final var paramName = "paramName";

        final var input = (short) 123;
        assertEquals(input, Check.throwOnLteZero(input, paramName));

        assertThrows(IllegalArgumentException.class,
                () -> Check.throwOnLteZero((short) 0, paramName));
        try {
            Check.throwOnLteZero((short) 0, paramName);
        }
        catch (IllegalArgumentException e) {
            assertEquals("inaugural.soliloquy.tools.tests.CheckTests" +
                            ".testThrowOnLteZeroForShort: " + paramName + " (" + 0 + ")" +
                            " cannot be less than or equal to 0",
                    e.getMessage());
        }
    }

    @Test
    void testThrowOnLteZeroForInt() {
        final var paramName = "paramName";

        final var input = 123;
        assertEquals(input, Check.throwOnLteZero(input, paramName));

        assertThrows(IllegalArgumentException.class, () -> Check.throwOnLteZero(0, paramName));
        try {
            Check.throwOnLteZero(0, paramName);
        }
        catch (IllegalArgumentException e) {
            assertEquals("inaugural.soliloquy.tools.tests.CheckTests" +
                            ".testThrowOnLteZeroForInt: " + paramName + " (" + 0 + ")" +
                            " cannot be less than or equal to 0",
                    e.getMessage());
        }
    }

    @Test
    void testThrowOnLteZeroForLong() {
        final var paramName = "paramName";

        final var input = 123L;
        assertEquals(input, Check.throwOnLteZero(input, paramName));

        assertThrows(IllegalArgumentException.class, () -> Check.throwOnLteZero(0L, paramName));
        try {
            Check.throwOnLteZero(0, paramName);
        }
        catch (IllegalArgumentException e) {
            assertEquals("inaugural.soliloquy.tools.tests.CheckTests" +
                            ".testThrowOnLteZeroForLong: " + paramName + " (" + 0 + ")" +
                            " cannot be less than or equal to 0",
                    e.getMessage());
        }
    }

    @Test
    void testThrowOnLteZeroForFloat() {
        final var paramName = "paramName";

        final var input = 0.123f;
        assertEquals(input, Check.throwOnLteZero(input, paramName));

        assertThrows(IllegalArgumentException.class, () -> Check.throwOnLteZero(0f, paramName));
        try {
            Check.throwOnLteZero(0f, paramName);
        }
        catch (IllegalArgumentException e) {
            assertEquals("inaugural.soliloquy.tools.tests.CheckTests" +
                            ".testThrowOnLteZeroForFloat: " + paramName + " (" + 0.0 + ")" +
                            " cannot be less than or equal to 0.0",
                    e.getMessage());
        }
    }

    @Test
    void testThrowOnLteZeroForDouble() {
        final var paramName = "paramName";

        final var input = 0.123d;
        assertEquals(input, Check.throwOnLteZero(input, paramName));

        assertThrows(IllegalArgumentException.class, () -> Check.throwOnLteZero(0d, paramName));
        try {
            Check.throwOnLteZero(0d, paramName);
        }
        catch (IllegalArgumentException e) {
            assertEquals("inaugural.soliloquy.tools.tests.CheckTests" +
                            ".testThrowOnLteZeroForDouble: " + paramName + " (" + 0.0 + ")" +
                            " cannot be less than or equal to 0.0",
                    e.getMessage());
        }
    }

    @Test
    void testThrowOnLtValueForShort() {
        final var paramName = "paramName";

        final var input = (short) 123;
        assertEquals(input, Check.throwOnLtValue(input, input, paramName));

        assertThrows(IllegalArgumentException.class,
                () -> Check.throwOnLtValue(input, (short) (input + 1), paramName));
        try {
            Check.throwOnLtValue(input, (short) (input + 1), paramName);
        }
        catch (IllegalArgumentException e) {
            assertEquals("inaugural.soliloquy.tools.tests.CheckTests" +
                            ".testThrowOnLtValueForShort: " + paramName + " (" + input + ")" +
                            " cannot be less than " + (input + 1),
                    e.getMessage());
        }
    }

    @Test
    void testThrowOnLtValueForInt() {
        final var paramName = "paramName";

        final var input = 123;
        assertEquals(input, Check.throwOnLtValue(input, input, paramName));

        assertThrows(IllegalArgumentException.class,
                () -> Check.throwOnLtValue(input, (input + 1), paramName));
        try {
            Check.throwOnLtValue(input, (input + 1), paramName);
        }
        catch (IllegalArgumentException e) {
            assertEquals("inaugural.soliloquy.tools.tests.CheckTests" +
                            ".testThrowOnLtValueForInt: " + paramName + " (" + input + ")" +
                            " cannot be less than " + (input + 1),
                    e.getMessage());
        }
    }

    @Test
    void testThrowOnLtValueForLong() {
        final var paramName = "paramName";

        final var input = 123L;
        assertEquals(input, Check.throwOnLtValue(input, input, paramName));

        assertThrows(IllegalArgumentException.class,
                () -> Check.throwOnLtValue(input, (input + 1), paramName));
        try {
            Check.throwOnLtValue(input, (input + 1), paramName);
        }
        catch (IllegalArgumentException e) {
            assertEquals("inaugural.soliloquy.tools.tests.CheckTests" +
                            ".testThrowOnLtValueForLong: " + paramName + " (" + input + ")" +
                            " cannot be less than " + (input + 1),
                    e.getMessage());
        }
    }

    @Test
    void testThrowOnLtValueForFloat() {
        final var paramName = "paramName";

        final var input = 0.122f;
        final var value = 0.123f;
        assertEquals(input, Check.throwOnLtValue(input, input, paramName));

        assertThrows(IllegalArgumentException.class,
                () -> Check.throwOnLtValue(input, value, paramName));
        try {
            Check.throwOnLtValue(input, value, paramName);
        }
        catch (IllegalArgumentException e) {
            assertEquals("inaugural.soliloquy.tools.tests.CheckTests" +
                            ".testThrowOnLtValueForFloat: " + paramName + " (" + input + ")" +
                            " cannot be less than " + value,
                    e.getMessage());
        }
    }

    @Test
    void testThrowOnLtValueForDouble() {
        final var paramName = "paramName";

        final var input = 0.122d;
        final var value = 0.123d;
        assertEquals(input, Check.throwOnLtValue(input, input, paramName));

        assertThrows(IllegalArgumentException.class,
                () -> Check.throwOnLtValue(input, value, paramName));
        try {
            Check.throwOnLtValue(input, value, paramName);
        }
        catch (IllegalArgumentException e) {
            assertEquals("inaugural.soliloquy.tools.tests.CheckTests" +
                            ".testThrowOnLtValueForDouble: " + paramName + " (" + input + ")" +
                            " cannot be less than " + value,
                    e.getMessage());
        }
    }

    @Test
    void testThrowOnLteValueForShort() {
        final var paramName = "paramName";

        final var input = (short) 123;
        assertEquals(input, Check.throwOnLteValue(input, (short) (input - 1), paramName));

        assertThrows(IllegalArgumentException.class,
                () -> Check.throwOnLteValue(input, (short) (input + 1), paramName));
        assertThrows(IllegalArgumentException.class,
                () -> Check.throwOnLteValue(input, input, paramName));
        try {
            Check.throwOnLteValue(input, (short) (input + 1), paramName);
        }
        catch (IllegalArgumentException e) {
            assertEquals("inaugural.soliloquy.tools.tests.CheckTests" +
                            ".testThrowOnLteValueForShort: " + paramName + " (" + input + ")" +
                            " cannot be less than or equal to " + (input + 1),
                    e.getMessage());
        }
    }

    @Test
    void testThrowOnLteValueForInt() {
        final var paramName = "paramName";

        final var input = 123;
        assertEquals(input, Check.throwOnLteValue(input, input - 1, paramName));

        assertThrows(IllegalArgumentException.class,
                () -> Check.throwOnLteValue(input, (input + 1), paramName));
        assertThrows(IllegalArgumentException.class,
                () -> Check.throwOnLteValue(input, input, paramName));
        try {
            Check.throwOnLteValue(input, (input + 1), paramName);
        }
        catch (IllegalArgumentException e) {
            assertEquals("inaugural.soliloquy.tools.tests.CheckTests" +
                            ".testThrowOnLteValueForInt: " + paramName + " (" + input + ")" +
                            " cannot be less than or equal to " + (input + 1),
                    e.getMessage());
        }
    }

    @Test
    void testThrowOnLteValueForLong() {
        final var paramName = "paramName";

        final var input = 123L;
        assertEquals(input, Check.throwOnLteValue(input, input - 1, paramName));

        assertThrows(IllegalArgumentException.class,
                () -> Check.throwOnLteValue(input, (input + 1), paramName));
        assertThrows(IllegalArgumentException.class,
                () -> Check.throwOnLteValue(input, input, paramName));
        try {
            Check.throwOnLteValue(input, (input + 1), paramName);
        }
        catch (IllegalArgumentException e) {
            assertEquals("inaugural.soliloquy.tools.tests.CheckTests" +
                            ".testThrowOnLteValueForLong: " + paramName + " (" + input + ")" +
                            " cannot be less than or equal to " + (input + 1),
                    e.getMessage());
        }
    }

    @Test
    void testThrowOnLteValueForFloat() {
        final var paramName = "paramName";

        final var input = 0.122f;
        final var value = 0.123f;
        assertEquals(input, Check.throwOnLteValue(input, input - 0.001f, paramName));

        assertThrows(IllegalArgumentException.class,
                () -> Check.throwOnLteValue(input, value, paramName));
        assertThrows(IllegalArgumentException.class,
                () -> Check.throwOnLteValue(input, input, paramName));
        try {
            Check.throwOnLteValue(input, value, paramName);
        }
        catch (IllegalArgumentException e) {
            assertEquals("inaugural.soliloquy.tools.tests.CheckTests" +
                            ".testThrowOnLteValueForFloat: " + paramName + " (" + input + ")" +
                            " cannot be less than or equal to " + value,
                    e.getMessage());
        }
    }

    @Test
    void testThrowOnLteValueForDouble() {
        final var paramName = "paramName";

        final var input = 0.122d;
        final var value = 0.123d;
        assertEquals(input, Check.throwOnLteValue(input, input - 0.001d, paramName));

        assertThrows(IllegalArgumentException.class,
                () -> Check.throwOnLteValue(input, value, paramName));
        assertThrows(IllegalArgumentException.class,
                () -> Check.throwOnLteValue(input, input, paramName));
        try {
            Check.throwOnLteValue(input, value, paramName);
        }
        catch (IllegalArgumentException e) {
            assertEquals("inaugural.soliloquy.tools.tests.CheckTests" +
                            ".testThrowOnLteValueForDouble: " + paramName + " (" + input + ")" +
                            " cannot be less than or equal to " + value,
                    e.getMessage());
        }
    }

    @Test
    void testThrowOnGtValueForShort() {
        final var paramName = "paramName";

        final var input = (short) 123;
        assertEquals(input, Check.throwOnGtValue(input, input, paramName));

        assertThrows(IllegalArgumentException.class,
                () -> Check.throwOnGtValue(input, (short) (input - 1), paramName));
        try {
            Check.throwOnGtValue(input, (short) (input - 1), paramName);
        }
        catch (IllegalArgumentException e) {
            assertEquals("inaugural.soliloquy.tools.tests.CheckTests" +
                            ".testThrowOnGtValueForShort: " + paramName + " (" + input + ")" +
                            " cannot be greater than " + (input - 1),
                    e.getMessage());
        }
    }

    @Test
    void testThrowOnGtValueForInt() {
        final var paramName = "paramName";

        final var input = 123;
        assertEquals(input, Check.throwOnGtValue(input, input, paramName));

        assertThrows(IllegalArgumentException.class,
                () -> Check.throwOnGtValue(input, input - 1, paramName));
        try {
            Check.throwOnGtValue(input, input - 1, paramName);
        }
        catch (IllegalArgumentException e) {
            assertEquals("inaugural.soliloquy.tools.tests.CheckTests" +
                            ".testThrowOnGtValueForInt: " + paramName + " (" + input + ")" +
                            " cannot be greater than " + (input - 1),
                    e.getMessage());
        }
    }

    @Test
    void testThrowOnGtValueForLong() {
        final var paramName = "paramName";

        final var input = 123L;
        assertEquals(input, Check.throwOnGtValue(input, input, paramName));

        assertThrows(IllegalArgumentException.class,
                () -> Check.throwOnGtValue(input, input - 1, paramName));
        try {
            Check.throwOnGtValue(input, input - 1, paramName);
        }
        catch (IllegalArgumentException e) {
            assertEquals("inaugural.soliloquy.tools.tests.CheckTests" +
                            ".testThrowOnGtValueForLong: " + paramName + " (" + input + ")" +
                            " cannot be greater than " + (input - 1),
                    e.getMessage());
        }
    }

    @Test
    void testThrowOnGtValueForFloat() {
        final var paramName = "paramName";

        final var input = 0.123f;
        final var value = 0.122f;
        assertEquals(input, Check.throwOnGtValue(input, input, paramName));

        assertThrows(IllegalArgumentException.class,
                () -> Check.throwOnGtValue(input, value, paramName));
        try {
            Check.throwOnGtValue(input, value, paramName);
        }
        catch (IllegalArgumentException e) {
            assertEquals("inaugural.soliloquy.tools.tests.CheckTests" +
                            ".testThrowOnGtValueForFloat: " + paramName + " (" + input + ")" +
                            " cannot be greater than " + value,
                    e.getMessage());
        }
    }

    @Test
    void testThrowOnGtValueForDouble() {
        final var paramName = "paramName";

        final var input = 0.123d;
        final var value = 0.122d;
        assertEquals(input, Check.throwOnGtValue(input, input, paramName));

        assertThrows(IllegalArgumentException.class,
                () -> Check.throwOnGtValue(input, value, paramName));
        try {
            Check.throwOnGtValue(input, value, paramName);
        }
        catch (IllegalArgumentException e) {
            assertEquals("inaugural.soliloquy.tools.tests.CheckTests" +
                            ".testThrowOnGtValueForDouble: " + paramName + " (" + input + ")" +
                            " cannot be greater than " + value,
                    e.getMessage());
        }
    }

    @Test
    void testThrowOnGteValueForShort() {
        final var paramName = "paramName";

        final var input = (short) 123;
        assertEquals(input, Check.throwOnGteValue(input, (short) (input + 1), paramName));

        assertThrows(IllegalArgumentException.class,
                () -> Check.throwOnGteValue(input, input, paramName));
        assertThrows(IllegalArgumentException.class,
                () -> Check.throwOnGteValue(input, (short) (input - 1), paramName));
        try {
            Check.throwOnGteValue(input, (short) (input - 1), paramName);
        }
        catch (IllegalArgumentException e) {
            assertEquals("inaugural.soliloquy.tools.tests.CheckTests" +
                            ".testThrowOnGteValueForShort: " + paramName + " (" + input + ")" +
                            " cannot be greater than " + (input - 1),
                    e.getMessage());
        }
    }

    @Test
    void testThrowOnGteValueForInt() {
        final var paramName = "paramName";

        final var input = 123;
        assertEquals(input, Check.throwOnGteValue(input, input + 1, paramName));

        assertThrows(IllegalArgumentException.class,
                () -> Check.throwOnGteValue(input, input, paramName));
        assertThrows(IllegalArgumentException.class,
                () -> Check.throwOnGteValue(input, input - 1, paramName));
        try {
            Check.throwOnGteValue(input, input - 1, paramName);
        }
        catch (IllegalArgumentException e) {
            assertEquals("inaugural.soliloquy.tools.tests.CheckTests" +
                            ".testThrowOnGteValueForInt: " + paramName + " (" + input + ")" +
                            " cannot be greater than " + (input - 1),
                    e.getMessage());
        }
    }

    @Test
    void testThrowOnGteValueForLong() {
        final var paramName = "paramName";

        final var input = 123L;
        assertEquals(input, Check.throwOnGteValue(input, input + 1, paramName));

        assertThrows(IllegalArgumentException.class,
                () -> Check.throwOnGteValue(input, input, paramName));
        assertThrows(IllegalArgumentException.class,
                () -> Check.throwOnGteValue(input, input - 1, paramName));
        try {
            Check.throwOnGteValue(input, input - 1, paramName);
        }
        catch (IllegalArgumentException e) {
            assertEquals("inaugural.soliloquy.tools.tests.CheckTests" +
                            ".testThrowOnGteValueForLong: " + paramName + " (" + input + ")" +
                            " cannot be greater than " + (input - 1),
                    e.getMessage());
        }
    }

    @Test
    void testThrowOnGteValueForFloat() {
        final var paramName = "paramName";

        final var input = 0.123f;
        assertEquals(input, Check.throwOnGteValue(input, input + 1, paramName));

        assertThrows(IllegalArgumentException.class,
                () -> Check.throwOnGteValue(input, input, paramName));
        assertThrows(IllegalArgumentException.class,
                () -> Check.throwOnGteValue(input, input - 1, paramName));
        try {
            Check.throwOnGteValue(input, input - 1, paramName);
        }
        catch (IllegalArgumentException e) {
            assertEquals("inaugural.soliloquy.tools.tests.CheckTests" +
                            ".testThrowOnGteValueForFloat: " + paramName + " (" + input + ")" +
                            " cannot be greater than " + (input - 1),
                    e.getMessage());
        }
    }

    @Test
    void testThrowOnGteValueForDouble() {
        final var paramName = "paramName";

        final var input = 0.123d;
        assertEquals(input, Check.throwOnGteValue(input, input + 1, paramName));

        assertThrows(IllegalArgumentException.class,
                () -> Check.throwOnGteValue(input, input, paramName));
        assertThrows(IllegalArgumentException.class,
                () -> Check.throwOnGteValue(input, input - 1, paramName));
        try {
            Check.throwOnGteValue(input, input - 1, paramName);
        }
        catch (IllegalArgumentException e) {
            assertEquals("inaugural.soliloquy.tools.tests.CheckTests" +
                            ".testThrowOnGteValueForDouble: " + paramName + " (" + input + ")" +
                            " cannot be greater than " + (input - 1),
                    e.getMessage());
        }
    }

    @Test
    void testThrowOnEqualsValueForShort() {
        final var paramName = "paramName";

        final var input = (short) 123;
        assertEquals(input, Check.throwOnEqualsValue(input, (short) (input + 1), paramName));

        assertThrows(IllegalArgumentException.class,
                () -> Check.throwOnEqualsValue(input, input, paramName));
        try {
            Check.throwOnEqualsValue(input, input, paramName);
        }
        catch (IllegalArgumentException e) {
            assertEquals("inaugural.soliloquy.tools.tests.CheckTests" +
                            ".testThrowOnEqualsValueForShort: " + paramName + " (" + input + ")" +
                            " cannot be equal to " + input,
                    e.getMessage());
        }
    }

    @Test
    void testThrowOnEqualsValueForInt() {
        final var paramName = "paramName";

        final var input = 123;
        assertEquals(input, Check.throwOnEqualsValue(input, input + 1, paramName));

        assertThrows(IllegalArgumentException.class,
                () -> Check.throwOnEqualsValue(input, input, paramName));
        try {
            Check.throwOnEqualsValue(input, input, paramName);
        }
        catch (IllegalArgumentException e) {
            assertEquals("inaugural.soliloquy.tools.tests.CheckTests" +
                            ".testThrowOnEqualsValueForInt: " + paramName + " (" + input + ")" +
                            " cannot be equal to " + input,
                    e.getMessage());
        }
    }

    @Test
    void testThrowOnEqualsValueForLong() {
        final var paramName = "paramName";

        final var input = 123L;
        assertEquals(input, Check.throwOnEqualsValue(input, input + 1, paramName));

        assertThrows(IllegalArgumentException.class,
                () -> Check.throwOnEqualsValue(input, input, paramName));
        try {
            Check.throwOnEqualsValue(input, input, paramName);
        }
        catch (IllegalArgumentException e) {
            assertEquals("inaugural.soliloquy.tools.tests.CheckTests" +
                            ".testThrowOnEqualsValueForLong: " + paramName + " (" + input + ")" +
                            " cannot be equal to " + input,
                    e.getMessage());
        }
    }

    @Test
    void testThrowOnEqualsValueForFloat() {
        final var paramName = "paramName";

        final var input = 0.123f;
        assertEquals(input, Check.throwOnEqualsValue(input, input + 1, paramName));

        assertThrows(IllegalArgumentException.class,
                () -> Check.throwOnEqualsValue(input, input, paramName));
        try {
            Check.throwOnEqualsValue(input, input, paramName);
        }
        catch (IllegalArgumentException e) {
            assertEquals("inaugural.soliloquy.tools.tests.CheckTests" +
                            ".testThrowOnEqualsValueForFloat: " + paramName + " (" + input + ")" +
                            " cannot be equal to " + input,
                    e.getMessage());
        }
    }

    @Test
    void testThrowOnEqualsValueForDouble() {
        final var paramName = "paramName";

        final var input = 0.123d;
        assertEquals(input, Check.throwOnEqualsValue(input, input + 1, paramName));

        assertThrows(IllegalArgumentException.class,
                () -> Check.throwOnEqualsValue(input, input, paramName));
        try {
            Check.throwOnEqualsValue(input, input, paramName);
        }
        catch (IllegalArgumentException e) {
            assertEquals("inaugural.soliloquy.tools.tests.CheckTests" +
                            ".testThrowOnEqualsValueForDouble: " + paramName + " (" + input + ")" +
                            " cannot be equal to " + input,
                    e.getMessage());
        }
    }

    @Test
    void testThrowOnSecondLteForShorts() {
        final var input1 = (short) 123;
        final var input2 = (short) 123;

        assertThrows(IllegalArgumentException.class, () ->
                Check.throwOnSecondLte(input1, input2, "input1", "input2"));
        try {
            Check.throwOnSecondLte(input1, input2, "input1", "input2");
        }
        catch (IllegalArgumentException e) {
            assertEquals("inaugural.soliloquy.tools.tests.CheckTests" +
                            ".testThrowOnSecondLteForShorts: input2 (" + input2 +
                            ") cannot be less than or equal to input1 (" + input1 + ")",
                    e.getMessage());
        }
    }

    @Test
    void testThrowOnSecondLteForInts() {
        final var input1 = 123;
        final var input2 = 123;

        assertThrows(IllegalArgumentException.class, () ->
                Check.throwOnSecondLte(input1, input2, "input1", "input2"));
        try {
            Check.throwOnSecondLte(input1, input2, "input1", "input2");
        }
        catch (IllegalArgumentException e) {
            assertEquals("inaugural.soliloquy.tools.tests.CheckTests" +
                            ".testThrowOnSecondLteForInts: input2 (" + input2 +
                            ") cannot be less than or equal to input1 (" + input1 + ")",
                    e.getMessage());
        }
    }

    @Test
    void testThrowOnSecondLteForLongs() {
        final var input1 = 123L;
        final var input2 = 123L;

        assertThrows(IllegalArgumentException.class, () ->
                Check.throwOnSecondLte(input1, input2, "input1", "input2"));
        try {
            Check.throwOnSecondLte(input1, input2, "input1", "input2");
        }
        catch (IllegalArgumentException e) {
            assertEquals("inaugural.soliloquy.tools.tests.CheckTests" +
                            ".testThrowOnSecondLteForLongs: input2 (" + input2 +
                            ") cannot be less than or equal to input1 (" + input1 + ")",
                    e.getMessage());
        }
    }

    @Test
    void testThrowOnSecondLteForFloats() {
        final var input1 = 123f;
        final var input2 = 123f;

        assertThrows(IllegalArgumentException.class, () ->
                Check.throwOnSecondLte(input1, input2, "input1", "input2"));
        try {
            Check.throwOnSecondLte(input1, input2, "input1", "input2");
        }
        catch (IllegalArgumentException e) {
            assertEquals("inaugural.soliloquy.tools.tests.CheckTests" +
                            ".testThrowOnSecondLteForFloats: input2 (" + input2 +
                            ") cannot be less than or equal to input1 (" + input1 + ")",
                    e.getMessage());
        }
    }

    @Test
    void testThrowOnSecondLteForDoubles() {
        final var input1 = 123d;
        final var input2 = 123d;

        assertThrows(IllegalArgumentException.class, () ->
                Check.throwOnSecondLte(input1, input2, "input1", "input2"));
        try {
            Check.throwOnSecondLte(input1, input2, "input1", "input2");
        }
        catch (IllegalArgumentException e) {
            assertEquals("inaugural.soliloquy.tools.tests.CheckTests" +
                            ".testThrowOnSecondLteForDoubles: input2 (" + input2 +
                            ") cannot be less than or equal to input1 (" + input1 + ")",
                    e.getMessage());
        }
    }

    @Test
    void testThrowOnSecondGtForShorts() {
        final short input1 = (short) 123;
        final short input2 = input1 + (short)1;

        assertThrows(IllegalArgumentException.class, () ->
                Check.throwOnSecondGt(input1, input2, "input1", "input2"));
        try {
            Check.throwOnSecondGt(input1, input2, "input1", "input2");
        }
        catch (IllegalArgumentException e) {
            assertEquals("inaugural.soliloquy.tools.tests.CheckTests" +
                            ".testThrowOnSecondGtForShorts: input2 (" + input2 +
                            ") cannot be greater than input1 (" + input1 + ")",
                    e.getMessage());
        }
    }

    @Test
    void testThrowOnSecondGtForInts() {
        final var input1 = 123;
        final var input2 = input1 + 1;

        assertThrows(IllegalArgumentException.class, () ->
                Check.throwOnSecondGt(input1, input2, "input1", "input2"));
        try {
            Check.throwOnSecondGt(input1, input2, "input1", "input2");
        }
        catch (IllegalArgumentException e) {
            assertEquals("inaugural.soliloquy.tools.tests.CheckTests" +
                            ".testThrowOnSecondGtForInts: input2 (" + input2 +
                            ") cannot be greater than input1 (" + input1 + ")",
                    e.getMessage());
        }
    }

    @Test
    void testThrowOnSecondGtForLongs() {
        final var input1 = 123L;
        final var input2 = input1 + 1;

        assertThrows(IllegalArgumentException.class, () ->
                Check.throwOnSecondGt(input1, input2, "input1", "input2"));
        try {
            Check.throwOnSecondGt(input1, input2, "input1", "input2");
        }
        catch (IllegalArgumentException e) {
            assertEquals("inaugural.soliloquy.tools.tests.CheckTests" +
                            ".testThrowOnSecondGtForLongs: input2 (" + input2 +
                            ") cannot be greater than input1 (" + input1 + ")",
                    e.getMessage());
        }
    }

    @Test
    void testThrowOnSecondGtForFloats() {
        final var input1 = 123f;
        final var input2 = input1 + 1;

        assertThrows(IllegalArgumentException.class, () ->
                Check.throwOnSecondGt(input1, input2, "input1", "input2"));
        try {
            Check.throwOnSecondGt(input1, input2, "input1", "input2");
        }
        catch (IllegalArgumentException e) {
            assertEquals("inaugural.soliloquy.tools.tests.CheckTests" +
                            ".testThrowOnSecondGtForFloats: input2 (" + input2 +
                            ") cannot be greater than input1 (" + input1 + ")",
                    e.getMessage());
        }
    }

    @Test
    void testThrowOnSecondGtForDoubles() {
        final var input1 = 123d;
        final var input2 = input1 + 1;

        assertThrows(IllegalArgumentException.class, () ->
                Check.throwOnSecondGt(input1, input2, "input1", "input2"));
        try {
            Check.throwOnSecondGt(input1, input2, "input1", "input2");
        }
        catch (IllegalArgumentException e) {
            assertEquals("inaugural.soliloquy.tools.tests.CheckTests" +
                            ".testThrowOnSecondGtForDoubles: input2 (" + input2 +
                            ") cannot be greater than input1 (" + input1 + ")",
                    e.getMessage());
        }
    }

    @Test
    void testIsBetweenZeroAndOne() {
        var input1 = -0.001f;
        var input2 = 1.001f;

        try {
            Check.isBetweenZeroAndOne(input1, "input1");
        }
        catch (IllegalArgumentException e) {
            assertEquals("inaugural.soliloquy.tools.tests.CheckTests" +
                            ".testIsBetweenZeroAndOne: input1 (" + input1 +
                            ") cannot be outside of the range of [0.0, 1.0]",
                    e.getMessage());
        }
        try {
            Check.isBetweenZeroAndOne(input2, "input2");
        }
        catch (IllegalArgumentException e) {
            assertEquals("inaugural.soliloquy.tools.tests.CheckTests" +
                            ".testIsBetweenZeroAndOne: input2 (" + input2 +
                            ") cannot be outside of the range of [0.0, 1.0]",
                    e.getMessage());
        }
    }

    @Test
    void testCheckMethodsFromConstructor() {
        try {
            new ConstructorExceptionMessageTester();
        }
        catch (IllegalArgumentException e) {
            assertEquals(
                    "inaugural.soliloquy.tools.tests" +
                            ".CheckTests$ConstructorExceptionMessageTester: " +
                            ConstructorExceptionMessageTester.ParamName + " cannot be null",
                    e.getMessage());
        }
    }

    @SuppressWarnings("InnerClassMayBeStatic")
    private class ConstructorExceptionMessageTester {
        private final static String ParamName = "ConstructorExceptionMessageTesterParamName";

        private ConstructorExceptionMessageTester() {
            Check.ifNull(null, ParamName);
        }
    }
}
