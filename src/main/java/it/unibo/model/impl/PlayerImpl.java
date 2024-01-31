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
    private int victoryPoint = 0;
    private final String name;

    public PlayerImpl(final String name) {
        this.name = name;
        for (ResourceType resource : ResourceType.values()) {
            hand.put(resource, 0);
        }
    }

    @Override
    public void incrementVictoryPoints(int amount) {
        if (amount > 0) {
            victoryPoint += amount;
        }
    }

    @Override
    public void addResources(ResourceType resource, int amount) {
        if (amount > 0) {
            hand.put(resource, hand.get(resource) + amount);
        }
    }

    @Override
    public void removeResources(ResourceType resource, int amount) {
        if (hand.get(resource) >= amount) {
            hand.put(resource, hand.get(resource) - amount);
        }
    }

    @Override
    public int getResource(ResourceType resource) {
        return hand.get(resource);
    }

    @Override
    public int getVictoryPoints() {
        return this.victoryPoint;
    }


}
