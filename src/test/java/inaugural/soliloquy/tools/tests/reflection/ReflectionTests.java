package inaugural.soliloquy.tools.tests.reflection;

import org.junit.jupiter.api.Test;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.entities.Function;

import static inaugural.soliloquy.tools.random.Random.randomInt;
import static inaugural.soliloquy.tools.random.Random.randomLong;
import static inaugural.soliloquy.tools.reflection.Reflection.readMethods;
import static org.junit.jupiter.api.Assertions.*;

public class ReflectionTests {
    @Test
    public void testReadActions() {
        var methods = readMethods(TestClass.class);

        assertNotNull(methods);
        var actions = methods.FIRST;
        assertEquals(1, actions.size());
        @SuppressWarnings({"OptionalGetWithoutIsPresent", "unchecked"}) Action<Integer> action =
                actions.stream().findFirst().get();
        assertEquals("theAction", action.id());
        var input = randomInt();
        action.accept(input);
        assertEquals(input, TestClass.TheActionInput);
    }

    @Test
    public void testReadFunctions() {
        var methods = readMethods(TestClass.class);

        assertNotNull(methods);
        var functions = methods.SECOND;
        assertEquals(1, functions.size());
        @SuppressWarnings({"OptionalGetWithoutIsPresent", "unchecked"}) Function<Long, String>
                function = functions.stream().findFirst().get();
        assertEquals("theFunction", function.id());
        var input = randomLong();
        var functionOutput = function.apply(input);
        assertEquals(input, TestClass.TheFunctionInput);
        assertEquals(((Long) input).toString(), functionOutput);
    }

    @Test
    public void testReadInstanceMethods() {
        var testClass = new TestClass();

        var methods = readMethods(testClass);

        assertNotNull(methods);
        var actions = methods.FIRST;
        assertEquals(1, actions.size());
        @SuppressWarnings({"OptionalGetWithoutIsPresent", "unchecked"}) Action<Integer> action =
                actions.stream().findFirst().get();
        assertEquals("theAction", action.id());
        var actionInput = randomInt();
        action.accept(actionInput);
        assertEquals(actionInput, TestClass.TheActionInput);

        assertNotNull(methods);
        var functions = methods.SECOND;
        assertEquals(1, functions.size());
        @SuppressWarnings({"OptionalGetWithoutIsPresent", "unchecked"}) Function<Long, String>
                function = functions.stream().findFirst().get();
        assertEquals("theFunction", function.id());
        var functionInput = randomLong();
        var functionOutput = function.apply(functionInput);
        assertEquals(functionInput, TestClass.TheFunctionInput);
        assertEquals(((Long) functionInput).toString(), functionOutput);
    }

    @Test
    public void testReadStaticMethods() {
        var methods = readMethods(TestClassWithStaticMethod.class);

        assertNotNull(methods);
        var actions = methods.FIRST;
        assertEquals(1, actions.size());
        @SuppressWarnings({"OptionalGetWithoutIsPresent", "unchecked"}) Action<Integer> action =
                actions.stream().findFirst().get();
        assertEquals("theStaticAction", action.id());
        var input = randomInt();
        action.accept(input);
        assertEquals(input, TestClassWithStaticMethod.TheStaticActionInput);
    }

    @Test
    public void testReadClassWithNoZeroParamConstructor() {
        assertThrows(IllegalArgumentException.class,
                () -> readMethods(TestClassNoZeroParamConstructor.class));
    }

    @Test
    public void testReadClassWithPrivateConstructor() {
        assertThrows(IllegalArgumentException.class,
                () -> readMethods(TestClassPrivateConstructor.class));
    }

    @Test
    public void testClassWithMultiParameterAction() {
        assertThrows(IllegalArgumentException.class,
                () -> readMethods(TestClassMultiParameterAction.class));
    }

    @Test
    public void testClassWithMultiParameterFunction() {
        assertThrows(IllegalArgumentException.class,
                () -> readMethods(TestClassMultiParameterFunction.class));
    }
}
