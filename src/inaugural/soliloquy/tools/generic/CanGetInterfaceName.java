package inaugural.soliloquy.tools.generic;

import soliloquy.specs.common.shared.SoliloquyClass;

public abstract class CanGetInterfaceName {
    protected String getProperTypeName(Object object) {
        return object instanceof SoliloquyClass ?
                ((SoliloquyClass) object).getInterfaceName() :
                object.getClass().getCanonicalName();
    }
}
