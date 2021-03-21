package inaugural.soliloquy.tools;

import java.util.concurrent.Callable;

public class CheckedExceptionWrapper {
    public static <T> T callWrapped(Callable<T> callable) {
        try {
            return Check.ifNull(callable, "callable").call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
