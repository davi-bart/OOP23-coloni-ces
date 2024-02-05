package it.unibo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.common.ResourceType;
import it.unibo.model.api.ResourceManager;
import it.unibo.model.impl.ResourceManagerImpl;

// CHECKSTYLE: MagicNumber OFF
/**
 * Test for the Resource Manager.
 */
class ResourceManagerTest {

    private ResourceManager rm;

    @BeforeEach
    void init() {
        this.rm = new ResourceManagerImpl(List.of("Luca", "Andrea", "Alex", "Sbara", "Bank"));
    }

    /**
     * Test addResources.
     */
    @Test
    public void testAddResources() {
        assertEquals(0, rm.getResource("luca", ResourceType.BRICK));
        rm.addResources("luca", ResourceType.BRICK, 3);
        assertEquals(3, rm.getResource("luca", ResourceType.BRICK));

    }

    /**
     * Test removeResources.
     */
    @Test
    public void testRemoveResources() {
        assertEquals(0, rm.getResource("luca", ResourceType.BRICK));
        rm.addResources("luca", ResourceType.BRICK, 5);
        rm.removeResources("luca", ResourceType.BRICK, 3);
        assertEquals(2, rm.getResource("luca", ResourceType.BRICK));

    }

    /**
     * Test trade.
     */
    @Test
    public void testTrade() {
        final Map<ResourceType, Integer> givingResource = new HashMap<>();
        final Map<ResourceType, Integer> recivingResource = new HashMap<>();

        rm.addResources("luca", ResourceType.BRICK, 5);
        rm.addResources("luca", ResourceType.WOOL, 5);
        rm.addResources("luca", ResourceType.GRAIN, 5);
        rm.addResources("alex", ResourceType.LUMBER, 5);
        rm.addResources("alex", ResourceType.ORE, 5);
        recivingResource.put(ResourceType.BRICK, 2);
        recivingResource.put(ResourceType.WOOL, 3);
        assertTrue(rm.canTrade("luca", recivingResource));
        givingResource.put(ResourceType.LUMBER, 2);
        givingResource.put(ResourceType.ORE, 4);
        rm.acceptTrade("alex", "luca", givingResource, recivingResource);
        assertEquals(3, rm.getResource("luca", ResourceType.BRICK));
        assertEquals(2, rm.getResource("luca", ResourceType.WOOL));
        assertEquals(3, rm.getResource("alex", ResourceType.LUMBER));
        assertEquals(1, rm.getResource("alex", ResourceType.ORE));

    }
    // CHECKSTYLE: MagicNumber ON

}
