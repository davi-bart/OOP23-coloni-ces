package it.unibo.model.impl;

import java.util.HashMap;
import java.util.Map;

import it.unibo.common.ResourceType;
import it.unibo.model.api.Player;

/**
 * Implementation of Player.
 */
public final class PlayerImpl implements Player {
    private final Map<ResourceType, Integer> hand = new HashMap<>();
    private int victoryPoints;

    /**
     * Create a Player implementation that has the given name.
     * 
     */
    public PlayerImpl() {
        victoryPoints = 0;
        for (final ResourceType resource : ResourceType.values()) {
            hand.put(resource, 0);
        }
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
    public void addResources(final ResourceType resource, final int amount) {
        if (amount > 0) {
            hand.put(resource, hand.get(resource) + amount);
        }else {
            throw new IllegalArgumentException("amount must be positive");
        }
    }

    @Override
    public void removeResources(final ResourceType resource, final int amount) {
        if (hand.get(resource) >= amount) {
            hand.put(resource, hand.get(resource) - amount);
        }else {
            throw new IllegalArgumentException("amount must be minor than the total resource");
        }
    }

    @Override
    public int getResource(final ResourceType resource) {
        return hand.get(resource);
    }

    @Override
    public int getVictoryPoints() {
        return this.victoryPoints;
    }
}
