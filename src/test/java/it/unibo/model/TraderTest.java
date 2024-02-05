package it.unibo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.common.ResourceType;
import it.unibo.model.api.ResourceManager;
import it.unibo.model.api.Trader;
import it.unibo.model.impl.PlayerImpl;
import it.unibo.model.impl.ResourceManagerImpl;

/**
 * Test for a trader istance.
 */
// CHECKSTYLE: MagicNumber OFF

class TraderTest {
    private Trader player;
    private ResourceManager rm;

    /**
     * Initialization.
     */
    @BeforeEach
    void init() {
        this.player = new PlayerImpl("lucone");
        this.rm = new ResourceManagerImpl(player);
    }

    /**
     * Test the initialization of the player.
     */
    @Test
    void testInit() {
        for (final ResourceType resource : ResourceType.values()) {
            assertEquals(0, player.getResource(resource));
        }
    }

    /**
     * Test addResources and removeResources.
     */
    @Test
    void testModifyResources() {
        rm.addResources(ResourceType.BRICK, 2);
        assertEquals(2, player.getResource(ResourceType.BRICK));
        assertThrows(IllegalArgumentException.class, () -> rm.removeResources(ResourceType.BRICK, 4));
        assertThrows(IllegalArgumentException.class, () -> rm.addResources(ResourceType.BRICK, -6));

        rm.removeResources(ResourceType.BRICK, 2);
        assertEquals(0, player.getResource(ResourceType.BRICK));
    }

    /**
     * Test acceptTrade.
     */
    @Test
    void testAcceptTrade() {
        rm.addResources(ResourceType.BRICK, 5);
        rm.addResources(ResourceType.LUMBER, 10);
        final Map<ResourceType, Integer> givenResouces = new HashMap<>();
        final Map<ResourceType, Integer> recivingResouces = new HashMap<>();
        givenResouces.put(ResourceType.BRICK, Integer.valueOf(2));
        recivingResouces.put(ResourceType.WOOL, Integer.valueOf(2));
        player.acceptTrade(givenResouces, recivingResouces);
        assertEquals(3, player.getResource(ResourceType.BRICK));
        assertEquals(2, player.getResource(ResourceType.WOOL));
    }
}
// CHECKSTYLE: MagicNumber ON
