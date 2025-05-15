package inaugural.soliloquy.tools.tests.valueobjects;

import org.junit.jupiter.api.Test;

import static inaugural.soliloquy.tools.valueobjects.Coordinate2d.addCoordinates2d;
import static inaugural.soliloquy.tools.valueobjects.Coordinate2d.addOffsets2d;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static soliloquy.specs.common.valueobjects.Coordinate2d.coordinate2dOf;

public class Coordinate2dTests {
    @Test
    public void testAddCoordinates2d() {
        var c1 = coordinate2dOf(111, 222);
        var c2 = coordinate2dOf(333, 444);

        var sum = addCoordinates2d(c1, c2);

        assertEquals(coordinate2dOf(444, 666), sum);
    }

    @Test
    public void testAddOffsets2d() {
        var c = coordinate2dOf(100, 200);

        var withOffsets = addOffsets2d(c, 11, 22);

        assertEquals(coordinate2dOf(111, 222), withOffsets);
    }
}
