package it.unibo.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import it.unibo.common.road.RoadDirection;
import it.unibo.common.road.RoadPosition;
import it.unibo.common.road.RoadPositionImpl;
import it.unibo.common.tile.TilePositionImpl;

/**
 * Test class for RoadPosition.
 */
// CHECKSTYLE: MagicNumber OFF
class RoadPositionTest {
    @Test
    void testEquals() {
        final RoadPosition road1 = new RoadPositionImpl(new TilePositionImpl(1, 1), RoadDirection.DOWNLEFT);
        final RoadPosition road2 = new RoadPositionImpl(new TilePositionImpl(1, 1), RoadDirection.DOWNLEFT);
        assertEquals(road1, road2);
        final RoadPosition road3 = new RoadPositionImpl(new TilePositionImpl(1, 2), RoadDirection.UPLEFT);
        assertNotEquals(road1, road3);
        final RoadPosition road4 = new RoadPositionImpl(new TilePositionImpl(0, 2), RoadDirection.DOWNRIGHT);
        assertEquals(road3, road4);
        final RoadPosition road5 = new RoadPositionImpl(new TilePositionImpl(2, 1), RoadDirection.UPRIGHT);
        assertEquals(road1, road5);
        assertNotEquals(road3, road5);
    }

    @Test
    void testNearby() {
        final RoadPosition road1 = new RoadPositionImpl(new TilePositionImpl(0, 1), RoadDirection.DOWNLEFT);
        final RoadPosition near1 = new RoadPositionImpl(new TilePositionImpl(1, 1), RoadDirection.UPLEFT);
        final RoadPosition near2 = new RoadPositionImpl(new TilePositionImpl(1, 0), RoadDirection.RIGHT);
        final RoadPosition notNear1 = new RoadPositionImpl(new TilePositionImpl(0, 0), RoadDirection.LEFT);
        assertTrue(road1.isNearby(near1));
        assertTrue(road1.isNearby(near2));
        assertFalse(road1.isNearby(notNear1));
    }
}
