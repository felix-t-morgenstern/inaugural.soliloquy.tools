package inaugural.soliloquy.tools.tests.testing;

import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import java.util.ArrayList;

import static inaugural.soliloquy.tools.collections.Collections.mapOf;
import static inaugural.soliloquy.tools.random.Random.randomFloatBox;
import static inaugural.soliloquy.tools.testing.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static soliloquy.specs.common.valueobjects.FloatBox.floatBoxOf;

public class AssertionsTests {
    @Test
    public void testAssertEqualsAndNotSame() {
        ArrayList<Integer> list1 = new ArrayList<>() {{
            add(1);
            add(2);
            add(3);
        }};
        ArrayList<Integer> list2 = new ArrayList<>(list1);

        assertEqualsAndNotSame(list1, list2);
        assertThrows(AssertionFailedError.class, () -> assertEqualsAndNotSame(list1, list1));
    }

    @Test
    public void testAssertOnlyContains() {
        assertThrows(AssertionFailedError.class, () -> assertOnlyContains(new ArrayList<>() {{
            add(0);
            add(1);
        }}, 0));
        assertThrows(AssertionFailedError.class, () -> assertOnlyContains(new ArrayList<>() {{
            add(1);
        }}, 0));
        assertThrows(AssertionFailedError.class, () -> assertOnlyContains(new ArrayList<>(), 0));
        assertThrows(AssertionFailedError.class, () -> assertOnlyContains(new ArrayList<>() {{
            add(new Object());
        }}, new Object()));
    }

    @Test
    public void testAssertThrowsWithMessage() {
        var message = "message";
        Runnable action = () -> {throw new IllegalArgumentException(message);};
        var type = IllegalArgumentException.class;

        assertThrowsWithMessage(action, type, message);
        assertThrows(AssertionFailedError.class,
                () -> assertThrowsWithMessage(() -> {}, type, message));
        assertThrows(AssertionFailedError.class,
                () -> assertThrowsWithMessage(() -> {throw new IllegalStateException();}, type,
                        message));
        assertThrows(AssertionFailedError.class,
                () -> assertThrowsWithMessage(action, IllegalStateException.class, message));
        assertThrows(AssertionFailedError.class,
                () -> assertThrowsWithMessage(action, type, "unexpectedMessage"));
    }

    @Test
    public void testAssertThrowsWithMessageWithInvalidArgs() {
        var message = "message";

        assertThrows(IllegalArgumentException.class,
                () -> assertThrowsWithMessage(null, IllegalArgumentException.class, message));
        assertThrows(IllegalArgumentException.class,
                () -> assertThrowsWithMessage(() -> {}, null, message));
        assertThrows(IllegalArgumentException.class,
                () -> assertThrowsWithMessage(() -> {}, IllegalArgumentException.class, null));
        assertThrows(IllegalArgumentException.class,
                () -> assertThrowsWithMessage(() -> {}, IllegalArgumentException.class, ""));
    }

    @Test
    public void testAssertMapContainsEquals() {
        var key = "key";
        var val = "val";
        var map = mapOf(key, val);

        assertDoesNotThrow(() -> assertMapContainsEquals(map, key, val));
        assertThrows(AssertionFailedError.class,
                () -> assertMapContainsEquals(map, "not the key", val));
        assertThrows(AssertionFailedError.class,
                () -> assertMapContainsEquals(map, key, "not the val"));
    }

    @Test
    public void testAssertMapContainsSame() {
        var key = "key";
        var val = "val";
        var map = mapOf(key, val);

        //noinspection StringOperationCanBeSimplified
        assertThrows(AssertionFailedError.class,
                () -> assertMapContainsSame(map, key, new String(val)));
    }

    @Test
    public void testAssertMapContainsNull() {
        var key = "key";
        var val = "val";
        var map = mapOf(key, val);

        assertThrows(AssertionFailedError.class, () -> assertMapContainsNull(map, key));
    }

    @Test
    public void testAssertFloatBoxesEqual() {
        var floatBoxExpected = randomFloatBox();
        var floatBoxEqual = floatBoxOf(
                floatBoxExpected.LEFT_X,
                floatBoxExpected.TOP_Y,
                floatBoxExpected.RIGHT_X,
                floatBoxExpected.BOTTOM_Y
        );
        var floatBoxUnequal = floatBoxOf(
                floatBoxExpected.LEFT_X / 2f,
                floatBoxExpected.TOP_Y,
                floatBoxExpected.RIGHT_X,
                floatBoxExpected.BOTTOM_Y
        );

        assertFloatBoxesEqual(null, null);
        assertFloatBoxesEqual(floatBoxExpected, floatBoxEqual);
        assertThrows(AssertionFailedError.class, () -> assertFloatBoxesEqual(null, floatBoxEqual));
        assertThrows(AssertionFailedError.class, () -> assertFloatBoxesEqual(floatBoxExpected, null));
        assertThrows(AssertionFailedError.class, () -> assertFloatBoxesEqual(floatBoxExpected, floatBoxUnequal));
    }
}
