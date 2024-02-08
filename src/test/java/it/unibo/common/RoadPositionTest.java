package it.unibo.common;

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
        assert road1.equals(road2);
        final RoadPosition road3 = new RoadPositionImpl(new TileCoordinatesImpl(1, 2), RoadDirection.UPLEFT);
        assert !road1.equals(road3);
        final RoadPosition road4 = new RoadPositionImpl(new TileCoordinatesImpl(0, 2), RoadDirection.DOWNRIGHT);
        assert road3.equals(road4);
        final RoadPosition road5 = new RoadPositionImpl(new TileCoordinatesImpl(2, 1), RoadDirection.UPRIGHT);
        assert road1.equals(road5);
        assert !road3.equals(road5);
    }
}

// CHECKSTYLE: MagicNumber ON
