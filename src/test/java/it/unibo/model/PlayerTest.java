package it.unibo.model;

import org.junit.jupiter.api.Test;

import it.unibo.common.ResourceType;
import it.unibo.model.api.Player;
import it.unibo.model.impl.PlayerImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {

    Player player = new PlayerImpl("lucone");

    @Test
    public void testInit() {
        for (ResourceType resource : ResourceType.values()) {
            assertEquals(player.getResource(resource), 0);
        }
    }

    @Test
    public void testModifyResources() {
        player.addResources(ResourceType.BRICK, 2);
        assertEquals(2, player.getResource(ResourceType.BRICK));
        player.removeResources(ResourceType.BRICK, 4);
        assertEquals(2, player.getResource(ResourceType.BRICK));
        player.removeResources(ResourceType.BRICK, 2);
        assertEquals(0, player.getResource(ResourceType.BRICK));
    }

    @Test
    public void testAddVictoryPoints() {
        assertEquals(player.getVictoryPoints(), 0);
        player.incrementVictoryPoints(2);
        assertEquals(player.getVictoryPoints(), 2);
        player.incrementVictoryPoints(1);
        assertEquals(player.getVictoryPoints(), 3);
    }
}
