package it.unibo.model.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import it.unibo.common.ResourceType;
import it.unibo.model.api.Player;
import it.unibo.model.api.ResourceManager;
import it.unibo.model.api.Trader;

/**
 * Implementation of Player.
 */
public final class PlayerImpl implements Player, Trader {
    private final Map<ResourceType, Integer> hand = new HashMap<>();
    private int victoryPoints;
    private final ResourceManager resourceManager = new ResourceManagerImpl(this);
    private final String name;

    /**
     * Creates a Player.
     * 
     * @param name the name of the player
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
    public int getVictoryPoints() {
        return this.victoryPoints;
    }

    @Override
    public int getResource(final ResourceType resource) {
        return hand.get(resource);
    }

    @Override
    public void setResource(final ResourceType resource, final int amount) {
        hand.replace(resource, amount);
    }

    @Override
    public void acceptTrade(final Map<ResourceType, Integer> givenResouces,
            final Map<ResourceType, Integer> recivingResources) {

        for (final Entry<ResourceType, Integer> resource : givenResouces.entrySet()) {
            resourceManager.removeResources(resource.getKey(), resource.getValue());
        }
        for (final Entry<ResourceType, Integer> resource : recivingResources.entrySet()) {
            resourceManager.addResources(resource.getKey(), resource.getValue());
        }
    }

    @Override
    public boolean canTrade(final Map<ResourceType, Integer> givenResouces) {
        for (final Entry<ResourceType, Integer> resource : givenResouces.entrySet()) {
            if (getResource(resource.getKey()) < resource.getValue()) {
                return false;
            }
        }
        return true;
    }

}
