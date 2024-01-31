package it.unibo.model;

import org.junit.jupiter.api.Test;

import it.unibo.common.ResourceType;
import it.unibo.model.api.Player;
import it.unibo.model.impl.PlayerImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for the player.
 */
public class PlayerTest {

    private final Player player = new PlayerImpl();

    /**
     * Test the initialization of the player.
     */
    @Test
    public void testInit() {
        for (final ResourceType resource : ResourceType.values()) {
            assertEquals(player.getResource(resource), 0);
        }
    }

    /**
     * Test addResources and removeResources.
     */
    @Test
    public void testModifyResources() {
        player.addResources(ResourceType.BRICK, 2);
        assertEquals(2, player.getResource(ResourceType.BRICK));
        player.removeResources(ResourceType.BRICK, 4);
        assertEquals(2, player.getResource(ResourceType.BRICK));
        player.removeResources(ResourceType.BRICK, 2);
        assertEquals(0, player.getResource(ResourceType.BRICK));
    }

    /**
     * Test addVictoryPoints.
     */
    @Test
    public void testAddVictoryPoints() {
        assertEquals(player.getVictoryPoints(), 0);
        player.incrementVictoryPoints(2);
        assertEquals(player.getVictoryPoints(), 2);
        player.incrementVictoryPoints(1);
        assertEquals(player.getVictoryPoints(), 3);
    }
}
