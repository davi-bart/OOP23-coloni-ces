package it.unibo.model;

import org.junit.jupiter.api.Test;

import it.unibo.model.player.PlayerImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;

// CHECKSTYLE: MagicNumber OFF
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
        this.player = new PlayerImpl("player1");
    }

    /**
     * Test addVictoryPoints.
     */
    @Test
    void testAddVictoryPoints() {
        assertEquals(0, player.getVictoryPoints());
        player.incrementVictoryPoints(1);
        assertEquals(1, player.getVictoryPoints());
        player.incrementVictoryPoints(1);
        player.incrementVictoryPoints(1);
        player.incrementVictoryPoints(1);
        assertEquals(4, player.getVictoryPoints());
    }
}
