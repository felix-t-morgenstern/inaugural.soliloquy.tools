package inaugural.soliloquy.tools.valueobjects;

import static soliloquy.specs.common.valueobjects.Vertex.vertexOf;

public class Vertex {
    public static soliloquy.specs.common.valueobjects.Vertex translate(
            soliloquy.specs.common.valueobjects.Vertex vertex,
            float xTranslation, float yTranslation) {
        return vertexOf(
                vertex.X + xTranslation,
                vertex.Y + yTranslation
        );
    }

    public static soliloquy.specs.common.valueobjects.Vertex translate(
            soliloquy.specs.common.valueobjects.Vertex v1,
            soliloquy.specs.common.valueobjects.Vertex v2
    ) {
        return translate(v1, v2.X, v2.Y);
    }
}
