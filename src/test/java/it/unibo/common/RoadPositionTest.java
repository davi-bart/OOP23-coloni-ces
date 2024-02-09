package it.unibo.common;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import it.unibo.common.api.RoadDirection;
import it.unibo.common.api.RoadPosition;
import it.unibo.common.impl.RoadPositionImpl;
import it.unibo.common.impl.TileCoordinatesImpl;

/**
 * Test class for RoadPosition.
 */
// CHECKSTYLE: MagicNumber OFF
class RoadPositionTest {
    @Test
    void testEquals() {
        final RoadPosition road1 = new RoadPositionImpl(new TileCoordinatesImpl(1, 1), RoadDirection.DOWNLEFT);
        final RoadPosition road2 = new RoadPositionImpl(new TileCoordinatesImpl(1, 1), RoadDirection.DOWNLEFT);
        assertTrue(road1.equals(road2));
        final RoadPosition road3 = new RoadPositionImpl(new TileCoordinatesImpl(1, 2), RoadDirection.UPLEFT);
        assertFalse(road1.equals(road3));
        final RoadPosition road4 = new RoadPositionImpl(new TileCoordinatesImpl(0, 2), RoadDirection.DOWNRIGHT);
        assertTrue(road3.equals(road4));
        final RoadPosition road5 = new RoadPositionImpl(new TileCoordinatesImpl(2, 1), RoadDirection.UPRIGHT);
        assertTrue(road1.equals(road5));
        assertFalse(road3.equals(road5));
    }

    @Test
    void testEquivalent() {
        final RoadPosition road1 = new RoadPositionImpl(new TileCoordinatesImpl(0, 1), RoadDirection.DOWNLEFT);
        final RoadPosition near1 = new RoadPositionImpl(new TileCoordinatesImpl(1, 1), RoadDirection.UPLEFT);
        final RoadPosition near2 = new RoadPositionImpl(new TileCoordinatesImpl(1, 0), RoadDirection.RIGHT);
        final RoadPosition notNear1 = new RoadPositionImpl(new TileCoordinatesImpl(0, 0), RoadDirection.LEFT);
        assertTrue(road1.isNearby(near1));
        assertTrue(road1.isNearby(near2));
        assertFalse(road1.isNearby(notNear1));
    }
}

// CHECKSTYLE: MagicNumber ON
