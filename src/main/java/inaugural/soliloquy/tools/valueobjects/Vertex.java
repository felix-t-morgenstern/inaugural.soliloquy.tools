package inaugural.soliloquy.tools.valueobjects;

import soliloquy.specs.common.valueobjects.FloatBox;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

import static java.lang.Math.*;
import static soliloquy.specs.common.valueobjects.FloatBox.floatBoxOf;
import static soliloquy.specs.common.valueobjects.Vertex.vertexOf;

public class Vertex {
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

    public static FloatBox polygonDimens(soliloquy.specs.common.valueobjects.Vertex... vertices) {
        //noinspection OptionalGetWithoutIsPresent
        return floatBoxOf(
                Arrays.stream(vertices).map(v -> v.X).min(Comparator.naturalOrder()).get(),
                Arrays.stream(vertices).map(v -> v.Y).min(Comparator.naturalOrder()).get(),
                Arrays.stream(vertices).map(v -> v.X).max(Comparator.naturalOrder()).get(),
                Arrays.stream(vertices).map(v -> v.Y).max(Comparator.naturalOrder()).get()
        );
    }

    public static FloatBox polygonDimens(Collection<soliloquy.specs.common.valueobjects.Vertex> vertices) {
        //noinspection OptionalGetWithoutIsPresent
        return floatBoxOf(
                vertices.stream().map(v -> v.X).min(Comparator.naturalOrder()).get(),
                vertices.stream().map(v -> v.Y).min(Comparator.naturalOrder()).get(),
                vertices.stream().map(v -> v.X).max(Comparator.naturalOrder()).get(),
                vertices.stream().map(v -> v.Y).max(Comparator.naturalOrder()).get()
        );
    }
}
