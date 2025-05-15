package inaugural.soliloquy.tools.tests.timing;

import inaugural.soliloquy.tools.tests.abstractimplementations.timing.AbstractPausableAtTimeImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static inaugural.soliloquy.tools.random.Random.randomLong;
import static inaugural.soliloquy.tools.random.Random.randomLongWithInclusiveFloor;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AbstractPausableAtTimeTests {
    private final long PAUSED_TIMESTAMP = randomLong();
    private final long MOST_RECENT_TIMESTAMP = randomLongWithInclusiveFloor(PAUSED_TIMESTAMP + 1);

    private AbstractPausableAtTimeImpl abstractPausableAtTime;

    @BeforeEach
    void setUp() {
        abstractPausableAtTime =
                new AbstractPausableAtTimeImpl(PAUSED_TIMESTAMP, MOST_RECENT_TIMESTAMP);
    }

    @Test
    void testConstructorWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () ->
                new AbstractPausableAtTimeImpl(PAUSED_TIMESTAMP, null));
        assertThrows(IllegalArgumentException.class, () ->
                new AbstractPausableAtTimeImpl(MOST_RECENT_TIMESTAMP + 1, MOST_RECENT_TIMESTAMP));
    }

    @Test
    void testPausedTimestamp() {
        assertEquals(PAUSED_TIMESTAMP, (long) abstractPausableAtTime.pausedTimestamp());
    }

    @Test
    void testReportPauseOrUnpauseWithOutdatedTimestamp() {
        assertThrows(IllegalArgumentException.class, () ->
                abstractPausableAtTime.reportPause(MOST_RECENT_TIMESTAMP - 1));
        assertThrows(IllegalArgumentException.class, () ->
                abstractPausableAtTime.reportUnpause(MOST_RECENT_TIMESTAMP - 1));
    }

    @Test
    void testReportPauseWhilePausedAndUnpausedWhileUnpaused() {
        assertThrows(UnsupportedOperationException.class, () ->
                abstractPausableAtTime.reportPause(MOST_RECENT_TIMESTAMP));

        abstractPausableAtTime.reportUnpause(MOST_RECENT_TIMESTAMP);

        assertThrows(UnsupportedOperationException.class, () ->
                abstractPausableAtTime.reportUnpause(MOST_RECENT_TIMESTAMP));
    }

    @Test
    void testReportUnpauseCallsUpdateInternalValuesOnUnpause() {
        long unpauseTimestamp = randomLongWithInclusiveFloor(MOST_RECENT_TIMESTAMP + 1);

        abstractPausableAtTime.reportUnpause(unpauseTimestamp);

        assertEquals(unpauseTimestamp,
                (long) abstractPausableAtTime.updateInternalValuesOnUnpauseInput);
    }
}
