package inaugural.soliloquy.tools.tests.valueobjects;

import org.junit.jupiter.api.Test;

import static inaugural.soliloquy.tools.collections.Collections.listOf;
import static inaugural.soliloquy.tools.valueobjects.Vertex.*;
import static java.lang.Math.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static soliloquy.specs.common.valueobjects.FloatBox.floatBoxOf;
import static soliloquy.specs.common.valueobjects.Vertex.vertexOf;

public class VertexTests {
    private final float X = 0.11f;
    private final float Y = 0.22f;

    private final soliloquy.specs.common.valueobjects.Vertex VERTEX = vertexOf(X, Y);

    @Test
    public void testDistance() {
        var v2x = 0.33f;
        var v2y = 0.44f;
        var expectedDist = (float) sqrt(pow(abs(X - v2x), 2d) + pow(abs(Y - v2y), 2d));

        var distance = distance(VERTEX, vertexOf(v2x, v2y));

        assertEquals(expectedDist, distance);
    }

    @Test
    public void testTranslateVertexFloats() {
        var xTranslation = 0.123f;
        var yTranslation = 0.456f;

        var translation = translateVertex(VERTEX, xTranslation, yTranslation);

        assertNotNull(translation);
        assertNotSame(VERTEX, translation);
        assertEquals(X + xTranslation, translation.X);
        assertEquals(Y + yTranslation, translation.Y);
    }

    @Test
    public void testTranslateVertexVertices() {
        var xTranslation = 0.123f;
        var yTranslation = 0.456f;

        var translation = translateVertex(VERTEX, vertexOf(xTranslation, yTranslation));

        assertNotNull(translation);
        assertNotSame(VERTEX, translation);
        assertEquals(X + xTranslation, translation.X);
        assertEquals(Y + yTranslation, translation.Y);
    }

    @Test
    public void testVerticesDifference() {
        var vertex2X = 0.33f;
        var vertex2Y = 0.4f;

        var difference = difference(VERTEX, vertexOf(vertex2X, vertex2Y));

        assertNotNull(difference);
        assertNotSame(VERTEX, difference);
        assertEquals(vertex2X - VERTEX.X, difference.X);
        assertEquals(vertex2Y - VERTEX.Y, difference.Y);
    }

    @Test
    public void testPolygonDimensFromArray() {
        var dimens = polygonDimens(
                vertexOf(1, 1),
                vertexOf(3, 2),
                vertexOf(2, 5)
        );

        assertEquals(floatBoxOf(1, 1, 3, 5), dimens);
    }

    @Test
    public void testPolygonDimensFromList() {
        var dimens = polygonDimens(
                listOf(
                        vertexOf(1, 1),
                        vertexOf(3, 2),
                        vertexOf(2, 5)
                )
        );

        assertEquals(floatBoxOf(1, 1, 3, 5), dimens);
    }
}
