package inaugural.soliloquy.tools.tests.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.function.Supplier;

import static inaugural.soliloquy.tools.exception.Exception.ignoreException;
import static inaugural.soliloquy.tools.random.Random.randomInt;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExceptionTests {
    @Mock private Runnable mockRunnable;
    @Mock private Supplier<Integer> mockSupplier;

    @Test
    public void testIgnoreExceptionWhenThrowing() {
        doThrow(new UnsupportedOperationException()).when(mockRunnable).run();

        var output = ignoreException(mockRunnable);

        assertInstanceOf(UnsupportedOperationException.class, output);
    }

    @Test
    public void testIgnoreExceptionWhenNotThrowing() {
        var output = ignoreException(mockRunnable);

        assertNull(output);
    }

    @Test
    public void testIgnoreExceptionWithOutputWhenThrowing() {
        doThrow(new UnsupportedOperationException()).when(mockSupplier).get();

        var output = ignoreException(mockSupplier);

        assertNotNull(output);
        assertNull(output.value());
        assertInstanceOf(UnsupportedOperationException.class, output.exception());
    }

    @Test
    public void testIgnoreExceptionWithOutputWhenNotThrowing() {
        var supplierReturn = randomInt();
        when(mockSupplier.get()).thenReturn(supplierReturn);

        var output = ignoreException(mockSupplier);

        assertNotNull(output);
        assertEquals(supplierReturn, output.value());
        assertNull(output.exception());
    }
}
