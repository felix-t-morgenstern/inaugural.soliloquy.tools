package inaugural.soliloquy.tools.valueobjects;

import static soliloquy.specs.common.valueobjects.Vertex.vertexOf;

public class Vertex {
    public static soliloquy.specs.common.valueobjects.Vertex translate(
            soliloquy.specs.common.valueobjects.Vertex floatBox,
            float xTranslation, float yTranslation) {
        return vertexOf(
                floatBox.X + xTranslation,
                floatBox.Y + yTranslation
        );
    }
}
