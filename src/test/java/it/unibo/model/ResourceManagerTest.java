package it.unibo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.common.ResourceType;
import it.unibo.model.api.ResourceManager;
import it.unibo.model.impl.ResourceManagerImpl;

public class ResourceManagerTest {
    private ResourceManager rm = new ResourceManagerImpl(List.of("Luca", "Andrea", "Alex", "Sbara", "Bank"));

    @Test
    public void testAddResources() {
        assertEquals(0, rm.getResource("luca", ResourceType.BRICK));
        rm.addResources("luca", ResourceType.BRICK, 3);
        assertEquals(3, rm.getResource("luca", ResourceType.BRICK));

    }
}
