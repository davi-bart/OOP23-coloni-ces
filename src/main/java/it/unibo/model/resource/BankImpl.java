package it.unibo.model.resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.unibo.common.tile.ResourceType;

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
        List.of(ResourceType.values()).forEach(resource -> resources.put(resource, defaultResourceAmount));
        return resources;
    }
}
