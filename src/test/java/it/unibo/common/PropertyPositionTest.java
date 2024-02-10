package it.unibo.common;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import it.unibo.common.api.PropertyDirection;
import it.unibo.common.api.PropertyPosition;
import it.unibo.common.api.RoadDirection;
import it.unibo.common.api.RoadPosition;
import it.unibo.common.impl.PropertyPositionImpl;
import it.unibo.common.impl.RoadPositionImpl;
import it.unibo.common.impl.TileCoordinatesImpl;

/**
 * Test class for PropertyPosition.
 */
// CHECKSTYLE: MagicNumber OFF
class PropertyPositionTest {
    @Test
    void testEquals() {
        final PropertyPosition position1 = new PropertyPositionImpl(new TileCoordinatesImpl(1, 1),
                PropertyDirection.UPLEFT);
        final PropertyPosition position2 = new PropertyPositionImpl(new TileCoordinatesImpl(1, 1),
                PropertyDirection.UPLEFT);
        assertTrue(position1.equals(position2));
        final PropertyPosition position3 = new PropertyPositionImpl(new TileCoordinatesImpl(1, 0),
                PropertyDirection.UPRIGHT);
        assertTrue(position1.equals(position3));
        final PropertyPosition position4 = new PropertyPositionImpl(new TileCoordinatesImpl(0, 1),
                PropertyDirection.DOWN);
        assertTrue(position1.equals(position4));
        assertTrue(position4.equals(position3));

        final PropertyPosition position5 = new PropertyPositionImpl(new TileCoordinatesImpl(2, 1),
                PropertyDirection.UPLEFT);
        assertFalse(position1.equals(position5));
    }
}

// CHECKSTYLE: MagicNumber ON
