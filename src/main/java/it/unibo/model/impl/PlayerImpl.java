package it.unibo.model.impl;

import java.util.HashMap;
import java.util.Map;

import it.unibo.common.api.tile.ResourceType;
import it.unibo.model.api.Player;

/**
 * Implementation of Player.
 */
public final class PlayerImpl implements Player {
    private int victoryPoints;
    private final String name;
    private final int defaultValue;

    /**
     * Creates a Player.
     * 
     * @param name the name of the player
     */
    public PlayerImpl(final String name) {
        this.name = name;
        this.defaultValue = 0;
        victoryPoints = 0;
    }

    /**
     * Creates a Player.
     * 
     * @param name         the name of the player
     * @param defaultValue the dafault value of the initial resources
     */
    public PlayerImpl(final String name, final int defaultValue) {
        this.name = name;
        this.defaultValue = defaultValue;
        victoryPoints = 0;
    }

    @Override
    public void incrementVictoryPoints(final int amount) {
        checkPointsAmount(amount);
        sumPoints(amount);
    }

    @Override
    public void decrementVictoryPoints(final int amount) {
        checkPointsAmount(amount);
        sumPoints(-amount);
    }

    @Override
    public int getVictoryPoints() {
        return this.victoryPoints;
    }

    @Override
    public Map<ResourceType, Integer> getDefaultResources() {
        final Map<ResourceType, Integer> resources = new HashMap<>();
        for (final ResourceType resource : ResourceType.values()) {
            resources.put(resource, defaultValue);
        }
        return resources;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        return this.name.equals(((Player) obj).getName());
    }

    private void sumPoints(final int amount) {
        victoryPoints += amount;
    }

    private void checkPointsAmount(final int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be non negative");
        }
    }
}
