package inaugural.soliloquy.tools.tests.module;

import inaugural.soliloquy.tools.module.AbstractModule;

public class AbstractModuleTestImplementation extends AbstractModule {
    public static int UnnamedInstance = 123;
    public static String NamedInstanceName = "instanceName";
    public static float NamedInstance = 0.456f;

    public AbstractModuleTestImplementation() {
        andRegister(UnnamedInstance);
        andRegister(NamedInstance, NamedInstanceName);
    }
}
