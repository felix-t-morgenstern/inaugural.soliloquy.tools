package inaugural.soliloquy.tools.tests;

import inaugural.soliloquy.tools.Tools;
import inaugural.soliloquy.tools.tests.fakes.PassthroughRunnable;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.shared.HasPriority;

import static inaugural.soliloquy.tools.Tools.*;
import static inaugural.soliloquy.tools.collections.Collections.listOf;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ToolsTests {
    private static String callingClassName;

    @Test
    public void testDefaultIfNull() {
        var val = 123;
        var theDefault = 456;

        assertEquals(val, defaultIfNull(val, theDefault));
        assertEquals(theDefault, defaultIfNull(null, theDefault));
    }

    @Test
    public void testDefaultIfNullWithTransform() {
        var base = 123;
        var theDefault = "default";

        assertEquals("" + base, defaultIfNull(base, Object::toString, theDefault));
        assertEquals(theDefault, defaultIfNull(null, Object::toString, theDefault));
    }

    @Test
    public void testDefaultIfNullWithNullTransform() {
        assertThrows(IllegalArgumentException.class, () -> defaultIfNull(null, null, null));
    }

    @Test
    public void testFalseIfNull() {
        var val = true;

        assertTrue(falseIfNull(val));
        assertFalse(falseIfNull(null));
    }

    @Test
    public void testEmptyIfNull() {
        var input = "input";

        assertEquals(input, Tools.emptyIfNull(input));

        assertEquals("", Tools.emptyIfNull(null));
    }

    @Test
    public void testProvideIfNull() {
        var val = 123;
        var theDefault = 456;

        assertEquals(val, provideIfNull(val, () -> theDefault));
        assertEquals(theDefault, provideIfNull(null, () -> theDefault));
    }

    @Test
    public void testNullIfEmpty() {
        var input = "input";

        assertEquals(input, Tools.nullIfEmpty(input));

        assertNull(Tools.nullIfEmpty(""));
    }

    @Test
    public void testRound() {
        var value = 1.23456f;
        var places = 3;

        assertEquals(1.235f, Tools.round(value, places));
        assertEquals(-1.235f, Tools.round(-value, places));

        assertThrows(IllegalArgumentException.class, () -> Tools.round(value, -1));
    }

    @Test
    public void testConstrainInts() {
        var inclusiveMin = 10;
        var inclusiveMax = 50;

        assertEquals(inclusiveMin, constrain(9, inclusiveMin, inclusiveMax));
        assertEquals(30, constrain(30, inclusiveMin, inclusiveMax));
        assertEquals(inclusiveMax, constrain(51, inclusiveMin, inclusiveMax));
    }

    @Test
    public void testConstrainIntsWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> constrain(0, 1, 0));
    }

    @Test
    public void testCallingClassName() {
        callingClassName = Tools.callingClassName();

        assertEquals(this.getClass().getCanonicalName(), callingClassName);
    }

    @Test
    public void testCallingClassNameWithStepsToMoveUp() {
        var setCallingClassName = new PassthroughRunnable(() ->
                callingClassName = Tools.callingClassName(6));
        var level1 = new PassthroughRunnable(setCallingClassName::call);
        var level2 = new PassthroughRunnable(level1::call);

        level2.call();

        assertEquals(this.getClass().getCanonicalName(), callingClassName);
    }

    @Test
    public void testOrderByPriority() {
        var firstPriority = 1;
        var secondPriority = 0;
        var hasPriority1 = mock(HasPriority.class);
        when(hasPriority1.priority()).thenReturn(firstPriority);
        var hasPriority2 = mock(HasPriority.class);
        when(hasPriority2.priority()).thenReturn(firstPriority);
        var hasPriority3 = mock(HasPriority.class);
        when(hasPriority3.priority()).thenReturn(secondPriority);

        var orderedByPriority =
                Tools.orderByPriority(listOf(hasPriority1, hasPriority2, hasPriority3));

        assertNotNull(orderedByPriority);
        verify(hasPriority1, atLeast(1)).priority();
        verify(hasPriority2, atLeast(1)).priority();
        verify(hasPriority3, atLeast(1)).priority();
        assertEquals(3, orderedByPriority.size());
        assertTrue(orderedByPriority.get(0) == hasPriority1 || orderedByPriority.get(0) == hasPriority2);
        assertTrue(orderedByPriority.get(1) == hasPriority1 || orderedByPriority.get(1) == hasPriority2);
        assertSame(hasPriority3, orderedByPriority.get(2));
    }
}
