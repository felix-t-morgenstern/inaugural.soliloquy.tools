package inaugural.soliloquy.tools.tests.abstractimplementations.timing;

import inaugural.soliloquy.tools.timing.AbstractPausableAtTime;
import inaugural.soliloquy.tools.timing.TimestampValidator;

public class AbstractPausableAtTimeImpl extends AbstractPausableAtTime {
    public Long updateInternalValuesOnUnpauseInput = null;

    public AbstractPausableAtTimeImpl(Long pausedTimestamp, TimestampValidator timestampValidator) {
        super(pausedTimestamp, timestampValidator);
    }

    @Override
    protected void updateInternalValuesOnUnpause(long timestamp) {
        updateInternalValuesOnUnpauseInput = timestamp;
    }
}
