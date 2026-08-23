package inaugural.soliloquy.tools.tests.timing;

import inaugural.soliloquy.tools.timing.TimestampValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TimestampValidatorTests {
    private final static long MOST_RECENT_TIMESTAMP = 123123L;

    private TimestampValidator timestampValidator;

    @BeforeEach
    void setUp() {
        timestampValidator = new TimestampValidator(null);
    }

    @Test
    public void testMostRecentTimestamp() {
        assertNull(timestampValidator.mostRecentTimestamp());

        timestampValidator.validateTimestamp(MOST_RECENT_TIMESTAMP);

        assertEquals(MOST_RECENT_TIMESTAMP, (long) timestampValidator.mostRecentTimestamp());
    }

    @Test
    public void testValidateOutdatedTimestampWithoutExplicitClassName() {
        var nextTimestamp = MOST_RECENT_TIMESTAMP + 1;
        timestampValidator.validateTimestamp(nextTimestamp);

        try {
            timestampValidator.validateTimestamp(MOST_RECENT_TIMESTAMP);
            fail("Should have thrown an exception");
        }
        catch (IllegalArgumentException e) {
            assertEquals(
                    "inaugural.soliloquy.tools.tests.timing.TimestampValidatorTests" +
                            ".testValidateOutdatedTimestampWithoutExplicitClassName: provided " +
                            "timestamp (" +
                            MOST_RECENT_TIMESTAMP + ") prior to most recent (" + nextTimestamp +
                            ")",
                    e.getMessage());
        }
    }

    @Test
    public void testValidateOutdatedTimestampWithExplicitClassName() {
        timestampValidator.validateTimestamp(MOST_RECENT_TIMESTAMP + 1);

        try {
            timestampValidator.validateTimestamp(this.getClass().getCanonicalName(),
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
