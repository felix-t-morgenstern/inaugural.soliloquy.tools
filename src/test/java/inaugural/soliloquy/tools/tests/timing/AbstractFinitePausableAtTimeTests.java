package inaugural.soliloquy.tools.tests.timing;

import inaugural.soliloquy.tools.tests.abstractimplementations.timing.FinitePausableAtTimeImpl;
import inaugural.soliloquy.tools.timing.AbstractFinitePausableAtTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static inaugural.soliloquy.tools.random.Random.*;
import static org.junit.jupiter.api.Assertions.*;

class AbstractFinitePausableAtTimeTests {
    private final long PAUSED_TIMESTAMP = randomLong();
    private final long MOST_RECENT_TIMESTAMP = randomLongWithInclusiveFloor(PAUSED_TIMESTAMP + 1);
    private final long ANCHOR_TIME = randomLongWithInclusiveFloor(MOST_RECENT_TIMESTAMP + 1);

    private AbstractFinitePausableAtTime finitePausableAtTime;
    private AbstractFinitePausableAtTime finitePausableAtTimeWithAnchor;

    @BeforeEach
    void setUp() {
        finitePausableAtTime =
                new FinitePausableAtTimeImpl(PAUSED_TIMESTAMP, MOST_RECENT_TIMESTAMP);
        finitePausableAtTimeWithAnchor =
                new FinitePausableAtTimeImpl(ANCHOR_TIME, PAUSED_TIMESTAMP, MOST_RECENT_TIMESTAMP);
    }

    @Test
    void testConstructorWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () ->
                new FinitePausableAtTimeImpl(MOST_RECENT_TIMESTAMP + 1, MOST_RECENT_TIMESTAMP));
        assertThrows(IllegalArgumentException.class, () ->
                new FinitePausableAtTimeImpl(ANCHOR_TIME, MOST_RECENT_TIMESTAMP + 1,
                        MOST_RECENT_TIMESTAMP));
    }

    @Test
    void testPausedTimestamp() {
        assertEquals(PAUSED_TIMESTAMP, (long) finitePausableAtTime.pausedTimestamp());
        assertEquals(PAUSED_TIMESTAMP, (long) finitePausableAtTimeWithAnchor.pausedTimestamp());
    }

    @Test
    void testReportPauseAndUnpauseAndUpdateInternalValuesOnUnpause() {
        long unpauseTimestamp = randomLongInRange(MOST_RECENT_TIMESTAMP + 1, ANCHOR_TIME - 1);
        long secondPauseTimestamp = randomLongInRange(unpauseTimestamp + 1, ANCHOR_TIME - 1);

        assertThrows(UnsupportedOperationException.class, () ->
                finitePausableAtTime.reportPause(unpauseTimestamp));
        assertThrows(UnsupportedOperationException.class, () ->
                finitePausableAtTimeWithAnchor.reportPause(unpauseTimestamp));

        finitePausableAtTime.reportUnpause(unpauseTimestamp);
        finitePausableAtTimeWithAnchor.reportUnpause(unpauseTimestamp);

        assertNull(finitePausableAtTime.pausedTimestamp());
        assertNull(finitePausableAtTimeWithAnchor.pausedTimestamp());
        assertEquals(ANCHOR_TIME + (unpauseTimestamp - PAUSED_TIMESTAMP),
                ((FinitePausableAtTimeImpl) finitePausableAtTimeWithAnchor).getAnchorTime());

        assertThrows(UnsupportedOperationException.class, () ->
                finitePausableAtTime.reportUnpause(secondPauseTimestamp));
        assertThrows(UnsupportedOperationException.class, () ->
                finitePausableAtTimeWithAnchor.reportUnpause(secondPauseTimestamp));

        finitePausableAtTime.reportPause(secondPauseTimestamp);
        finitePausableAtTimeWithAnchor.reportPause(secondPauseTimestamp);

        assertEquals(secondPauseTimestamp, (long) finitePausableAtTime.pausedTimestamp());
        assertEquals(secondPauseTimestamp,
                (long) finitePausableAtTimeWithAnchor.pausedTimestamp());
    }
}
