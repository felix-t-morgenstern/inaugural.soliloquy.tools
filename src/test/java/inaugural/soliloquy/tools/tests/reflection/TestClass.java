package inaugural.soliloquy.tools.tests.reflection;

public class TestClass {
    static int TheActionInput;

    static long TheFunctionInput;

    public void theAction(int input) {
        TheActionInput = input;
    }

    public String theFunction(long input) {
        return ((Long)(TheFunctionInput = input)).toString();
    }
}
