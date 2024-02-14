package it.unibo.model.impl;

import java.util.HashMap;
import java.util.Map;

import it.unibo.common.api.tile.ResourceType;
import it.unibo.model.api.ResourceOwner;

/**
 * Implementation of the bank.
 */
public final class BankImpl implements ResourceOwner {

    private final int defaultResourceAmount;

    /**
     * Create the bank.
     * 
     * @param defaultResourceAmount is the default amount of resources of the bank.
     */
    public BankImpl(final int defaultResourceAmount) {
        this.defaultResourceAmount = defaultResourceAmount;
    }

    @Override
    public Map<ResourceType, Integer> getDefaultResources() {
        final Map<ResourceType, Integer> resources = new HashMap<>();
        for (final ResourceType resource : ResourceType.values()) {
            resources.put(resource, defaultResourceAmount);
        }
        return resources;
    }
}
