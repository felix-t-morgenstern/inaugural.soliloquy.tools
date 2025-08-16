package inaugural.soliloquy.tools.timing;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.shared.HasPeriodModuloOffset;
import soliloquy.specs.common.shared.PausableAtTime;

// TODO: Test this as a stub implementation, and not merely when extended in other modules
public abstract class AbstractLoopingPausableAtTime
        extends AbstractPausableAtTime
        implements PausableAtTime, HasPeriodModuloOffset {
    protected final int PERIOD_DURATION;

    public AbstractLoopingPausableAtTime(int periodDuration,
                                         int periodModuloOffset,
                                         Long pausedTimestamp,
                                         TimestampValidator timestampValidator) {
        super(pausedTimestamp, timestampValidator);
        if (periodModuloOffset >= periodDuration) {
            throw new IllegalArgumentException("AbstractLoopingPausableAtTime: " +
                    "periodModuloOffset (" + periodModuloOffset + ") cannot be greater than " +
                    "period duration (" + periodDuration + ")");
        }
        PERIOD_DURATION = periodDuration;
        this.periodModuloOffset = Check.throwOnLtValue(periodModuloOffset, 0, "periodModuloOffset");
    }

    @Override
    public int periodModuloOffset() {
        return periodModuloOffset;
    }

    @Override
    protected void updateInternalValuesOnUnpause(long timestamp) {
        periodModuloOffset = (int) ((periodModuloOffset - (timestamp - pausedTimestamp)
                + PERIOD_DURATION) % PERIOD_DURATION);
        while (periodModuloOffset < 0) {
            periodModuloOffset += PERIOD_DURATION;
        }
    }
}
