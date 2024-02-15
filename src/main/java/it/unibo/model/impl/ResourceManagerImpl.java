package it.unibo.model.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import it.unibo.common.api.tile.ResourceType;
import it.unibo.model.api.Player;
import it.unibo.model.api.ResourceManager;
import it.unibo.model.api.ResourceOwner;

/**
 * An implementation of ResourceManager.
 */
public final class ResourceManagerImpl implements ResourceManager {

    private final Map<ResourceOwner, Map<ResourceType, Integer>> allEntityResources = new HashMap<>();
    private final ResourceOwner bank;

    /**
     * Create the ResourceManager from the list of the resource owner(such as
     * players and bank).
     * 
     * @param players             the list of players
     * @param bankResourcesAmount the amount of resources in the bank
     */
    public ResourceManagerImpl(final List<Player> players, final int bankResourcesAmount) {
        players.forEach(ro -> allEntityResources.put(ro, ro.getDefaultResources()));
        bank = new BankImpl(bankResourcesAmount);
        allEntityResources.put(bank, bank.getDefaultResources());
    }

    @Override
    public final void addResources(final ResourceOwner owner, final ResourceType resource, final int amount) {
        if (amount >= 0) {
            allEntityResources.get(owner).compute(resource, (k, v) -> v + amount);
        } else {
            throw new IllegalArgumentException("amount must be positive");
        }
    }

    @Override
    public final void removeResources(final ResourceOwner owner, final ResourceType resource, final int amount) {
        if (allEntityResources.get(owner).get(resource) >= amount) {
            allEntityResources.get(owner).compute(resource, (k, v) -> v - amount);
        } else {
            throw new IllegalArgumentException("amount must be lower than the total resource");
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
    public final boolean hasResources(final ResourceOwner owner, final Map<ResourceType, Integer> resources) {
        for (final Entry<ResourceType, Integer> resource : resources.entrySet()) {
            if (resource.getValue() > getResource(owner, resource.getKey())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ResourceOwner getBank() {
        return this.bank;
    }

    @Override
    public Map<ResourceType, Integer> getResources(ResourceOwner owner) {
        return allEntityResources.get(owner);
    }
}
