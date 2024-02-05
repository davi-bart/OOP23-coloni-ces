package it.unibo.model.impl;

import it.unibo.model.api.Player;

/**
 * Implementation of Player.
 */
public final class PlayerImpl implements Player {
    private int victoryPoints;
    private final String name;

    /**
     * Creates a Player.
     * 
     * @param name the name of the player
     */
    public PlayerImpl(final String name) {
        this.name = name;
        victoryPoints = 0;
    }

    @Override
    public void incrementVictoryPoints(final int amount) {
        if (amount > 0) {
            victoryPoints += amount;
        } else {
            throw new IllegalArgumentException("amount must be positive");
        }
    }

    @Override
    public int getVictoryPoints() {
        return this.victoryPoints;
    }
}
