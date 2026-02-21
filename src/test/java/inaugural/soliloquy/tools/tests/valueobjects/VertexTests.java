package inaugural.soliloquy.tools.tests.valueobjects;

import org.junit.jupiter.api.Test;
import soliloquy.specs.common.valueobjects.Vertex;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static inaugural.soliloquy.tools.collections.Collections.listOf;
import static inaugural.soliloquy.tools.collections.Collections.setOf;
import static inaugural.soliloquy.tools.random.Random.*;
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
    public void testSlope() {
        assertEquals(2f, slope(vertexOf(1f, 1f), vertexOf(3f, 5f)));
    }

    @Test
    public void testVerticalSlopes() {
        assertEquals(Float.POSITIVE_INFINITY, slope(vertexOf(1f, 1f), vertexOf(1f, 5f)));
        assertEquals(Float.NEGATIVE_INFINITY, slope(vertexOf(1f, 1f), vertexOf(1f, -5f)));
    }

    @SuppressWarnings("DataFlowIssue")
    @Test
    public void testSlopeWithInvalidArgs() {
        // NB: Specifically avoiding checks since this may be called many times per frame
        assertThrows(NullPointerException.class, () -> slope(null, randomVertex()));
        assertThrows(NullPointerException.class, () -> slope(randomVertex(), null));
    }

    @Test
    public void testYIntersectAtX() {
        assertEquals(7f, yIntersectAtX(2f, vertexOf(3f, 5f), 4f));
    }

    @SuppressWarnings("DataFlowIssue")
    @Test
    public void testYIntersectAtXWithInvalidArgs() {
        // NB: Specifically avoiding checks since this may be called many times per frame
        assertThrows(NullPointerException.class,
                () -> yIntersectAtX(randomFloat(), null, randomFloat()));
    }

    @Test
    public void testXIntersectAtY() {
        assertEquals(3.5f, xIntersectAtY(2f, vertexOf(3f, 5f), 6f));
    }

    @SuppressWarnings("DataFlowIssue")
    @Test
    public void testXIntersectAtYWithInvalidArgs() {
        // NB: Specifically avoiding checks since this may be called many times per frame
        assertThrows(NullPointerException.class,
                () -> xIntersectAtY(randomFloat(), null, randomFloat()));
    }

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
    public void testPolygonEncompassingDimensFromArray() {
        var dimens = polygonEncompassingDimens(
                vertexOf(1, 1),
                vertexOf(3, 2),
                vertexOf(2, 5)
        );

        assertEquals(floatBoxOf(1, 1, 3, 5), dimens);
    }

    @Test
    public void testPolygonEncompassingDimensFromList() {
        var dimens = polygonEncompassingDimens(
                listOf(
                        vertexOf(1, 1),
                        vertexOf(3, 2),
                        vertexOf(2, 5)
                )
        );

        assertEquals(floatBoxOf(1, 1, 3, 5), dimens);
    }

    @Test
    public void testGetVerticesCentroidFromArray() {
        var vertices = IntStream.rangeClosed(0, randomIntInRange(2, 10))
                .mapToObj(_ -> randomVertex()).toArray(Vertex[]::new);

        var expectedX = Arrays.stream(vertices).map(v -> v.X).reduce(0f, Float::sum) /
                (float) vertices.length;
        var expectedY = Arrays.stream(vertices).map(v -> v.Y).reduce(0f, Float::sum) /
                (float) vertices.length;

        var output = getVerticesCentroid(vertices);

        assertEquals(vertexOf(expectedX, expectedY), output);
    }

    @Test
    public void testGetVerticesCentroidFromArrayWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> getVerticesCentroid((Vertex[]) null));
        assertThrows(IllegalArgumentException.class, () -> getVerticesCentroid((Vertex) null));
    }

    @Test
    public void testGetVerticesCentroidFromCollection() {
        var vertices = IntStream.rangeClosed(0, randomIntInRange(2, 10))
                .mapToObj(_ -> randomVertex()).collect(Collectors.toSet());

        var expectedX = vertices.stream().map(v -> v.X).reduce(0f, Float::sum) /
                (float) vertices.size();
        var expectedY = vertices.stream().map(v -> v.Y).reduce(0f, Float::sum) /
                (float) vertices.size();

        var output = getVerticesCentroid(vertices);

        assertEquals(vertexOf(expectedX, expectedY), output);
    }

    @Test
    public void testGetVerticesCentroidFromCollectionWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class,
                () -> getVerticesCentroid((Collection<Vertex>) null));
        assertThrows(IllegalArgumentException.class,
                () -> getVerticesCentroid(setOf((Vertex) null)));
    }

    @Test
    public void testGetVerticesCentroidFromStream() {
        var vertices = IntStream.rangeClosed(0, randomIntInRange(2, 10))
                .mapToObj(_ -> randomVertex()).collect(Collectors.toSet());

        var expectedX = vertices.stream().map(v -> v.X).reduce(0f, Float::sum) /
                (float) vertices.size();
        var expectedY = vertices.stream().map(v -> v.Y).reduce(0f, Float::sum) /
                (float) vertices.size();

        var output = getVerticesCentroid(vertices.stream());

        assertEquals(vertexOf(expectedX, expectedY), output);
    }

    @Test
    public void testGetVerticesCentroidFromStreamWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class,
                () -> getVerticesCentroid((Stream<Vertex>) null));
        assertThrows(IllegalArgumentException.class,
                () -> getVerticesCentroid(setOf((Vertex) null).stream()));
    }

    @Test
    public void testTriangleArea() {
        assertEquals(
                0.5f,
                triangleArea(
                        vertexOf(0f, 0f),
                        vertexOf(1f, 0f),
                        vertexOf(1f, 1f)
                )
        );

        assertEquals(
                6f,
                triangleArea(
                        vertexOf(0f, 0f),
                        vertexOf(2f, 0f),
                        vertexOf(4f, 6f)
                )
        );
    }

    @SuppressWarnings("DataFlowIssue")
    @Test
    public void testTriangleAreaWithInvalidArgs() {
        // I'm fine with this not having specific checks, since it's used in high-performance
        // contexts
        assertThrows(NullPointerException.class,
                () -> triangleArea(null, randomVertex(), randomVertex()));
        assertThrows(NullPointerException.class,
                () -> triangleArea(randomVertex(), null, randomVertex()));
        assertThrows(NullPointerException.class,
                () -> triangleArea(randomVertex(), randomVertex(), null));
    }

    @Test
    public void testPointIsInTriangle() {
        assertTrue(pointIsInTriangle(
                vertexOf(0.51f, 0.5f),
                vertexOf(0f, 0f),
                vertexOf(1f, 0f),
                vertexOf(1f, 1f)
        ));
        assertFalse(pointIsInTriangle(
                vertexOf(0.49f, 0.5f),
                vertexOf(0f, 0f),
                vertexOf(1f, 0f),
                vertexOf(1f, 1f)
        ));
        assertTrue(pointIsInTriangle(
                vertexOf(0.5f, 0.49f),
                vertexOf(0f, 0f),
                vertexOf(1f, 0f),
                vertexOf(1f, 1f)
        ));
        assertFalse(pointIsInTriangle(
                vertexOf(0.5f, 0.51f),
                vertexOf(0f, 0f),
                vertexOf(1f, 0f),
                vertexOf(1f, 1f)
        ));

        assertFalse(pointIsInTriangle(
                vertexOf(1.9f, 3f),
                vertexOf(0f, 0f),
                vertexOf(2f, 0f),
                vertexOf(4f, 6f)
        ));
        assertTrue(pointIsInTriangle(
                vertexOf(2.1f, 3f),
                vertexOf(0f, 0f),
                vertexOf(2f, 0f),
                vertexOf(4f, 6f)
        ));
        assertTrue(pointIsInTriangle(
                vertexOf(2.9f, 3f),
                vertexOf(0f, 0f),
                vertexOf(2f, 0f),
                vertexOf(4f, 6f)
        ));
        assertFalse(pointIsInTriangle(
                vertexOf(3.1f, 3f),
                vertexOf(0f, 0f),
                vertexOf(2f, 0f),
                vertexOf(4f, 6f)
        ));

        assertTrue(pointIsInTriangle(
                vertexOf(0.25f,0.25f),
                vertexOf(0.18515939f,0.13649687f),
                vertexOf(0.38515937f,0.23649688f),
                vertexOf(0.26515937f,0.3364969f)
        ));
    }

    @Test
    public void testPointIsInTriangleWithInvalidArgs() {
        // I'm fine with this not having specific checks, since it's used in high-performance
        // contexts
        assertThrows(NullPointerException.class,
                () -> pointIsInTriangle(null, randomVertex(), randomVertex(), randomVertex()));
        assertThrows(NullPointerException.class,
                () -> pointIsInTriangle(randomVertex(), null, randomVertex(), randomVertex()));
        assertThrows(NullPointerException.class,
                () -> pointIsInTriangle(randomVertex(), randomVertex(), null, randomVertex()));
        assertThrows(NullPointerException.class,
                () -> pointIsInTriangle(randomVertex(), randomVertex(), randomVertex(), null));
    }
}
