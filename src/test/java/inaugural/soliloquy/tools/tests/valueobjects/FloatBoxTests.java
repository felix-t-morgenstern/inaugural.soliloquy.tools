package inaugural.soliloquy.tools.tests.valueobjects;

import org.junit.jupiter.api.Test;

import static inaugural.soliloquy.tools.random.Random.*;
import static inaugural.soliloquy.tools.valueobjects.FloatBox.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static soliloquy.specs.common.valueobjects.FloatBox.floatBoxOf;
import static soliloquy.specs.common.valueobjects.Vertex.vertexOf;

public class FloatBoxTests {
    private final float LEFT_X = 0.11f;
    private final float TOP_Y = 0.22f;
    private final float RIGHT_X = 0.33f;
    private final float BOTTOM_Y = 0.44f;

    private final soliloquy.specs.common.valueobjects.FloatBox FLOAT_BOX =
            floatBoxOf(LEFT_X, TOP_Y, RIGHT_X, BOTTOM_Y);

    @Test
    public void testIntersection() {
        var newLeftX = LEFT_X + 0.01f;
        var newTopY = TOP_Y + 0.01f;
        var newRightX = RIGHT_X - 0.01f;
        var newBottomY = BOTTOM_Y - 0.01f;

        var intersectand1 = floatBoxOf(newLeftX, TOP_Y, RIGHT_X, BOTTOM_Y);

        var intersection1 = intersection(FLOAT_BOX, intersectand1);

        assertNotNull(intersection1);
        assertEquals(newLeftX, intersection1.LEFT_X);
        assertEquals(TOP_Y, intersection1.TOP_Y);
        assertEquals(RIGHT_X, intersection1.RIGHT_X);
        assertEquals(BOTTOM_Y, intersection1.BOTTOM_Y);

        var intersectand2 = floatBoxOf(LEFT_X, newTopY, RIGHT_X, BOTTOM_Y);

        var intersection2 = intersection(intersection1, intersectand2);

        assertNotNull(intersection2);
        assertEquals(newLeftX, intersection2.LEFT_X);
        assertEquals(newTopY, intersection2.TOP_Y);
        assertEquals(RIGHT_X, intersection2.RIGHT_X);
        assertEquals(BOTTOM_Y, intersection2.BOTTOM_Y);

        var intersectand3 = floatBoxOf(LEFT_X, TOP_Y, newRightX, BOTTOM_Y);

        var intersection3 = intersection(intersection2, intersectand3);

        assertNotNull(intersection3);
        assertEquals(newLeftX, intersection3.LEFT_X);
        assertEquals(newTopY, intersection3.TOP_Y);
        assertEquals(newRightX, intersection3.RIGHT_X);
        assertEquals(BOTTOM_Y, intersection3.BOTTOM_Y);

        var intersectand4 = floatBoxOf(LEFT_X, TOP_Y, RIGHT_X, newBottomY);

        var intersection4 = intersection(intersection3, intersectand4);

        assertNotNull(intersection4);
        assertEquals(newLeftX, intersection4.LEFT_X);
        assertEquals(newTopY, intersection4.TOP_Y);
        assertEquals(newRightX, intersection4.RIGHT_X);
        assertEquals(newBottomY, intersection4.BOTTOM_Y);
    }

    @Test
    public void testIntersectionWithNoOverlap() {
        var topLeft = 0.25f;
        var bottomRight = 0.75f;
        var floatBox = floatBoxOf(topLeft, topLeft, bottomRight, bottomRight);

        assertNotNull(intersection(floatBox, floatBox));

        var floatBoxToTheLeft = floatBoxOf(0f, topLeft, topLeft, bottomRight);
        assertNull(intersection(floatBox, floatBoxToTheLeft));

        var floatBoxToTheTop = floatBoxOf(topLeft, 0f, bottomRight, topLeft);
        assertNull(intersection(floatBox, floatBoxToTheTop));

        var floatBoxToTheRight = floatBoxOf(bottomRight, topLeft, 1f, bottomRight);
        assertNull(intersection(floatBox, floatBoxToTheRight));

        var floatBoxToTheBottom = floatBoxOf(topLeft, bottomRight, bottomRight, 1f);
        assertNull(intersection(floatBox, floatBoxToTheBottom));
    }

    @Test
    public void testIntersectionWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> intersection(FLOAT_BOX, null));
        assertThrows(IllegalArgumentException.class, () -> intersection(null, FLOAT_BOX));
    }

    @Test
    public void testEncompassing() {
        var newLeftX = LEFT_X - 0.01f;
        var newTopY = TOP_Y - 0.01f;
        var newRightX = RIGHT_X + 0.01f;
        var newBottomY = BOTTOM_Y + 0.01f;

        var toEncompass1 = floatBoxOf(newLeftX, TOP_Y, RIGHT_X, BOTTOM_Y);

        var encompassed1 = encompassing(FLOAT_BOX, toEncompass1);

        assertNotNull(encompassed1);
        assertEquals(newLeftX, encompassed1.LEFT_X);
        assertEquals(TOP_Y, encompassed1.TOP_Y);
        assertEquals(RIGHT_X, encompassed1.RIGHT_X);
        assertEquals(BOTTOM_Y, encompassed1.BOTTOM_Y);

        var toEncompass2 = floatBoxOf(LEFT_X, newTopY, RIGHT_X, BOTTOM_Y);

        var encompassed2 = encompassing(encompassed1, toEncompass2);

        assertNotNull(encompassed2);
        assertEquals(newLeftX, encompassed2.LEFT_X);
        assertEquals(newTopY, encompassed2.TOP_Y);
        assertEquals(RIGHT_X, encompassed2.RIGHT_X);
        assertEquals(BOTTOM_Y, encompassed2.BOTTOM_Y);

        var toEncompass3 = floatBoxOf(LEFT_X, TOP_Y, newRightX, BOTTOM_Y);

        var encompassed3 = encompassing(encompassed2, toEncompass3);

        assertNotNull(encompassed3);
        assertEquals(newLeftX, encompassed3.LEFT_X);
        assertEquals(newTopY, encompassed3.TOP_Y);
        assertEquals(newRightX, encompassed3.RIGHT_X);
        assertEquals(BOTTOM_Y, encompassed3.BOTTOM_Y);

        var toEncompass4 = floatBoxOf(LEFT_X, TOP_Y, RIGHT_X, newBottomY);

        var encompassed4 = encompassing(encompassed3, toEncompass4);

        assertNotNull(encompassed4);
        assertEquals(newLeftX, encompassed4.LEFT_X);
        assertEquals(newTopY, encompassed4.TOP_Y);
        assertEquals(newRightX, encompassed4.RIGHT_X);
        assertEquals(newBottomY, encompassed4.BOTTOM_Y);
    }

    @Test
    public void testEncompassingFromArray() {
        var newLeftX = LEFT_X - 0.01f;
        var newTopY = TOP_Y - 0.01f;
        var newRightX = RIGHT_X + 0.01f;
        var newBottomY = BOTTOM_Y + 0.01f;

        var toEncompass1 = floatBoxOf(newLeftX, TOP_Y, RIGHT_X, BOTTOM_Y);
        var toEncompass2 = floatBoxOf(LEFT_X, newTopY, RIGHT_X, BOTTOM_Y);
        var toEncompass3 = floatBoxOf(LEFT_X, TOP_Y, newRightX, BOTTOM_Y);
        var toEncompass4 = floatBoxOf(LEFT_X, TOP_Y, RIGHT_X, newBottomY);

        var encompassed = encompassing(toEncompass1, toEncompass2, toEncompass3, toEncompass4);

        assertNotNull(encompassed);
        assertEquals(newLeftX, encompassed.LEFT_X);
        assertEquals(newTopY, encompassed.TOP_Y);
        assertEquals(newRightX, encompassed.RIGHT_X);
        assertEquals(newBottomY, encompassed.BOTTOM_Y);
    }

    @Test
    public void testTranslateFloatBox() {
        var xTranslation = 0.123f;
        var yTranslation = 0.456f;

        var translation = translateFloatBox(FLOAT_BOX, xTranslation, yTranslation);

        assertNotNull(translation);
        assertNotSame(FLOAT_BOX, translation);
        assertEquals(LEFT_X + xTranslation, translation.LEFT_X);
        assertEquals(TOP_Y + yTranslation, translation.TOP_Y);
        assertEquals(RIGHT_X + xTranslation, translation.RIGHT_X);
        assertEquals(BOTTOM_Y + yTranslation, translation.BOTTOM_Y);
    }

    @Test
    public void testTranslateFloatBoxFromVertex() {
        var xTranslation = 0.123f;
        var yTranslation = 0.456f;

        var translation = translateFloatBox(FLOAT_BOX, vertexOf(xTranslation, yTranslation));

        assertNotNull(translation);
        assertNotSame(FLOAT_BOX, translation);
        assertEquals(LEFT_X + xTranslation, translation.LEFT_X);
        assertEquals(TOP_Y + yTranslation, translation.TOP_Y);
        assertEquals(RIGHT_X + xTranslation, translation.RIGHT_X);
        assertEquals(BOTTOM_Y + yTranslation, translation.BOTTOM_Y);
    }

    @Test
    public void testContains() {
        var floatBoxDimensDistFromCenter = randomFloatInRange(1f, 1000f);
        var slightlyOffCenter = floatBoxDimensDistFromCenter + 1f;
        // Using constrained vals to avoid rounding errors; any FloatBox with valid dimens will
        // work here
        var floatBox = floatBoxOf(
                -floatBoxDimensDistFromCenter,
                -floatBoxDimensDistFromCenter,
                floatBoxDimensDistFromCenter,
                floatBoxDimensDistFromCenter
        );

        assertTrue(contains(floatBox, vertexOf(
                randomFloatInRange(-floatBoxDimensDistFromCenter, floatBoxDimensDistFromCenter),
                randomFloatInRange(-floatBoxDimensDistFromCenter, floatBoxDimensDistFromCenter)
        )));
        assertFalse(contains(floatBox, vertexOf(
                -slightlyOffCenter,
                randomFloatInRange(-floatBoxDimensDistFromCenter, floatBoxDimensDistFromCenter)
        )));
        assertFalse(contains(floatBox, vertexOf(
                slightlyOffCenter,
                randomFloatInRange(-floatBoxDimensDistFromCenter, floatBoxDimensDistFromCenter)
        )));
        assertFalse(contains(floatBox, vertexOf(
                randomFloatInRange(-floatBoxDimensDistFromCenter, floatBoxDimensDistFromCenter),
                -slightlyOffCenter
        )));
        assertFalse(contains(floatBox, vertexOf(
                randomFloatInRange(-floatBoxDimensDistFromCenter, floatBoxDimensDistFromCenter),
                slightlyOffCenter
        )));
    }

    @Test
    public void testContainsWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> contains(null, randomVertex()));
        assertThrows(IllegalArgumentException.class, () -> contains(randomFloatBox(), null));
    }

    @Test
    public void testEquals() {
        assertEquals(FLOAT_BOX, floatBoxOf(LEFT_X, TOP_Y, RIGHT_X, BOTTOM_Y));
    }
}
