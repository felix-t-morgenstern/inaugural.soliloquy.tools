package inaugural.soliloquy.tools.tests.reflection;

public class TestClassWithStaticMethod {
    static int TheStaticConsumerInput;

    public static void theStaticConsumer(int input) {
        TheStaticConsumerInput = input;
    }
}
