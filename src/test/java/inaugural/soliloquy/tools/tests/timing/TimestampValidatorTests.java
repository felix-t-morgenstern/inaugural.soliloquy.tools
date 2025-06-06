package inaugural.soliloquy.tools.tests.timing;

import inaugural.soliloquy.tools.timing.TimestampValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TimestampValidatorTests {
    private final static long MOST_RECENT_TIMESTAMP = 123123L;

    private TimestampValidator _timestampValidator;

    @BeforeEach
    void setUp() {
        _timestampValidator = new TimestampValidator(null);
    }

    @Test
    public void testMostRecentTimestamp() {
        assertNull(_timestampValidator.mostRecentTimestamp());

        _timestampValidator.validateTimestamp(MOST_RECENT_TIMESTAMP);

        assertEquals(MOST_RECENT_TIMESTAMP, (long) _timestampValidator.mostRecentTimestamp());
    }

    @Test
    public void testValidateOutdatedTimestampWithoutExplicitClassName() {
        _timestampValidator.validateTimestamp(MOST_RECENT_TIMESTAMP + 1);

        try {
            _timestampValidator.validateTimestamp(MOST_RECENT_TIMESTAMP);
            fail("Should have thrown an exception");
        }
        catch (IllegalArgumentException e) {
            assertEquals("inaugural.soliloquy.tools.tests.timing." +
                            "TimestampValidatorTests" +
                            ".testValidateOutdatedTimestampWithoutExplicitClassName: " +
                            "provided outdated timestamp (" + MOST_RECENT_TIMESTAMP + ")",
                    e.getMessage());
        }
    }

    @Test
    public void testValidateOutdatedTimestampWithExplicitClassName() {
        _timestampValidator.validateTimestamp(MOST_RECENT_TIMESTAMP + 1);

        try {
            _timestampValidator.validateTimestamp(this.getClass().getCanonicalName(),
                    MOST_RECENT_TIMESTAMP);
            fail("Should have thrown an exception");
        }
        catch (IllegalArgumentException e) {
            assertEquals("inaugural.soliloquy.tools.tests.timing." +
                            "TimestampValidatorTests" +
                            ".testValidateOutdatedTimestampWithExplicitClassName: " +
                            "provided outdated timestamp (" + MOST_RECENT_TIMESTAMP + ")",
                    e.getMessage());
        }
    }
}
