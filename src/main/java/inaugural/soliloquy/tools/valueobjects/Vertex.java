package inaugural.soliloquy.tools.valueobjects;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.valueobjects.FloatBox;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

import static java.lang.Math.*;
import static soliloquy.specs.common.valueobjects.FloatBox.floatBoxOf;
import static soliloquy.specs.common.valueobjects.Vertex.vertexOf;

public class Vertex {
    public static float slope(soliloquy.specs.common.valueobjects.Vertex v1,
                              soliloquy.specs.common.valueobjects.Vertex v2) {
        return (v2.Y - v1.Y) / (v2.X - v1.X);
    }

    public static float yIntersectAtX(float slope,
                                      soliloquy.specs.common.valueobjects.Vertex point,
                                      float x) {
        return point.Y + ((x - point.X) * slope);
    }

    public static float xIntersectAtY(float slope,
                                      soliloquy.specs.common.valueobjects.Vertex point,
                                      float y) {
        return point.X + ((y - point.Y) * (1f/slope));
    }

    public static float distance(
            soliloquy.specs.common.valueobjects.Vertex v1,
            soliloquy.specs.common.valueobjects.Vertex v2
    ) {
        return (float) sqrt(pow(abs(v1.X - v2.X), 2d) + pow(abs(v1.Y - v2.Y), 2d));
    }

    public static soliloquy.specs.common.valueobjects.Vertex translateVertex(
            soliloquy.specs.common.valueobjects.Vertex vertex,
            float xTranslation, float yTranslation) {
        return vertexOf(
                vertex.X + xTranslation,
                vertex.Y + yTranslation
        );
    }

    public static soliloquy.specs.common.valueobjects.Vertex translateVertex(
            soliloquy.specs.common.valueobjects.Vertex v1,
            soliloquy.specs.common.valueobjects.Vertex v2
    ) {
        return translateVertex(v1, v2.X, v2.Y);
    }

    /**
     * @param v1 The vertex whose dimensions are subtrahends
     * @param v2 The vertex whose dimensions from which to subtract
     * @return A vertex whose respective dimensions indicate the distance from v1 to v2 for that
     *         dimension
     */
    public static soliloquy.specs.common.valueobjects.Vertex difference(
            soliloquy.specs.common.valueobjects.Vertex v1,
            soliloquy.specs.common.valueobjects.Vertex v2
    ) {
        return vertexOf(
                v2.X - v1.X,
                v2.Y - v1.Y
        );
    }

    public static FloatBox polygonEncompassingDimens(
            soliloquy.specs.common.valueobjects.Vertex... vertices
    ) {
        //noinspection OptionalGetWithoutIsPresent
        return floatBoxOf(
                Arrays.stream(vertices).map(v -> v.X).min(Comparator.naturalOrder()).get(),
                Arrays.stream(vertices).map(v -> v.Y).min(Comparator.naturalOrder()).get(),
                Arrays.stream(vertices).map(v -> v.X).max(Comparator.naturalOrder()).get(),
                Arrays.stream(vertices).map(v -> v.Y).max(Comparator.naturalOrder()).get()
        );
    }

    public static FloatBox polygonEncompassingDimens(
            Collection<soliloquy.specs.common.valueobjects.Vertex> vertices
    ) {
        //noinspection OptionalGetWithoutIsPresent
        return floatBoxOf(
                vertices.stream().map(v -> v.X).min(Comparator.naturalOrder()).get(),
                vertices.stream().map(v -> v.Y).min(Comparator.naturalOrder()).get(),
                vertices.stream().map(v -> v.X).max(Comparator.naturalOrder()).get(),
                vertices.stream().map(v -> v.Y).max(Comparator.naturalOrder()).get()
        );
    }

    public static soliloquy.specs.common.valueobjects.Vertex getVerticesCentroid(
            soliloquy.specs.common.valueobjects.Vertex... points
    ) {
        Check.ifNull(points, "points");

        var x = 0f;
        var y = 0f;
        var pointCount = points.length;
        for (soliloquy.specs.common.valueobjects.Vertex point : points) {
            Check.ifNull(point, "point within points");

            x += point.X;
            y += point.Y;
        }
        return vertexOf(
                x / (float)pointCount,
                y / (float)pointCount
        );
    }

    public static soliloquy.specs.common.valueobjects.Vertex getVerticesCentroid(
            Collection<soliloquy.specs.common.valueobjects.Vertex> points
    ) {
        Check.ifNull(points, "points");

        var x = 0f;
        var y = 0f;
        var pointCount = points.size();
        for (soliloquy.specs.common.valueobjects.Vertex point : points) {
            Check.ifNull(point, "point within points");

            x += point.X;
            y += point.Y;
        }
        return vertexOf(
                x / (float)pointCount,
                y / (float)pointCount
        );
    }

    public static soliloquy.specs.common.valueobjects.Vertex getVerticesCentroid(
            Stream<soliloquy.specs.common.valueobjects.Vertex> pointsStream
    ) {
        Check.ifNull(pointsStream, "pointsStream");

        AtomicReference<Float> x = new AtomicReference<>(0f);
        AtomicReference<Float> y = new AtomicReference<>(0f);
        AtomicReference<Integer> pointCount = new AtomicReference<>(0);

        pointsStream.forEach(p -> {
            Check.ifNull(p, "point within pointsStream");

            x.updateAndGet(v -> v + p.X);
            y.updateAndGet(v -> v + p.Y);
            pointCount.updateAndGet(count -> count + 1);
        });
        return vertexOf(
                x.get() / (float)pointCount.get(),
                y.get() / (float)pointCount.get()
        );
    }
}
