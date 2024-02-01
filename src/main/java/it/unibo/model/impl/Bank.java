package it.unibo.model.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import it.unibo.common.ResourceType;
import it.unibo.model.api.ResourceManager;
import it.unibo.model.api.Trader;

/**
 * An implementation of a Trader, that represent the bank of the game.
 * Is like a player, but the bank must accept every trade that a player propose
 * to it. At the beginning, the bank has 19 of each resource.
 */
public class Bank implements Trader {
    private final Map<ResourceType, Integer> vault = new HashMap<>();
    private final ResourceManager resourceManager = new ResourceManagerImpl(this);
    private static final int INITIAL_VALUE = 19;

    /**
     * Create a Bank.
     */
    public Bank() {
        for (final ResourceType resource : ResourceType.values()) {
            vault.put(resource, INITIAL_VALUE);
        }
    }

    @Override
    public final void acceptTrade(final Map<ResourceType, Integer> givenResouces,
            final Map<ResourceType, Integer> recivingResources) {
        for (final Entry<ResourceType, Integer> resource : givenResouces.entrySet()) {
            resourceManager.removeResources(resource.getKey(), resource.getValue());
        }
    }

    @Override
    public final boolean canTrade(final Map<ResourceType, Integer> tradeResource) {
        for (final Entry<ResourceType, Integer> resource : tradeResource.entrySet()) {
            if (resource.getValue() > getResource(resource.getKey())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public final int getResource(final ResourceType resource) {
        return vault.get(resource);
    }

    @Override
    public final void setResource(final ResourceType resource, final int amount) {
        vault.replace(resource, amount);
    }

}
