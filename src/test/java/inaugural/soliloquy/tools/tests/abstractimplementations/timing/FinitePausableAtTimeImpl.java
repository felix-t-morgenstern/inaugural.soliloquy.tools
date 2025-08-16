package inaugural.soliloquy.tools.tests.abstractimplementations.timing;

import inaugural.soliloquy.tools.timing.AbstractFinitePausableAtTime;
import inaugural.soliloquy.tools.timing.TimestampValidator;

public class FinitePausableAtTimeImpl extends AbstractFinitePausableAtTime {
    public FinitePausableAtTimeImpl(Long pausedTimestamp, TimestampValidator timestampValidator) {
        super(pausedTimestamp, timestampValidator);
    }

    public FinitePausableAtTimeImpl(Long anchorTime,
                                    Long pausedTimestamp,
                                    TimestampValidator timestampValidator) {
        super(anchorTime, pausedTimestamp, timestampValidator);
    }

    public long getAnchorTime() {
        return anchorTime;
    }
}
