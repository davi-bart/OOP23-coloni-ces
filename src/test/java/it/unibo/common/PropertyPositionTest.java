package it.unibo.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import it.unibo.common.api.PropertyDirection;
import it.unibo.common.api.PropertyPosition;
import it.unibo.common.impl.PropertyPositionImpl;
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
        assertEquals(position1, position2);
        final PropertyPosition position3 = new PropertyPositionImpl(new TileCoordinatesImpl(1, 0),
                PropertyDirection.UPRIGHT);
        assertEquals(position1, position3);
        final PropertyPosition position4 = new PropertyPositionImpl(new TileCoordinatesImpl(0, 1),
                PropertyDirection.DOWN);
        assertEquals(position1, position4);
        assertEquals(position4, position3);
        final PropertyPosition position5 = new PropertyPositionImpl(new TileCoordinatesImpl(2, 1),
                PropertyDirection.UPLEFT);
        assertNotEquals(position1, position5);
    }
}

// CHECKSTYLE: MagicNumber ON
