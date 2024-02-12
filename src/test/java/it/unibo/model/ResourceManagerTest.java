package it.unibo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.common.api.ResourceType;
import it.unibo.model.api.ResourceManager;
import it.unibo.model.api.ResourceOwner;
import it.unibo.model.impl.PlayerImpl;
import it.unibo.model.impl.ResourceManagerImpl;

// CHECKSTYLE: MagicNumber OFF
/**
 * Test for the Resource Manager.
 */
class ResourceManagerTest {

    private ResourceManager resourceManager;
    private static final ResourceOwner PLAYER1 = new PlayerImpl("luca");
    private static final ResourceOwner PLAYER2 = new PlayerImpl("andrea");
    private static final ResourceOwner PLAYER3 = new PlayerImpl("alex");
    private static final ResourceOwner PLAYER4 = new PlayerImpl("sbara");

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
        resourceManager.addResources(PLAYER1, ResourceType.BRICK, 3);
        assertEquals(3, resourceManager.getResource(PLAYER1, ResourceType.BRICK));

    }

    /**
     * Test removeResources.
     */
    @Test
    void testRemoveResources() {
        assertEquals(0, resourceManager.getResource(PLAYER1, ResourceType.BRICK));
        resourceManager.addResources(PLAYER1, ResourceType.BRICK, 5);
        resourceManager.removeResources(PLAYER1, ResourceType.BRICK, 3);
        assertEquals(2, resourceManager.getResource(PLAYER1, ResourceType.BRICK));

    }

    /**
     * Test trade.
     */
    @Test
    void testTrade() {
        final Map<ResourceType, Integer> givingResource = new HashMap<>();
        final Map<ResourceType, Integer> recivingResource = new HashMap<>();

        resourceManager.addResources(PLAYER1, ResourceType.BRICK, 5);
        resourceManager.addResources(PLAYER1, ResourceType.WOOL, 5);
        resourceManager.addResources(PLAYER1, ResourceType.GRAIN, 5);
        resourceManager.addResources(PLAYER2, ResourceType.LUMBER, 5);
        resourceManager.addResources(PLAYER2, ResourceType.ORE, 5);
        recivingResource.put(ResourceType.BRICK, 2);
        recivingResource.put(ResourceType.WOOL, 3);
        assertTrue(resourceManager.hasResources(PLAYER1, recivingResource));
        givingResource.put(ResourceType.LUMBER, 2);
        givingResource.put(ResourceType.ORE, 4);
        resourceManager.acceptTrade(PLAYER2, PLAYER1, givingResource, recivingResource);
        assertEquals(3, resourceManager.getResource(PLAYER1, ResourceType.BRICK));
        assertEquals(2, resourceManager.getResource(PLAYER1, ResourceType.WOOL));
        assertEquals(3, resourceManager.getResource(PLAYER2, ResourceType.LUMBER));
        assertEquals(1, resourceManager.getResource(PLAYER2, ResourceType.ORE));

    }
    // CHECKSTYLE: MagicNumber ON

}
