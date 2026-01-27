package inaugural.soliloquy.tools.tests.reflection;

import inaugural.soliloquy.tools.reflection.Reflection;

public class TestClassWithAnnotatedMethod {
    @Reflection.DoNotReadMethod
    public void theRunnable() {
    }
}
