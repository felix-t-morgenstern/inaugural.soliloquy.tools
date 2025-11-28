package inaugural.soliloquy.tools.tests.reflection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.entities.*;

import static inaugural.soliloquy.tools.random.Random.*;
import static inaugural.soliloquy.tools.reflection.Reflection.readMethods;
import static org.junit.jupiter.api.Assertions.*;

public class ReflectionTests {
    @BeforeEach
    public void setUp() {
        TestClass.TheRunnableExecutedCount = 0;
        TestClass.TheSupplierExecutedCount = 0;
        TestClass.TheConsumerInput = randomInt();
        TestClass.TheBiConsumerInput1 = randomString();
        TestClass.TheBiConsumerInput2 = randomInt();
        TestClass.TheFunctionInput = randomLong();
        TestClass.TheBiFunctionInput1 = randomString();
        TestClass.TheBiFunctionInput2 = randomDouble();
    }

    @Test
    public void testReadClassMethods() {
        var methods = readMethods(TestClass.class);

        assertNotNull(methods);

        assertMethodsRead(methods);
    }

    @Test
    public void testReadInstanceMethods() {
        var testClass = new TestClass();

        var methods = readMethods(testClass);

        assertMethodsRead(methods);
    }

    private void assertMethodsRead(Methods methods) {
        assertNotNull(methods);

        assertEquals(2, methods.RUNNABLES.size());
        var runnable = methods.RUNNABLES.get("theRunnable");
        runnable.run();
        assertEquals(1, TestClass.TheRunnableExecutedCount);
        var runnableFromSupplierInput = methods.RUNNABLES.get("theSupplier");
        runnableFromSupplierInput.run();
        assertEquals(1, TestClass.TheSupplierExecutedCount);

        assertEquals(1, methods.SUPPLIERS.size());
        var supplier = methods.SUPPLIERS.get("theSupplier");
        var supplied = supplier.get();
        assertEquals(TestClass.TheSupplierOutput, supplied);
        assertEquals(2, TestClass.TheSupplierExecutedCount);

        assertEquals(2, methods.CONSUMERS.size());
        @SuppressWarnings("unchecked") Consumer<Integer> consumer =
                methods.CONSUMERS.get("theConsumer");
        var consumerInput = randomInt();
        consumer.accept(consumerInput);
        assertEquals(consumerInput, TestClass.TheConsumerInput);
        @SuppressWarnings("unchecked") Consumer<Long> consumerFromFunction =
                methods.CONSUMERS.get("theFunction");
        var consumerFromFunctionInput = randomLong();
        consumerFromFunction.accept(consumerFromFunctionInput);
        assertEquals(consumerFromFunctionInput, TestClass.TheFunctionInput);

        assertEquals(2, methods.BICONSUMERS.size());
        @SuppressWarnings("unchecked") BiConsumer<String, Integer> biConsumer =
                methods.BICONSUMERS.get("theBiConsumer");
        var biConsumerInput1 = randomString();
        var biConsumerInput2 = randomInt();
        biConsumer.accept(biConsumerInput1, biConsumerInput2);
        assertEquals(biConsumerInput1, TestClass.TheBiConsumerInput1);
        assertEquals(biConsumerInput2, TestClass.TheBiConsumerInput2);
        @SuppressWarnings("unchecked") BiConsumer<String, Double> biConsumerFromBiFunction =
                methods.BICONSUMERS.get("theBiFunction");
        var biConsumerFromFunctionInput1 = randomString();
        var biConsumerFromFunctionInput2 = randomDouble();
        biConsumerFromBiFunction.accept(biConsumerFromFunctionInput1, biConsumerFromFunctionInput2);
        assertEquals(biConsumerFromFunctionInput1, TestClass.TheBiFunctionInput1);
        assertEquals(biConsumerFromFunctionInput2, TestClass.TheBiFunctionInput2);

        assertEquals(1, methods.FUNCTIONS.size());
        @SuppressWarnings("unchecked") Function<Long, String> function =
                methods.FUNCTIONS.get("theFunction");
        var functionInput = randomLong();
        var functionOutput = function.apply(functionInput);
        assertEquals(functionInput, TestClass.TheFunctionInput);
        assertEquals(((Long) functionInput).toString(), functionOutput);

        assertEquals(1, methods.FUNCTIONS.size());
        @SuppressWarnings("unchecked") BiFunction<String, Double, String> biFunction =
                methods.BIFUNCTIONS.get("theBiFunction");
        var biFunctionInput1 = randomString();
        var biFunctionInput2 = randomDouble();
        var biFunctionOutput = biFunction.apply(biFunctionInput1, biFunctionInput2);
        assertEquals(biFunctionInput1, TestClass.TheBiFunctionInput1);
        assertEquals(biFunctionInput2, TestClass.TheBiFunctionInput2);
        assertEquals(biFunctionInput1 + biFunctionInput2, biFunctionOutput);
    }

    @Test
    public void testReadStaticMethods() {
        var methods = readMethods(TestClassWithStaticMethod.class);

        assertNotNull(methods);
        assertEquals(1, methods.CONSUMERS.size());
        @SuppressWarnings("unchecked") Consumer<Integer> consumer =
                methods.CONSUMERS.get("theStaticConsumer");
        var input = randomInt();
        consumer.accept(input);
        assertEquals(input, TestClassWithStaticMethod.TheStaticConsumerInput);
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
    public void testReadClassWithUnsupportedNumberOfParams() {
        assertThrows(IllegalArgumentException.class,
                () -> readMethods(TestClassWithUnsupportedNumberOfParams.class));
    }
}
