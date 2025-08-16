package inaugural.soliloquy.tools.timing;

import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.Tools;
import soliloquy.specs.common.shared.PausableAtTime;

public abstract class AbstractPausableAtTime implements PausableAtTime {
    protected final TimestampValidator TIMESTAMP_VALIDATOR;

    protected int periodModuloOffset;
    protected Long pausedTimestamp;

    public AbstractPausableAtTime(Long pausedTimestamp, TimestampValidator timestampValidator) {
        TIMESTAMP_VALIDATOR = Check.ifNull(timestampValidator, "timestampValidator");
        var mostRecentTimestamp = TIMESTAMP_VALIDATOR.mostRecentTimestamp();
        if (pausedTimestamp != null) {
            if (mostRecentTimestamp == null) {
                throw new IllegalArgumentException("AbstractPausableAtTime: cannot have null " +
                        "mostRecentTimestamp and non-null pausedTimestamp");
            }
            else if (pausedTimestamp > mostRecentTimestamp) {
                throw new IllegalArgumentException("AbstractPausableAtTime: pausedTimestamp (" +
                        pausedTimestamp + ") cannot be greater than mostRecentTimestamp (" +
                        mostRecentTimestamp + ")");
            }
        }
        this.pausedTimestamp = pausedTimestamp;
    }

    @Override
    public Long pausedTimestamp() {
        return pausedTimestamp;
    }

    @Override
    public void reportPause(long timestamp) throws IllegalArgumentException {
        var priorMostRecentTimestamp = TIMESTAMP_VALIDATOR.mostRecentTimestamp();
        TIMESTAMP_VALIDATOR.validateTimestamp(timestamp);
        if (pausedTimestamp != null) {
            throw new UnsupportedOperationException(Tools.callingClassName(2) + ".reportPause: " +
                    "cannot pause if already paused");
        }
        if (priorMostRecentTimestamp != null && timestamp < priorMostRecentTimestamp) {
            throw new IllegalArgumentException(Tools.callingClassName(2) +
                    ".reportPause: cannot pause at timestamp prior to most recent timestamp");
        }
        pausedTimestamp = timestamp;
    }

    @Override
    public void reportUnpause(long timestamp) throws IllegalArgumentException {
        var mostRecentTimestamp = TIMESTAMP_VALIDATOR.mostRecentTimestamp();
        TIMESTAMP_VALIDATOR.validateTimestamp(timestamp);
        if (pausedTimestamp == null) {
            throw new UnsupportedOperationException(Tools.callingClassName(2) +
                    ".reportUnpause: cannot unpause if already unpaused");
        }
        if (mostRecentTimestamp != null && timestamp < mostRecentTimestamp) {
            throw new IllegalArgumentException(
                    Tools.callingClassName(2) + ".reportUnpause: cannot unpause at timestamp (" +
                            timestamp + ") prior to most recent timestamp (" + mostRecentTimestamp +
                            ")");
        }

        updateInternalValuesOnUnpause(timestamp);

        pausedTimestamp = null;
    }

    protected abstract void updateInternalValuesOnUnpause(long timestamp);
}
