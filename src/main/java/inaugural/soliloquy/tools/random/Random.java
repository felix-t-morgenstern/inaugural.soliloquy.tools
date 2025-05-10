package inaugural.soliloquy.tools.random;

import soliloquy.specs.common.shared.Direction;
import soliloquy.specs.common.valueobjects.Coordinate2d;
import soliloquy.specs.common.valueobjects.Coordinate3d;

import java.awt.*;

public class Random {
    public static java.util.Random RANDOM = new java.util.Random();

    private final static float ROUNDING_SAFE_BOUND = 10000f;

    public static boolean randomBoolean() {
        return RANDOM.nextBoolean();
    }

    public static int randomInt() {
        return RANDOM.nextInt();
    }

    public static int randomIntWithInclusiveFloor(int floor) {
        return randomIntInRange(floor, Integer.MAX_VALUE);
    }

    public static int randomIntWithInclusiveCeiling(int ceiling) {
        return randomIntInRange(Integer.MIN_VALUE, ceiling);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public static int randomIntInRange(int min, int max) {
        return RANDOM.ints(min, max).findFirst().getAsInt();
    }

    public static long randomLong() {
        return RANDOM.nextLong();
    }

    public static long randomLongWithInclusiveFloor(long floor) {
        return randomLongInRange(floor, Long.MAX_VALUE);
    }

    public static long randomLongWithInclusiveCeiling(long ceiling) {
        return randomLongInRange(Long.MIN_VALUE, ceiling);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public static long randomLongInRange(long min, long max) {
        return RANDOM.longs(min, max).findFirst().getAsLong();
    }

    public static float randomFloat() {
        return RANDOM.nextFloat();
    }

    public static float randomFloatInRange(float min, float max) {
        return min + ((max - min) * RANDOM.nextFloat());
    }

    public static float randomFloatWithInclusiveFloor(float floor) {
        return randomFloatInRange(floor, Float.MAX_VALUE);
    }

    public static float randomFloatWithInclusiveCeiling(float ceiling) {
        return randomFloatInRange(Float.MIN_VALUE, ceiling);
    }

    // NB: This method is used to generate float values which are random, but which still
    // generate values which are not so extreme that single point rounding invalidates
    // otherwise-valid tests with rounding inaccuracy.
    public static float randomFloatRoundingSafe() {
        return randomFloatInRange(ROUNDING_SAFE_BOUND, -ROUNDING_SAFE_BOUND);
    }

    public static double randomDouble() {
        return RANDOM.nextDouble();
    }

    public static double randomDoubleInRange(double min, double max) {
        return min + ((max - min) * RANDOM.nextDouble());
    }

    public static double randomDoubleWithInclusiveFloor(double floor) {
        return randomDoubleInRange(floor, Double.MAX_VALUE);
    }

    public static double randomDoubleWithInclusiveCeiling(double ceiling) {
        return randomDoubleInRange(Double.MIN_VALUE, ceiling);
    }

    public static Color randomColor() {
        return new Color(randomIntInRange(0, 255), randomIntInRange(0, 255),
                randomIntInRange(0, 255), randomIntInRange(0, 255));
    }

    public static Color randomOpaqueColor() {
        return new Color(randomIntInRange(0, 255), randomIntInRange(0, 255),
                randomIntInRange(0, 255), 255);
    }

    // NB: Taken from Baeldung, at https://www.baeldung.com/java-random-string, accessed on
    // 2022/04/19
    public static String randomString() {
        var leftLimit = 48; // numeral '0'
        var rightLimit = 122; // letter 'z'
        var targetStringLength = 20;

        return RANDOM.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    // NB: Taken from Delftstack, at
    // https://www.delftstack.com/howto/java/java-random-character/, accessed on 2022/07/26
    public static char randomChar() {
        return (char) (RANDOM.nextInt(95) + ' ');
    }

    public static Coordinate2d randomCoordinate2d() {
        return Coordinate2d.of(randomInt(), randomInt());
    }

    public static Coordinate3d randomCoordinate3d() {
        return Coordinate3d.of(randomInt(), randomInt(), randomInt());
    }

    public static Direction randomDirection() {
        var forEnum = randomIntInRange(1,8);
        if (forEnum >= 5) {
            forEnum++;
        }
        return Direction.fromValue(forEnum);
    }
}
