package inaugural.soliloquy.tools.persistence;

import com.google.gson.Gson;
import soliloquy.specs.common.persistence.TypeHandler;

public abstract class AbstractTypeHandler<T> implements TypeHandler<T> {
    @SuppressWarnings("unused")
    protected static final Gson JSON = new Gson();
}
