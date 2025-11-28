package inaugural.soliloquy.tools.tests.reflection;

public class TestClass {
    static int TheRunnableExecutedCount;

    static int TheSupplierExecutedCount;
    static String TheSupplierOutput = "TheSupplierOutput";

    static int TheConsumerInput;

    static String TheBiConsumerInput1;
    static int TheBiConsumerInput2;

    static long TheFunctionInput;

    static String TheBiFunctionInput1;
    static double TheBiFunctionInput2;

    public void theRunnable() {
        TheRunnableExecutedCount++;
    }

    public String theSupplier() {
        TheSupplierExecutedCount++;
        return TheSupplierOutput;
    }

    public void theConsumer(int input) {
        TheConsumerInput = input;
    }

    public void theBiConsumer(String input1, int input2) {
        TheBiConsumerInput1 = input1;
        TheBiConsumerInput2 = input2;
    }

    public String theFunction(long input) {
        return ((Long)(TheFunctionInput = input)).toString();
    }

    public String theBiFunction(String input1, double input2) {
        return (TheBiFunctionInput1 = input1) + (TheBiFunctionInput2 = input2);
    }
}
