package inaugural.soliloquy.tools.tests.valueobjects;

import org.junit.jupiter.api.Test;
import soliloquy.specs.common.valueobjects.Coordinate2d;

import static inaugural.soliloquy.tools.valueobjects.Coordinate2d.addCoordinates2d;
import static inaugural.soliloquy.tools.valueobjects.Coordinate2d.addOffsets2d;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Coordinate2dTests {
    @Test
    public void testAddCoordinates2d() {
        var c1 = Coordinate2d.of(111, 222);
        var c2 = Coordinate2d.of(333, 444);

        var sum = addCoordinates2d(c1, c2);

        assertEquals(Coordinate2d.of(444, 666), sum);
    }

    @Test
    public void testAddOffsets2d() {
        var c = Coordinate2d.of(100, 200);

        var withOffsets = addOffsets2d(c, 11, 22);

        assertEquals(Coordinate2d.of(111, 222), withOffsets);
    }
}
