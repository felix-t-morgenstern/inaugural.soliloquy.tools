package inaugural.soliloquy.tools.exception;

import java.util.function.Supplier;

public class Exception {
    public static java.lang.Exception ignoreException(Runnable runnable) {
        try {
            runnable.run();
        }
        catch (java.lang.Exception e) {
            return e;
        }
        return null;
    }

    public static <T> Result<T> ignoreException(Supplier<T> supplier) {
        try {
            return new Result<>(supplier.get(), null);
        }
        catch (java.lang.Exception e) {
            return new Result<>(null, e);
        }
    }

    public record Result<T>(T value, java.lang.Exception exception) {
    }
}
