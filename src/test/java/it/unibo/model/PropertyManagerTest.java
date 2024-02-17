package it.unibo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.common.property.PropertyDirection;
import it.unibo.common.property.PropertyPosition;
import it.unibo.common.property.PropertyPositionImpl;
import it.unibo.common.property.PropertyType;
import it.unibo.common.tile.TilePositionImpl;
import it.unibo.model.player.Player;
import it.unibo.model.player.PlayerImpl;
import it.unibo.model.property.Property;
import it.unibo.model.property.PropertyImpl;
import it.unibo.model.property.PropertyManager;
import it.unibo.model.property.PropertyManagerImpl;

// CHECKSTYLE: MagicNumber OFF
/**
 * Test for the Property Manager.
 */
class PropertyManagerTest {

    private PropertyManager propertyManager;
    private static final Player PLAYER1 = new PlayerImpl("player1");
    private PropertyPosition pos1;
    private PropertyPosition pos2;
    private PropertyPosition pos3;

    @BeforeEach
    void init() {
        this.propertyManager = new PropertyManagerImpl();
        this.pos1 = new PropertyPositionImpl(new TilePositionImpl(0, 1), PropertyDirection.DOWN);
        this.pos2 = new PropertyPositionImpl(new TilePositionImpl(0, 1), PropertyDirection.UP);
        this.pos3 = new PropertyPositionImpl(new TilePositionImpl(0, 2), PropertyDirection.UP);
    }

    /**
     * Test settlement.
     */
    @Test
    void testSettlement() {
        propertyManager.addSettlement(pos1, PLAYER1);
        assertEquals(Optional.of(PLAYER1), propertyManager.getPropertyOwner(pos1));
        assertEquals(PropertyType.SETTLEMENT, propertyManager.getPropertyType(pos1));
    }

    /**
     * Test city.
     */
    @Test
    void testCity() {
        propertyManager.addSettlement(pos1, PLAYER1);
        propertyManager.upgradeToCity(pos1);
        assertEquals(Optional.of(PLAYER1), propertyManager.getPropertyOwner(pos1));
        assertEquals(PropertyType.CITY, propertyManager.getPropertyType(pos1));
        assertThrows(IllegalArgumentException.class, () -> propertyManager.upgradeToCity(pos2));
    }

    @Test
    void testGetPlayerProperties() {
        propertyManager.addSettlement(pos1, PLAYER1);
        propertyManager.addSettlement(pos2, PLAYER1);
        for (Property property : propertyManager.getPlayerProperties(PLAYER1)) {
            assertTrue(
                    Set.of(new PropertyImpl(pos2, PLAYER1).getPosition(), new PropertyImpl(pos1, PLAYER1).getPosition())
                            .contains(property.getPosition()));
        }
    }

    @Test
    void testGetPropertyOwner() {
        propertyManager.addSettlement(pos2, PLAYER1);
        assertEquals(Optional.of(PLAYER1), propertyManager.getPropertyOwner(pos2));
        assertEquals(Optional.empty(), propertyManager.getPropertyOwner(pos3));
    }
}
