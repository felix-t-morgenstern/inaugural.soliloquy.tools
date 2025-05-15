package inaugural.soliloquy.tools.tests.abstractimplementations.timing;

import inaugural.soliloquy.tools.timing.AbstractPausableAtTime;

public class AbstractPausableAtTimeImpl extends AbstractPausableAtTime {
    public Long updateInternalValuesOnUnpauseInput = null;

    public AbstractPausableAtTimeImpl(Long pausedTimestamp, Long mostRecentTimestamp) {
        super(pausedTimestamp, mostRecentTimestamp);
    }

    @Override
    protected void updateInternalValuesOnUnpause(long timestamp) {
        updateInternalValuesOnUnpauseInput = timestamp;
    }
}
