package it.unibo.model.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import it.unibo.common.ResourceType;
import it.unibo.model.api.ResourceManager;

/**
 * An implementation of ResourceManager.
 */
public class ResourceManagerImpl implements ResourceManager {

    private static final int DEFAUL_INIT_VALUE = 0;
    private static final int DEFAUL_BANK_VALUE = 19;

    private final Map<String, Map<ResourceType, Integer>> allEntityResources = new HashMap<>();

    /**
     * Create the ResourceManager from the list of the players.
     * 
     * @param playersName
     */
    public ResourceManagerImpl(final List<String> playersName) {
        for (final String name : playersName) {

            final Map<ResourceType, Integer> currentPlayerResourceAmount = new HashMap<>();
            for (final ResourceType resource : ResourceType.values()) {
                if ("bank".equals(name.toLowerCase(Locale.US))) {
                    currentPlayerResourceAmount.put(resource, Integer.valueOf(DEFAUL_BANK_VALUE));
                } else {
                    currentPlayerResourceAmount.put(resource, Integer.valueOf(DEFAUL_INIT_VALUE));
                }
            }
            allEntityResources.put(name.toLowerCase(Locale.US), currentPlayerResourceAmount);
        }
    }

    @Override
    public final void addResources(final String name, final ResourceType resource, final int amount) {
        if (amount > 0) {
            allEntityResources.get(name).replace(resource, amount + allEntityResources.get(name).get(resource));
        } else {
            throw new IllegalArgumentException("amount must be positive");
        }
    }

    @Override
    public final void removeResources(final String name, final ResourceType resource, final int amount) {
        if (allEntityResources.get(name).get(resource) >= amount) {
            allEntityResources.get(name).replace(resource, allEntityResources.get(name).get(resource) - amount);
        } else {
            throw new IllegalArgumentException("amount must be minor than the total resource");
        }
    }

    @Override
    public final int getResource(final String name, final ResourceType resource) {
        return allEntityResources.get(name).get(resource);
    }

    @Override
    public final void acceptTrade(final String proposer, final String accepter,
            final Map<ResourceType, Integer> givingResouces,
            final Map<ResourceType, Integer> recivingResources) {

        for (final Entry<ResourceType, Integer> resource : givingResouces.entrySet()) {
            removeResources(proposer, resource.getKey(), resource.getValue());
            addResources(accepter, resource.getKey(), resource.getValue());

        }
        for (final Entry<ResourceType, Integer> resource : recivingResources.entrySet()) {
            addResources(proposer, resource.getKey(), resource.getValue());
            removeResources(accepter, resource.getKey(), resource.getValue());
        }
    }

    @Override
    public final boolean canTrade(final String name, final Map<ResourceType, Integer> tradeResource) {
        for (final Entry<ResourceType, Integer> resource : tradeResource.entrySet()) {
            if (resource.getValue() > getResource(name, resource.getKey())) {
                return false;
            }
        }
        return true;
    }
}
