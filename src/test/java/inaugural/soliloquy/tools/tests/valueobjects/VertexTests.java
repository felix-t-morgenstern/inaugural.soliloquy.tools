package inaugural.soliloquy.tools.tests.valueobjects;

import org.junit.jupiter.api.Test;

import static inaugural.soliloquy.tools.valueobjects.Vertex.translate;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static soliloquy.specs.common.valueobjects.Vertex.vertexOf;

public class VertexTests {
    private final float X = 0.11f;
    private final float Y = 0.22f;

    private final soliloquy.specs.common.valueobjects.Vertex VERTEX = vertexOf(X, Y);

    @Test
    public void testTranslateFloats() {
        var xTranslation = 0.123f;
        var yTranslation = 0.456f;

        var translation = translate(VERTEX, xTranslation, yTranslation);

        assertNotNull(translation);
        assertNotSame(VERTEX, translation);
        assertEquals(X + xTranslation, translation.X);
        assertEquals(Y + yTranslation, translation.Y);
    }

    @Test
    public void testTranslateVertices() {
        var xTranslation = 0.123f;
        var yTranslation = 0.456f;

        var translation = translate(VERTEX, vertexOf(xTranslation, yTranslation));

        assertNotNull(translation);
        assertNotSame(VERTEX, translation);
        assertEquals(X + xTranslation, translation.X);
        assertEquals(Y + yTranslation, translation.Y);
    }
}
