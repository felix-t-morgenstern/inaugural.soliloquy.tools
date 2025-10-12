package inaugural.soliloquy.tools.valueobjects;

import inaugural.soliloquy.tools.Check;

import static soliloquy.specs.common.valueobjects.FloatBox.floatBoxOf;

public class FloatBox {
    public static soliloquy.specs.common.valueobjects.FloatBox intersection(
            soliloquy.specs.common.valueobjects.FloatBox floatBox1,
            soliloquy.specs.common.valueobjects.FloatBox floatBox2) {
        Check.ifNull(floatBox1, "floatBox1");
        Check.ifNull(floatBox2, "floatBox2");
        if (floatBox1.RIGHT_X <= floatBox2.LEFT_X) {
            return null;
        }
        if (floatBox1.BOTTOM_Y <= floatBox2.TOP_Y) {
            return null;
        }
        if (floatBox1.LEFT_X >= floatBox2.RIGHT_X) {
            return null;
        }
        if (floatBox1.TOP_Y >= floatBox2.BOTTOM_Y) {
            return null;
        }
        return floatBoxOf(
                Math.max(floatBox2.LEFT_X, floatBox1.LEFT_X),
                Math.max(floatBox2.TOP_Y, floatBox1.TOP_Y),
                Math.min(floatBox2.RIGHT_X, floatBox1.RIGHT_X),
                Math.min(floatBox2.BOTTOM_Y, floatBox1.BOTTOM_Y));
    }

    public static soliloquy.specs.common.valueobjects.FloatBox encompassing(
            soliloquy.specs.common.valueobjects.FloatBox floatBox1,
            soliloquy.specs.common.valueobjects.FloatBox floatBox2) {
        Check.ifNull(floatBox1, "floatBox1");
        Check.ifNull(floatBox2, "floatBox2");
        return floatBoxOf(
                Math.min(floatBox2.LEFT_X, floatBox1.LEFT_X),
                Math.min(floatBox2.TOP_Y, floatBox1.TOP_Y),
                Math.max(floatBox2.RIGHT_X, floatBox1.RIGHT_X),
                Math.max(floatBox2.BOTTOM_Y, floatBox1.BOTTOM_Y));
    }

    public static soliloquy.specs.common.valueobjects.FloatBox translate(
            soliloquy.specs.common.valueobjects.FloatBox floatBox,
            float xTranslation, float yTranslation) {
        return floatBoxOf(
                floatBox.LEFT_X + xTranslation,
                floatBox.TOP_Y + yTranslation,
                floatBox.RIGHT_X + xTranslation,
                floatBox.BOTTOM_Y + yTranslation);
    }
}
