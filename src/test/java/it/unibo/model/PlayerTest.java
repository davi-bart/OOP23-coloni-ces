package it.unibo.model;

import org.junit.jupiter.api.Test;

import it.unibo.model.impl.PlayerImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;

/**
 * Test class for the player.
 */
class PlayerTest {
    private PlayerImpl player;

    /**
     * Initialization.
     */
    @BeforeEach
    void init() {
        this.player = new PlayerImpl("lucone");
    }

    /**
     * Test addVictoryPoints.
     */
    @Test
    void testAddVictoryPoints() {
        assertEquals(0, player.getVictoryPoints());
        player.incrementVictoryPoints();
        assertEquals(1, player.getVictoryPoints());
        player.incrementVictoryPoints();
        player.incrementVictoryPoints();
        player.incrementVictoryPoints();
        assertEquals(4, player.getVictoryPoints());
    }
}
