package inaugural.soliloquy.tools.timing;

// TODO: Test this implementation taken from Graphics
public class TimestampValidator {
    private Long mostRecentTimestamp;

    public TimestampValidator(Long mostRecentTimestamp) {
        this.mostRecentTimestamp = mostRecentTimestamp;
    }

    public void validateTimestamp(long timestamp) {
        if (mostRecentTimestamp != null && timestamp < mostRecentTimestamp) {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

            throw new IllegalArgumentException(stackTrace[2].getClassName() + "." +
                    stackTrace[2].getMethodName() + ": provided outdated timestamp (" + timestamp +
                    ")");
        }
        mostRecentTimestamp = timestamp;
    }

    public void validateTimestamp(String className, long timestamp) {
        if (mostRecentTimestamp != null && timestamp < mostRecentTimestamp) {
            String invokingClass = "undiscoveredClass";
            String invokingMethod = "undiscoveredMethod";

            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            for (StackTraceElement element : stackTrace) {
                if (element.getClassName().equals(className)) {
                    invokingClass = element.getClassName();
                    invokingMethod = element.getMethodName();

                    break;
                }
            }

            throw new IllegalArgumentException(invokingClass + "." + invokingMethod +
                    ": provided outdated timestamp (" + timestamp + ")");
        }
        mostRecentTimestamp = timestamp;
    }

    public Long mostRecentTimestamp() {
        return mostRecentTimestamp;
    }
}
