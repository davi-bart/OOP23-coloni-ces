package it.unibo.model;

import org.junit.jupiter.api.Test;

import it.unibo.model.impl.PlayerImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
