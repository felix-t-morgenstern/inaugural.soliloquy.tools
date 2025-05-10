package inaugural.soliloquy.tools.tests.valueobjects;

import org.junit.jupiter.api.Test;
import soliloquy.specs.common.valueobjects.Coordinate3d;

import static inaugural.soliloquy.tools.valueobjects.Coordinate3d.addCoordinates3d;
import static inaugural.soliloquy.tools.valueobjects.Coordinate3d.addOffsets3d;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Coordinate3dTests {
    @Test
    public void testAddCoordinates3d() {
        var c1 = Coordinate3d.of(111, 222, 333);
        var c2 = Coordinate3d.of(444, 555, 666);

        var sum = addCoordinates3d(c1, c2);

        assertEquals(Coordinate3d.of(555, 777, 999), sum);
    }

    @Test
    public void testAddOffsets3d() {
        var c = Coordinate3d.of(100, 200, 300);

        var withOffsets = addOffsets3d(c, 11, 22, 33);

        assertEquals(Coordinate3d.of(111, 222, 333), withOffsets);
    }
}
