package inaugural.soliloquy.tools.random;

import soliloquy.specs.common.shared.Direction;
import soliloquy.specs.common.valueobjects.Coordinate2d;
import soliloquy.specs.common.valueobjects.Coordinate3d;
import soliloquy.specs.common.valueobjects.FloatBox;
import soliloquy.specs.common.valueobjects.Vertex;

import java.awt.*;

import static soliloquy.specs.common.valueobjects.Coordinate2d.coordinate2dOf;
import static soliloquy.specs.common.valueobjects.Coordinate3d.coordinate3dOf;
import static soliloquy.specs.common.valueobjects.FloatBox.floatBoxOf;
import static soliloquy.specs.common.valueobjects.Vertex.vertexOf;

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

    public static short randomShort() {
        return  (short) RANDOM.nextInt(Short.MAX_VALUE + 1);
    }

    public static short randomShortWithInclusiveFloor(short floor) {
        return randomShortInRange(floor, Short.MAX_VALUE);
    }

    public static short randomShortWithInclusiveCeiling(short ceiling) {
        return randomShortInRange(Short.MIN_VALUE, ceiling);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public static short randomShortInRange(short min, short max) {
        return (short) RANDOM.ints(min, max).findFirst().getAsInt();
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

    public static Color randomHighSaturationColor() {
        return Color.getHSBColor(randomFloatInRange(0f,1f), 1f, 1f);
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
        return coordinate2dOf(randomInt(), randomInt());
    }

    public static Coordinate3d randomCoordinate3d() {
        return coordinate3dOf(randomInt(), randomInt(), randomInt());
    }

    public static Vertex randomVertex() {
        return vertexOf(randomFloat(), randomFloat());
    }

    public static Direction randomDirection() {
        var forEnum = randomIntInRange(1,8);
        if (forEnum >= 5) {
            forEnum++;
        }
        return Direction.fromValue(forEnum);
    }

    public static FloatBox randomFloatBox() {
        return floatBoxOf(randomFloat(), randomFloat(), randomFloat(), randomFloat());
    }

    public static FloatBox randomValidFloatBox() {
        var leftX = randomFloat();
        var topY = randomFloat();
        var rightX = randomFloatWithInclusiveFloor(leftX);
        var bottomY = randomFloatWithInclusiveFloor(topY);

        return floatBoxOf(leftX, topY, rightX, bottomY);
    }
}
