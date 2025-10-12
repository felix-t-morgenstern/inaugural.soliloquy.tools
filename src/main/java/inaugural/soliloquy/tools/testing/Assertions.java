package inaugural.soliloquy.tools.testing;

import inaugural.soliloquy.tools.Check;
import org.mockito.verification.VerificationMode;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

public class Assertions {
    public static <T> void assertEqualsAndNotSame(T expected, T actual) {
        assertEquals(expected, actual);
        assertNotSame(expected, actual);
    }

    public static <T> void assertOnlyContains(List<T> list, T item) {
        assertEquals(1, list.size());
        if (item instanceof Integer ||
                item instanceof Long ||
                item instanceof Float ||
                item instanceof Double ||
                item instanceof UUID) {
            assertEquals(item, list.getFirst());
        }
        else {
            assertSame(item, list.getFirst());
        }
    }

    public static <T extends Exception> void assertThrowsWithMessage(Runnable action,
                                                                     Class<T> exceptionType,
                                                                     String expectedMessage) {
        Check.ifNull(action, "action");
        Check.ifNull(exceptionType, "exceptionType");
        Check.ifNullOrEmpty(expectedMessage, "expectedMessage");

        try {
            action.run();
            fail();
        }
        catch (Exception e) {
            assertSame(exceptionType, e.getClass());
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    public static <K,V> void assertMapContainsEquals(Map<K,V> map, K expectedKey, V expectedVal) {
        assertTrue(map.containsKey(expectedKey));
        assertEquals(expectedVal, map.get(expectedKey));
    }

    public static <K,V> void assertMapContainsSame(Map<K,V> map, K expectedKey, V expectedVal) {
        assertTrue(map.containsKey(expectedKey));
        assertSame(expectedVal, map.get(expectedKey));
    }

    public static <K,V> void assertMapContainsNull(Map<K,V> map, K expectedKey) {
        assertTrue(map.containsKey(expectedKey));
        assertNull(map.get(expectedKey));
    }

    public static VerificationMode once() {
        return times(1);
    }
}
