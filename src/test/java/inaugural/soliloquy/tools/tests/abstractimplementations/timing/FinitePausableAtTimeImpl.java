package inaugural.soliloquy.tools.tests.abstractimplementations.timing;

import inaugural.soliloquy.tools.timing.AbstractFinitePausableAtTime;

public class FinitePausableAtTimeImpl extends AbstractFinitePausableAtTime {
    public FinitePausableAtTimeImpl(Long pausedTimestamp, Long mostRecentTimestamp) {
        super(pausedTimestamp, mostRecentTimestamp);
    }

    public FinitePausableAtTimeImpl(Long pausedTimestamp, Long mostRecentTimestamp,
                                    Long anchorTime) {
        super(pausedTimestamp, mostRecentTimestamp, anchorTime);
    }

    public long getAnchorTime() {
        return anchorTime;
    }
}
