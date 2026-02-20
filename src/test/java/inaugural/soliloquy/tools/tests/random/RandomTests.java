package inaugural.soliloquy.tools.tests.random;

import inaugural.soliloquy.tools.random.Random;
import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static inaugural.soliloquy.tools.random.Random.randomShort;
import static java.awt.Color.RGBtoHSB;
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
    public void testRandomShort() {
        runRandomizationTest(Random::randomShort);
    }

    @Test
    public void testRandomShortWithInclusiveFloor() {
        var floor = randomShort();
        runRandomizationTest(() -> Random.randomShortWithInclusiveFloor(floor),
                i -> assertTrue(i >= floor));
    }

    @Test
    public void testRandomShortWithInclusiveCeiling() {
        var ceiling = randomShort();
        runRandomizationTest(() -> Random.randomShortWithInclusiveCeiling(ceiling),
                i -> assertTrue(i <= ceiling), 10);
    }

    @Test
    public void testRandomShortInRange() {
        var min = (short) -Math.abs(randomShort());
        var max = (short) -min;
        runRandomizationTest(
                () -> Random.randomShortInRange(min, max),
                i -> {
                    assertTrue(i >= min);
                    assertTrue(i <= max);
                },
                true,
                1000,
                10
        );
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
    public void testRandomHighSaturationColor() {
        runRandomizationTest(Random::randomHighSaturationColor, c -> {
                    assertEquals(255, c.getAlpha());
                    var hsb = new float[3];
                    RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), hsb);
                    assertEquals(1f, hsb[1]);
                },
                false, 1000, 10);
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

    // NB: This is technically indeterminate, but for most test cases, that doesn't matter, and
    // failOnEqualsThreshold should be zero. An exception would be something like
    // randomHighSaturationColor, where there are only so many RGB integer combinations, so a
    // very small tolerance for duplicate random entries is needed to properly test that they are
    // indeed being generated randomly.
    @SuppressWarnings("SameParameterValue")
    private <T> void runRandomizationTest(Supplier<T> randomMethod,
                                          Consumer<T> additionalAssertions,
                                          boolean failOnEquals,
                                          int numTests,
                                          int failOnEqualsThreshold) {
        for (var i = 0; i < numTests; i++) {
            var val1 = randomMethod.get();
            var val2 = randomMethod.get();
            var equalsCount = 0;
            if (Objects.equals(val1, val2) && failOnEquals) {
                equalsCount++;
                if (equalsCount > failOnEqualsThreshold) {
                    fail("Number of equal randomized pairs exceeded threshold (" +
                            failOnEqualsThreshold + ")");
                }
            }
            if (additionalAssertions != null) {
                additionalAssertions.accept(val1);
                additionalAssertions.accept(val2);
            }
        }
    }

    private <T> void runRandomizationTest(Supplier<T> randomMethod,
                                          Consumer<T> additionalAssertions) {
        runRandomizationTest(randomMethod, additionalAssertions, true, 1000, 0);
    }

    private <T> void runRandomizationTest(Supplier<T> randomMethod,
                                          Consumer<T> additionalAssertions,
                                          int failOnEqualsThreshold) {
        runRandomizationTest(randomMethod, additionalAssertions, true, 1000, failOnEqualsThreshold);
    }

    private <T> void runRandomizationTest(Supplier<T> randomMethod,
                                          Consumer<T> additionalAssertions,
                                          boolean failOnEquals) {
        runRandomizationTest(randomMethod, additionalAssertions, failOnEquals, 1000, 0);
    }

    @SuppressWarnings("SameParameterValue")
    private <T> void runRandomizationTest(Supplier<T> randomMethod, boolean failOnEquals) {
        runRandomizationTest(randomMethod, null, failOnEquals, 1000, 0);
    }

    private void runRandomizationTest(Supplier<Object> randomMethod) {
        runRandomizationTest(randomMethod, null);
    }
}
