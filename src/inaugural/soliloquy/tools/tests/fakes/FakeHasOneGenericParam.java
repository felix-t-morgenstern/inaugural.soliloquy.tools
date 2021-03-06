package inaugural.soliloquy.tools.tests.fakes;

import soliloquy.specs.common.shared.HasOneGenericParam;

public class FakeHasOneGenericParam<P> implements HasOneGenericParam<P> {
    public P _archetype;

    public FakeHasOneGenericParam(P archetype) {
        _archetype = archetype;
    }

    @Override
    public P getArchetype() {
        return _archetype;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
