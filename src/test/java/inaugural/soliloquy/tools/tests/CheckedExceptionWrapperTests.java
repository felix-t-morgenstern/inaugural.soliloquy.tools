package inaugural.soliloquy.tools.tests;

import inaugural.soliloquy.tools.CheckedExceptionWrapper;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Callable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class CheckedExceptionWrapperTests {
    @Test
    public void testWrapThrowingCallable() {
        String exceptionString = "exceptionString";
        Callable<Boolean> throwingCallable = () -> {
            throw new UnsupportedOperationException(exceptionString);
        };

        try {
            CheckedExceptionWrapper.callWrapped(throwingCallable);
        }
        catch (RuntimeException e) {
            Throwable innerThrowable = e.getCause();
            if (innerThrowable instanceof UnsupportedOperationException) {
                UnsupportedOperationException innerException =
                        (UnsupportedOperationException) innerThrowable;

                assertEquals(exceptionString, innerException.getMessage());
            }
            else {
                fail("Inner exception of incorrect type");
            }
        }
        catch (Exception e) {
            fail("RuntimeException not thrown");
        }
    }

    @Test
    public void testWrapNonthrowingCallable() {
        int valueToReturn = 123123;
        Callable<Integer> nonthrowingCallable = () -> valueToReturn;

        assertEquals(valueToReturn, (int) CheckedExceptionWrapper.callWrapped(nonthrowingCallable));
    }
}
