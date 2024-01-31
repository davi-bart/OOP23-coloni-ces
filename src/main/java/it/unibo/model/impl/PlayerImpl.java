package it.unibo.model.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import it.unibo.common.ResourceType;
import it.unibo.model.api.Player;
import it.unibo.model.api.Trader;

/**
 * Implementation of Player.
 */
public final class PlayerImpl implements Player, Trader {
    private final Map<ResourceType, Integer> hand = new HashMap<>();
    private int victoryPoints;
    private final String name;

    /**
     * Create a Player implementation that has the given name.
     * 
     */
    public PlayerImpl(final String name) {
        this.name = name;
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
        } else {
            throw new IllegalArgumentException("amount must be positive");
        }
    }

    @Override
    public void removeResources(final ResourceType resource, final int amount) {
        if (hand.get(resource) >= amount) {
            hand.put(resource, hand.get(resource) - amount);
        } else {
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

    @Override
    public void acceptTrade(final Map<ResourceType, Integer> givenResouces,
            final Map<ResourceType, Integer> recivingResources) {

        for (Entry<ResourceType, Integer> resource : givenResouces.entrySet()) {
            removeResources(resource.getKey(), resource.getValue());
        }
        for (Entry<ResourceType, Integer> resource : recivingResources.entrySet()) {
            addResources(resource.getKey(), resource.getValue());
        }
    }

    @Override
    public boolean canTrade(Map<ResourceType, Integer> givenResouces) {
        for (Entry<ResourceType, Integer> resource : givenResouces.entrySet()) {
            if (getResource(resource.getKey()) < resource.getValue()) {
                return false;
            }
        }
        return true;
    }
}
