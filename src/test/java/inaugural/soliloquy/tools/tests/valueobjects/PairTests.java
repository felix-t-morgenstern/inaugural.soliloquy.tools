package inaugural.soliloquy.tools.tests.valueobjects;

import org.junit.jupiter.api.Test;
import soliloquy.specs.common.valueobjects.Pair;

import java.util.Objects;

import static inaugural.soliloquy.tools.random.Random.randomInt;
import static inaugural.soliloquy.tools.random.Random.randomString;
import static inaugural.soliloquy.tools.valueobjects.Pair.pairOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PairTests {
    private final Integer ITEM_1 = randomInt();
    private final String ITEM_2 = randomString();
    private final Integer ARCHETYPE_1 = randomInt();
    private final String ARCHETYPE_2 = randomString();

    @Test
    public void testPairOf() {
        var pair = pairOf(ITEM_1, ITEM_2);

        assertEquals(ITEM_1, pair.item1());
        assertEquals(ITEM_2, pair.item2());
        assertEquals(ITEM_1, pair.firstArchetype());
        assertEquals(ITEM_2, pair.secondArchetype());
    }

    @Test
    public void testPairOfWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> pairOf(null, ITEM_2));
        assertThrows(IllegalArgumentException.class, () -> pairOf(ITEM_1, null));
    }

    @Test
    public void testPairOfWithArchetypes() {
        var pair = pairOf(ITEM_1, ITEM_2, ARCHETYPE_1, ARCHETYPE_2);

        assertEquals(ITEM_1, pair.item1());
        assertEquals(ITEM_2, pair.item2());
        assertEquals(ARCHETYPE_1, pair.firstArchetype());
        assertEquals(ARCHETYPE_2, pair.secondArchetype());
    }

    @Test
    public void testPairOfWithArchetypesWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> pairOf(ITEM_1, ITEM_2, null, ARCHETYPE_2));
        assertThrows(IllegalArgumentException.class,
                () -> pairOf(ITEM_1, ITEM_2, ARCHETYPE_1, null));
    }

    @Test
    public void testMakeClone() {
        var pair = pairOf(ITEM_1, ITEM_2, ARCHETYPE_1, ARCHETYPE_2);

        var clone = pair.makeClone();

        assertEquals(pair.item1(), clone.item1());
        assertEquals(pair.firstArchetype(), clone.firstArchetype());
        assertEquals(pair.item2(), clone.item2());
        assertEquals(pair.secondArchetype(), clone.secondArchetype());
    }

    @Test
    public void testGetInterfaceName() {
        var pair = pairOf(ITEM_1, ITEM_2);

        assertEquals(Pair.class.getCanonicalName(), pair.getInterfaceName());
    }

    @Test
    public void testEquals() {
        var pair1 = pairOf(ITEM_1, ITEM_2, randomInt(), randomString());
        var pair2 = pairOf(ITEM_1, ITEM_2, randomInt(), randomString());

        assertEquals(pair1, pair2);
    }

    @Test
    public void testHashCode() {
        var pair = pairOf(ITEM_1, ITEM_2);

        assertEquals(Objects.hash(ITEM_1, ITEM_2), pair.hashCode());
    }
}
