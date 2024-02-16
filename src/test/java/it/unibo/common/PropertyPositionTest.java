package it.unibo.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import it.unibo.common.property.PropertyDirection;
import it.unibo.common.property.PropertyPosition;
import it.unibo.common.property.PropertyPositionImpl;
import it.unibo.common.tile.TilePositionImpl;

/**
 * Test class for PropertyPosition.
 */
// CHECKSTYLE: MagicNumber OFF
class PropertyPositionTest {
        @Test
        void testEquals() {
                final PropertyPosition position1 = new PropertyPositionImpl(new TilePositionImpl(1, 1),
                                PropertyDirection.UPLEFT);
                final PropertyPosition position2 = new PropertyPositionImpl(new TilePositionImpl(1, 1),
                                PropertyDirection.UPLEFT);
                assertEquals(position1, position2);
                final PropertyPosition position3 = new PropertyPositionImpl(new TilePositionImpl(1, 0),
                                PropertyDirection.UPRIGHT);
                assertEquals(position1, position3);
                final PropertyPosition position4 = new PropertyPositionImpl(new TilePositionImpl(0, 1),
                                PropertyDirection.DOWN);
                assertEquals(position1, position4);
                assertEquals(position4, position3);
                final PropertyPosition position5 = new PropertyPositionImpl(new TilePositionImpl(2, 1),
                                PropertyDirection.UPLEFT);
                assertNotEquals(position1, position5);
        }

        @Test
        void testIsNear() {
                final PropertyPosition property1 = new PropertyPositionImpl(new TilePositionImpl(0, 1),
                                PropertyDirection.DOWN);
                final PropertyPosition near1 = new PropertyPositionImpl(new TilePositionImpl(0, 1),
                                PropertyDirection.DOWNRIGHT);
                final PropertyPosition near2 = new PropertyPositionImpl(new TilePositionImpl(1, 0),
                                PropertyDirection.DOWNRIGHT);
                final PropertyPosition notNear1 = new PropertyPositionImpl(new TilePositionImpl(0, 1),
                                PropertyDirection.UPRIGHT);
                assertTrue(property1.isNear(near1));
                assertFalse(property1.isNear(notNear1));
                assertFalse(notNear1.isNear(property1));
                assertTrue(near1.isNear(property1));
                assertTrue(near2.isNear(property1));
                assertTrue(property1.isNear(near2));
        }
}

// CHECKSTYLE: MagicNumber ON
