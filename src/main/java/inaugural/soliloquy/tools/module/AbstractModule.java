package inaugural.soliloquy.tools.module;

import inaugural.soliloquy.tools.Check;
import org.int4.dirk.api.Injector;
import org.int4.dirk.di.Injectors;
import soliloquy.specs.game.Module;

import java.util.Map;

import static inaugural.soliloquy.tools.collections.Collections.mapOf;

public abstract class AbstractModule implements Module {
    protected final Injector INJECTOR;
    protected final Map<String, Object> NAMED_INSTANCES;

    protected AbstractModule() {
        INJECTOR = Injectors.manual();
        NAMED_INSTANCES = mapOf();
    }

    @Override
    public <T> T provide(Class<T> aClass) throws IllegalArgumentException {
        Check.ifNull(aClass, "aClass");
        try {
            return INJECTOR.getInstance(aClass);
        }
        catch (Exception e) {
            throw new IllegalArgumentException("class \"" + aClass.getCanonicalName() + "\" not registered");
        }
    }

    @Override
    public <T> T provide(String instanceName) throws IllegalArgumentException {
        Check.ifNullOrEmpty(instanceName, "instanceName");

        if (!NAMED_INSTANCES.containsKey(instanceName)) {
            throw new IllegalArgumentException("IOModule.provide: instanceName (" + instanceName +
                    ") does not correspond to a valid instance");
        }

        //noinspection unchecked
        return (T) NAMED_INSTANCES.get(instanceName);
    }

    @SuppressWarnings("UnusedReturnValue")
    protected  <T> T andRegister(T registrant) {
        INJECTOR.registerInstance(registrant);

        return registrant;
    }

    @SuppressWarnings("UnusedReturnValue")
    protected  <T> T andRegister(T registrant, String instanceName) {
        NAMED_INSTANCES.put(instanceName, registrant);

        return registrant;
    }
}
