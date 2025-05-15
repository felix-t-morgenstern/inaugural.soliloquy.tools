package inaugural.soliloquy.tools.valueobjects;

import static soliloquy.specs.common.valueobjects.Coordinate2d.coordinate2dOf;

public class Coordinate2d {
    public static soliloquy.specs.common.valueobjects.Coordinate2d addCoordinates2d(
            soliloquy.specs.common.valueobjects.Coordinate2d c1,
            soliloquy.specs.common.valueobjects.Coordinate2d c2) {
        return coordinate2dOf(c1.X + c2.X, c1.Y + c2.Y);
    }

    public static soliloquy.specs.common.valueobjects.Coordinate2d addOffsets2d(
            soliloquy.specs.common.valueobjects.Coordinate2d c, int offsetX, int offsetY) {
        return coordinate2dOf(c.X + offsetX, c.Y + offsetY);
    }
}
