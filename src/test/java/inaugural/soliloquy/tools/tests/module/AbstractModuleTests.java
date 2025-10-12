package inaugural.soliloquy.tools.tests.module;

import inaugural.soliloquy.tools.module.AbstractModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static inaugural.soliloquy.tools.tests.module.AbstractModuleTestImplementation.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AbstractModuleTests {
    private AbstractModule module;

    @BeforeEach
    public void setUp() {
        module = new AbstractModuleTestImplementation();
    }

    @Test
    public void testProvideUnnamedInstance() {
        var provided = module.provide(int.class);

        assertEquals(UnnamedInstance, provided);
    }

    @Test
    public void testProvideNamedInstance() {
        var provided = module.provide(NamedInstanceName);

        assertEquals(NamedInstance, provided);
    }

    @Test
    public void testProvideNonexistentUnnamedInstance() {
        assertThrows(IllegalArgumentException.class, () -> module.provide(short.class));
    }

    @Test
    public void testProvideNullClass() {
        assertThrows(IllegalArgumentException.class, () -> module.provide((Class<?>) null));
    }

    @Test
    public void testProvideNonexistentNamedInstance() {
        assertThrows(IllegalArgumentException.class, () -> module.provide("not a valid name"));
    }

    @Test
    public void testProvideNullOrEmptyNameForInstance() {
        assertThrows(IllegalArgumentException.class, () -> module.provide((String) null));
        assertThrows(IllegalArgumentException.class, () -> module.provide(""));
    }
}
