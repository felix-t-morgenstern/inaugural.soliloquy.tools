package inaugural.soliloquy.tools.tests.valueobjects;

import org.junit.jupiter.api.Test;

import static inaugural.soliloquy.tools.valueobjects.Coordinate3d.addCoordinates3d;
import static inaugural.soliloquy.tools.valueobjects.Coordinate3d.addOffsets3d;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static soliloquy.specs.common.valueobjects.Coordinate3d.coordinate3dOf;

public class Coordinate3dTests {
    @Test
    public void testAddCoordinates3d() {
        var c1 = coordinate3dOf(111, 222, 333);
        var c2 = coordinate3dOf(444, 555, 666);

        var sum = addCoordinates3d(c1, c2);

        assertEquals(coordinate3dOf(555, 777, 999), sum);
    }

    @Test
    public void testAddOffsets3d() {
        var c = coordinate3dOf(100, 200, 300);

        var withOffsets = addOffsets3d(c, 11, 22, 33);

        assertEquals(coordinate3dOf(111, 222, 333), withOffsets);
    }
}
