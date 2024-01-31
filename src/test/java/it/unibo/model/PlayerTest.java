package it.unibo.model;

import org.junit.jupiter.api.Test;

import it.unibo.common.ResourceType;
import it.unibo.model.impl.PlayerImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;

/**
 * Test class for the player.
 */
public final class PlayerTest {

    private PlayerImpl player;

    /**
     * Initialization.
     */
    @BeforeEach
    public void init() {
        this.player = new PlayerImpl("lucone");
    }

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
        assertThrows(IllegalArgumentException.class, () -> player.removeResources(ResourceType.BRICK, 4));
        assertThrows(IllegalArgumentException.class, () -> player.addResources(ResourceType.BRICK, -6));

        player.removeResources(ResourceType.BRICK, 2);
        assertEquals(0, player.getResource(ResourceType.BRICK));
    }

    /**
     * Test addVictoryPoints.
     */
    @Test
    public void testAddVictoryPoints() {
        assertEquals(0, player.getVictoryPoints());
        player.incrementVictoryPoints(2);
        assertEquals(2, player.getVictoryPoints());
        player.incrementVictoryPoints(1);
        assertEquals(3, player.getVictoryPoints());
    }

    /**
     * Test acceptTrade.
     */
    @Test
    public void testAcceptTrade() {
        player.addResources(ResourceType.BRICK, 5);
        player.addResources(ResourceType.LUMBER, 10);
        final Map<ResourceType, Integer> givenResouces = new HashMap<>();
        final Map<ResourceType, Integer> recivingResouces = new HashMap<>();
        givenResouces.put(ResourceType.BRICK, Integer.valueOf(2));
        recivingResouces.put(ResourceType.WOOL, Integer.valueOf(2));
        player.acceptTrade(givenResouces, recivingResouces);
        assertEquals(3, player.getResource(ResourceType.BRICK));
        assertEquals(2, player.getResource(ResourceType.WOOL));
    }
}
