package inaugural.soliloquy.tools.tests;

import inaugural.soliloquy.tools.NearestFloorAndCeilingTree;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NearestFloorAndCeilingTreeTests {
    private final List<Integer> BOUNDARIES = List.of(5, 10, 15, 20);

    private NearestFloorAndCeilingTree _nearestFloorAndCeilingTree;

    @BeforeEach
    void setUp() {
        _nearestFloorAndCeilingTree = NearestFloorAndCeilingTree.FromIntegers(BOUNDARIES);
    }

    @Test
    void testConstructorWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () ->
                NearestFloorAndCeilingTree.FromIntegers(null));
        assertThrows(IllegalArgumentException.class, () ->
                NearestFloorAndCeilingTree.FromIntegers(new ArrayList<>()));
        assertThrows(IllegalArgumentException.class, () ->
                NearestFloorAndCeilingTree.FromLongs(null));
        assertThrows(IllegalArgumentException.class, () ->
                NearestFloorAndCeilingTree.FromLongs(new ArrayList<>()));
    }

    @Test
    void testGetNearestFloor() {
        assertEquals(5, _nearestFloorAndCeilingTree.getNearestFloor(4));
        assertEquals(5, _nearestFloorAndCeilingTree.getNearestFloor(5));
        assertEquals(5, _nearestFloorAndCeilingTree.getNearestFloor(8));
        assertEquals(10, _nearestFloorAndCeilingTree.getNearestFloor(10));
        assertEquals(10, _nearestFloorAndCeilingTree.getNearestFloor(12));
        assertEquals(15, _nearestFloorAndCeilingTree.getNearestFloor(15));
        assertEquals(15, _nearestFloorAndCeilingTree.getNearestFloor(18));
        assertEquals(20, _nearestFloorAndCeilingTree.getNearestFloor(20));
        assertEquals(20, _nearestFloorAndCeilingTree.getNearestFloor(23));
    }

    @Test
    void testGetNearestCeiling() {
        assertEquals(5, _nearestFloorAndCeilingTree.getNearestCeiling(4));
        assertEquals(5, _nearestFloorAndCeilingTree.getNearestCeiling(5));
        assertEquals(10, _nearestFloorAndCeilingTree.getNearestCeiling(8));
        assertEquals(10, _nearestFloorAndCeilingTree.getNearestCeiling(10));
        assertEquals(15, _nearestFloorAndCeilingTree.getNearestCeiling(12));
        assertEquals(15, _nearestFloorAndCeilingTree.getNearestCeiling(15));
        assertEquals(20, _nearestFloorAndCeilingTree.getNearestCeiling(18));
        assertEquals(20, _nearestFloorAndCeilingTree.getNearestCeiling(20));
        assertEquals(20, _nearestFloorAndCeilingTree.getNearestCeiling(23));
    }

    @Test
    void testMinimumValue() {
        assertEquals(5, _nearestFloorAndCeilingTree.MinimumValue);
    }

    @Test
    void testMaximumValue() {
        assertEquals(20, _nearestFloorAndCeilingTree.MaximumValue);
    }

    @Test
    void testOrderedValues() {
        assertEquals(4, _nearestFloorAndCeilingTree.OrderedValues.length);
        assertEquals(5, _nearestFloorAndCeilingTree.OrderedValues[0]);
        assertEquals(10, _nearestFloorAndCeilingTree.OrderedValues[1]);
        assertEquals(15, _nearestFloorAndCeilingTree.OrderedValues[2]);
        assertEquals(20, _nearestFloorAndCeilingTree.OrderedValues[3]);
    }

    @Test
    void testValueIndices() {
        assertEquals(4, _nearestFloorAndCeilingTree.ValueIndices.size());
        assertEquals(0, (int) _nearestFloorAndCeilingTree.ValueIndices.get(5L));
        assertEquals(1, (int) _nearestFloorAndCeilingTree.ValueIndices.get(10L));
        assertEquals(2, (int) _nearestFloorAndCeilingTree.ValueIndices.get(15L));
        assertEquals(3, (int) _nearestFloorAndCeilingTree.ValueIndices.get(20L));
    }

    @Test
    void testIncrementAllValues() {
        long increment = 100L;

        _nearestFloorAndCeilingTree.incrementAllValues(increment);

        assertEquals(5 + increment,
                _nearestFloorAndCeilingTree.getNearestFloor(4 + increment));
        assertEquals(5 + increment,
                _nearestFloorAndCeilingTree.getNearestFloor(5 + increment));
        assertEquals(5 + increment,
                _nearestFloorAndCeilingTree.getNearestFloor(8 + increment));
        assertEquals(10 + increment,
                _nearestFloorAndCeilingTree.getNearestFloor(10 + increment));
        assertEquals(10 + increment,
                _nearestFloorAndCeilingTree.getNearestFloor(12 + increment));
        assertEquals(15 + increment,
                _nearestFloorAndCeilingTree.getNearestFloor(15 + increment));
        assertEquals(15 + increment,
                _nearestFloorAndCeilingTree.getNearestFloor(18 + increment));
        assertEquals(20 + increment,
                _nearestFloorAndCeilingTree.getNearestFloor(20 + increment));
        assertEquals(20 + increment,
                _nearestFloorAndCeilingTree.getNearestFloor(23 + increment));


        assertEquals(5 + increment,
                _nearestFloorAndCeilingTree.getNearestCeiling(4 + increment));
        assertEquals(5 + increment,
                _nearestFloorAndCeilingTree.getNearestCeiling(5 + increment));
        assertEquals(10 + increment,
                _nearestFloorAndCeilingTree.getNearestCeiling(8 + increment));
        assertEquals(10 + increment,
                _nearestFloorAndCeilingTree.getNearestCeiling(10 + increment));
        assertEquals(15 + increment,
                _nearestFloorAndCeilingTree.getNearestCeiling(12 + increment));
        assertEquals(15 + increment,
                _nearestFloorAndCeilingTree.getNearestCeiling(15 + increment));
        assertEquals(20 + increment,
                _nearestFloorAndCeilingTree.getNearestCeiling(18 + increment));
        assertEquals(20 + increment,
                _nearestFloorAndCeilingTree.getNearestCeiling(20 + increment));
        assertEquals(20 + increment,
                _nearestFloorAndCeilingTree.getNearestCeiling(23 + increment));

        assertEquals(5 + increment, _nearestFloorAndCeilingTree.MinimumValue);

        assertEquals(20 + increment, _nearestFloorAndCeilingTree.MaximumValue);

        assertEquals(4, _nearestFloorAndCeilingTree.OrderedValues.length);
        assertEquals(5 + increment, _nearestFloorAndCeilingTree.OrderedValues[0]);
        assertEquals(10 + increment, _nearestFloorAndCeilingTree.OrderedValues[1]);
        assertEquals(15 + increment, _nearestFloorAndCeilingTree.OrderedValues[2]);
        assertEquals(20 + increment, _nearestFloorAndCeilingTree.OrderedValues[3]);

        assertEquals(4, _nearestFloorAndCeilingTree.ValueIndices.size());
        assertEquals(0, (int) _nearestFloorAndCeilingTree.ValueIndices.get(5L + increment));
        assertEquals(1, (int) _nearestFloorAndCeilingTree.ValueIndices.get(10L + increment));
        assertEquals(2, (int) _nearestFloorAndCeilingTree.ValueIndices.get(15L + increment));
        assertEquals(3, (int) _nearestFloorAndCeilingTree.ValueIndices.get(20L + increment));
    }
}
