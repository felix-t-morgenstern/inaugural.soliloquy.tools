package inaugural.soliloquy.tools.tests.timing;

import inaugural.soliloquy.tools.tests.abstractimplementations.timing.AbstractPausableAtTimeImpl;
import inaugural.soliloquy.tools.timing.TimestampValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static inaugural.soliloquy.tools.random.Random.randomLong;
import static inaugural.soliloquy.tools.random.Random.randomLongWithInclusiveFloor;
import static inaugural.soliloquy.tools.testing.Assertions.once;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AbstractPausableAtTimeTests {
    private final long PAUSED_TIMESTAMP = randomLong();
    private final long MOST_RECENT_TIMESTAMP = randomLongWithInclusiveFloor(PAUSED_TIMESTAMP + 1);

    @Mock private TimestampValidator mockTimestampValidator;

    private AbstractPausableAtTimeImpl abstractPausableAtTime;

    @BeforeEach
    void setUp() {
        lenient().when(mockTimestampValidator.mostRecentTimestamp())
                .thenReturn(MOST_RECENT_TIMESTAMP);

        abstractPausableAtTime =
                new AbstractPausableAtTimeImpl(PAUSED_TIMESTAMP, mockTimestampValidator);
    }

    @Test
    public void testConstructorWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () ->
                new AbstractPausableAtTimeImpl(PAUSED_TIMESTAMP, null));
        assertThrows(IllegalArgumentException.class, () ->
                new AbstractPausableAtTimeImpl(MOST_RECENT_TIMESTAMP + 1, mockTimestampValidator));
    }

    @Test
    public void testPausedTimestamp() {
        assertEquals(PAUSED_TIMESTAMP, (long) abstractPausableAtTime.pausedTimestamp());
    }

    @Test
    public void testReportPauseValidatesTimestamps() {
        var unpausedPausable = new AbstractPausableAtTimeImpl(null, mockTimestampValidator);

        unpausedPausable.reportPause(MOST_RECENT_TIMESTAMP);

        verify(mockTimestampValidator, once()).validateTimestamp(MOST_RECENT_TIMESTAMP);
    }

    @Test
    public void testReportUnpauseValidatesTimestamps() {
        abstractPausableAtTime.reportUnpause(MOST_RECENT_TIMESTAMP);

        verify(mockTimestampValidator, once()).validateTimestamp(MOST_RECENT_TIMESTAMP);
    }

    @Test
    public void testReportPauseWhilePausedAndUnpausedWhileUnpaused() {
        assertThrows(UnsupportedOperationException.class, () ->
                abstractPausableAtTime.reportPause(MOST_RECENT_TIMESTAMP));

        abstractPausableAtTime.reportUnpause(MOST_RECENT_TIMESTAMP);

        assertThrows(UnsupportedOperationException.class, () ->
                abstractPausableAtTime.reportUnpause(MOST_RECENT_TIMESTAMP));
    }

    @Test
    public void testReportUnpauseCallsUpdateInternalValuesOnUnpause() {
        var unpauseTimestamp = randomLongWithInclusiveFloor(MOST_RECENT_TIMESTAMP + 1);

        abstractPausableAtTime.reportUnpause(unpauseTimestamp);

        assertEquals(unpauseTimestamp,
                (long) abstractPausableAtTime.updateInternalValuesOnUnpauseInput);
    }
}
