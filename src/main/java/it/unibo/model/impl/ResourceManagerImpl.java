package it.unibo.model.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import it.unibo.common.ResourceType;
import it.unibo.model.api.ResourceManager;
import it.unibo.model.api.ResourceOwner;

/**
 * An implementation of ResourceManager.
 */
public class ResourceManagerImpl implements ResourceManager {

    private final Map<ResourceOwner, Map<ResourceType, Integer>> allEntityResources = new HashMap<>();

    /**
     * Create the ResourceManager from the list of the resource owner(such as players and bank).
     * 
     * @param resourceOwners 
     */
    public ResourceManagerImpl(final List<ResourceOwner> resourceOwners) {
        resourceOwners.forEach(ro -> allEntityResources.put(ro, ro.getDefaultResources()));
    }

    @Override
    public final void addResources(final ResourceOwner owner, final ResourceType resource, final int amount) {
        if (amount > 0) {
            // allEntityResources.get(owner).replace(resource, amount + allEntityResources.get(owner).get(resource));
            allEntityResources.get(owner).compute(resource, (k, v) -> v + amount);
        } else {
            throw new IllegalArgumentException("amount must be positive");
        }
    }

    @Override
    public final void removeResources(final ResourceOwner owner, final ResourceType resource, final int amount) {
        if (allEntityResources.get(owner).get(resource) >= amount) {
            // allEntityResources.get(owner).replace(resource, allEntityResources.get(owner).get(resource) - amount);
            allEntityResources.get(owner).compute(resource, (k, v) -> v - amount);
        } else {
            throw new IllegalArgumentException("amount must be minor than the total resource");
        }
    }

    @Override
    public final int getResource(final ResourceOwner owner, final ResourceType resource) {
        return allEntityResources.get(owner).get(resource);
    }

    @Override
    public final void acceptTrade(final ResourceOwner proposer, final ResourceOwner accepter,
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
    public final boolean canTrade(final ResourceOwner owner, final Map<ResourceType, Integer> tradeResource) {
        for (final Entry<ResourceType, Integer> resource : tradeResource.entrySet()) {
            if (resource.getValue() > getResource(owner, resource.getKey())) {
                return false;
            }
        }
        return true;
    }
}
