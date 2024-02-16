package it.unibo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.common.tile.ResourceType;
import it.unibo.model.player.Player;
import it.unibo.model.player.PlayerImpl;
import it.unibo.model.resource.ResourceManager;
import it.unibo.model.resource.ResourceManagerImpl;

// CHECKSTYLE: MagicNumber OFF
/**
 * Test for the Resource Manager.
 */
class ResourceManagerTest {

    private ResourceManager resourceManager;
    private static final Player PLAYER1 = new PlayerImpl("luca");
    private static final Player PLAYER2 = new PlayerImpl("andrea");
    private static final Player PLAYER3 = new PlayerImpl("alex");
    private static final Player PLAYER4 = new PlayerImpl("sbara");

    @BeforeEach
    void init() {
        this.resourceManager = new ResourceManagerImpl(List.of(PLAYER1, PLAYER2, PLAYER3, PLAYER4));
    }

    /**
     * Test addResources.
     */
    @Test
    void testAddResources() {
        assertEquals(0, resourceManager.getResource(PLAYER1, ResourceType.BRICK));
        resourceManager.addResources(PLAYER1, Map.of(ResourceType.BRICK, 3));
        assertEquals(3, resourceManager.getResource(PLAYER1, ResourceType.BRICK));
    }

    /**
     * Test removeResources.
     */
    @Test
    void testRemoveResources() {
        assertEquals(0, resourceManager.getResource(PLAYER1, ResourceType.BRICK));
        resourceManager.addResources(PLAYER1, Map.of(ResourceType.BRICK, 5));
        resourceManager.removeResources(PLAYER1, Map.of(ResourceType.BRICK, 3));
        assertEquals(2, resourceManager.getResource(PLAYER1, ResourceType.BRICK));
    }

    /**
     * Test trade.
     */
    @Test
    void testTrade() {
        final Map<ResourceType, Integer> proposedResources = new HashMap<>();
        final Map<ResourceType, Integer> wantedResources = new HashMap<>();

        resourceManager.addResources(PLAYER1, Map.of(ResourceType.BRICK, 5, ResourceType.WOOL, 5, ResourceType.GRAIN, 5,
                ResourceType.LUMBER, 5, ResourceType.ORE, 5));
        resourceManager.addResources(PLAYER2, Map.of(ResourceType.BRICK, 2, ResourceType.WOOL, 3));
        wantedResources.put(ResourceType.BRICK, 2);
        wantedResources.put(ResourceType.WOOL, 3);
        assertTrue(resourceManager.hasResources(PLAYER1, wantedResources));
        proposedResources.put(ResourceType.LUMBER, 2);
        proposedResources.put(ResourceType.ORE, 4);

        assertTrue(resourceManager.canTrade(PLAYER1, PLAYER2, proposedResources, wantedResources));
        resourceManager.trade(PLAYER1, PLAYER2, proposedResources, wantedResources);

        assertEquals(7, resourceManager.getResource(PLAYER1, ResourceType.BRICK));
        assertEquals(8, resourceManager.getResource(PLAYER1, ResourceType.WOOL));
        assertEquals(3, resourceManager.getResource(PLAYER1, ResourceType.LUMBER));
        assertEquals(1, resourceManager.getResource(PLAYER1, ResourceType.ORE));

        assertEquals(0, resourceManager.getResource(PLAYER2, ResourceType.BRICK));
        assertEquals(0, resourceManager.getResource(PLAYER2, ResourceType.WOOL));
        assertEquals(2, resourceManager.getResource(PLAYER2, ResourceType.LUMBER));
        assertEquals(4, resourceManager.getResource(PLAYER2, ResourceType.ORE));

    }
}
