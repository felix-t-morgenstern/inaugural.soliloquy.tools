package inaugural.soliloquy.tools.tests.random;

import inaugural.soliloquy.tools.random.Random;
import org.junit.jupiter.api.Test;
import org.mockito.internal.creation.SuspendMethod;

import java.util.function.Consumer;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

public class RandomTests {
    @Test
    public void testRandomBoolean() {
        for (var i = 0; i < 1000; i++) {
            if (Random.randomBoolean()) {
                return;
            }
        }
        fail("randomBoolean() did not return a true after 1000 trials");
    }

    @Test
    public void testRandomInt() {
        runRandomizationTest(Random::randomInt);
    }

    @Test
    public void testRandomIntWithInclusiveFloor() {
        var floor = new java.util.Random().nextInt();
        runRandomizationTest(() -> Random.randomIntWithInclusiveFloor(floor),
                i -> assertTrue(i >= floor));
    }

    @Test
    public void testRandomIntWithInclusiveCeiling() {
        var ceiling = new java.util.Random().nextInt();
        runRandomizationTest(() -> Random.randomIntWithInclusiveCeiling(ceiling),
                i -> assertTrue(i <= ceiling));
    }

    @Test
    public void testRandomIntInRange() {
        var min = -Math.abs(new java.util.Random().nextInt() / 2);
        var max = -min;
        runRandomizationTest(() -> Random.randomIntInRange(min, max), i -> {
            assertTrue(i >= min);
            assertTrue(i <= max);
        });
    }

    @Test
    public void testRandomLong() {
        runRandomizationTest(Random::randomLong);
    }

    @Test
    public void testRandomLongWithInclusiveFloor() {
        var floor = new java.util.Random().nextLong() / 2;
        runRandomizationTest(() -> Random.randomLongWithInclusiveFloor(floor),
                l -> assertTrue(l >= floor));
    }

    @Test
    public void testRandomLongWithInclusiveCeiling() {
        var ceiling = new java.util.Random().nextLong() / 2;
        runRandomizationTest(() -> Random.randomLongWithInclusiveCeiling(ceiling),
                l -> assertTrue(l <= ceiling));
    }

    @Test
    public void testRandomLongInRange() {
        var min = -Math.abs(new java.util.Random().nextLong() / 2L);
        var max = -min;
        runRandomizationTest(() -> Random.randomLongInRange(min, max), l -> {
            assertTrue(l >= min);
            assertTrue(l <= max);
        });
    }

    @Test
    public void testRandomFloat() {
        runRandomizationTest(Random::randomFloat);
    }

    @Test
    public void testRandomFloatInRange() {
        var min = -999f;
        var max = -min;
        runRandomizationTest(() -> Random.randomFloatInRange(min, max), f -> {
            assertTrue(f >= min);
            assertTrue(f <= max);
        });
    }

    @Test
    public void testRandomFloatWithInclusiveFloor() {
        var floor = new java.util.Random().nextFloat();

        runRandomizationTest(() -> Random.randomFloatWithInclusiveFloor(floor),
                f -> assertTrue(f >= floor));
    }

    @Test
    public void testRandomFloatWithInclusiveCeiling() {
        var ceiling = new java.util.Random().nextFloat();

        runRandomizationTest(() -> Random.randomFloatWithInclusiveCeiling(ceiling),
                f -> assertTrue(f <= ceiling));
    }

    @Test
    public void testRandomFloatRoundingSafe() {
        runRandomizationTest(Random::randomFloatRoundingSafe, f -> {
            assertTrue(f <= 10000);
            assertTrue(f >= -10000);
        });
    }

    @Test
    public void testRandomDouble() {
        runRandomizationTest(Random::randomDouble);
    }

    @Test
    public void testRandomDoubleInRange() {
        var min = -999d;
        var max = -min;
        runRandomizationTest(() -> Random.randomDoubleInRange(min, max), d -> {
            assertTrue(d >= min);
            assertTrue(d <= max);
        });
    }

    @Test
    public void testRandomDoubleWithInclusiveFloor() {
        var floor = new java.util.Random().nextDouble();

        runRandomizationTest(() -> Random.randomDoubleWithInclusiveFloor(floor),
                d -> assertTrue(d >= floor));
    }

    @Test
    public void testRandomDoubleWithInclusiveCeiling() {
        var ceiling = new java.util.Random().nextDouble();

        runRandomizationTest(() -> Random.randomDoubleWithInclusiveCeiling(ceiling),
                d -> assertTrue(d <= ceiling));
    }

    @Test
    public void testRandomColor() {
        runRandomizationTest(Random::randomColor);
    }

    @Test
    public void testRandomOpaqueColor() {
        runRandomizationTest(Random::randomOpaqueColor, c -> assertEquals(255, c.getAlpha()));
    }

    @Test
    public void testRandomString() {
        runRandomizationTest(Random::randomString, s -> assertEquals(20, s.length()));
    }

    @Test
    public void testRandomChar() {
        runRandomizationTest(Random::randomChar, c -> {
            assertTrue((int) c >= (int) ' ');
            assertTrue((int) c <= (int) '~');
        }, false);
    }

    @Test
    public void testRandomCoordinate2d() {
        runRandomizationTest(Random::randomCoordinate2d);
    }

    @Test
    public void testRandomCoordinate3d() {
        runRandomizationTest(Random::randomCoordinate3d);
    }

    @Test
    public void testRandomVertex() {
        runRandomizationTest(Random::randomVertex);
    }

    @Test
    public void testRandomDirection() {
        runRandomizationTest(Random::randomDirection, false);
    }

    @Test
    public void testRandomFloatBox() {
        runRandomizationTest(Random::randomFloatBox);
    }

    @Test
    public void testRandomValidFloatBox() {
        runRandomizationTest(Random::randomValidFloatBox, f -> {
            assertTrue(f.RIGHT_X >= f.LEFT_X);
            assertTrue(f.BOTTOM_Y >= f.TOP_Y);
        }, true);
    }

    // NB: This is technically indeterminate, but the odds of duplicate random results should be
    //     practically within the same realm of possibility as a duplicate UUID
    private <T> void runRandomizationTest(Supplier<T> randomMethod,
                                          Consumer<T> additionalAssertions,
                                          boolean failOnEquals) {
        for (var i = 0; i < 1000; i++) {
            var val1 = randomMethod.get();
            var val2 = randomMethod.get();
            if (failOnEquals) {
                assertNotEquals(val1, val2);
            }
            if (additionalAssertions != null) {
                additionalAssertions.accept(val1);
                additionalAssertions.accept(val2);
            }
        }
    }

    private <T> void runRandomizationTest(Supplier<T> randomMethod,
                                          Consumer<T> additionalAssertions) {
        runRandomizationTest(randomMethod, additionalAssertions, true);
    }

    private <T> void runRandomizationTest(Supplier<T> randomMethod, boolean failOnEquals) {
        runRandomizationTest(randomMethod, null, failOnEquals);
    }

    private void runRandomizationTest(Supplier<Object> randomMethod) {
        runRandomizationTest(randomMethod, null);
    }
}
