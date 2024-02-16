package it.unibo.controller.impl;

import java.util.Map;
import java.util.function.Function;
import java.util.HashMap;

import it.unibo.common.api.tile.ResourceType;
import it.unibo.controller.api.ResourceController;
import it.unibo.model.api.ResourceManager;
import it.unibo.model.api.ResourceOwner;

/**
 * Resource controller implementation.
 */
public final class ResourceControllerImpl implements ResourceController {

    private final ResourceManager resourceManager;
    private final ResourceOwner bank;
    private final Function<String, ResourceOwner> getResourceOwnerByName;

    /**
     * Constructor of resource controller.
     * 
     * @param getResourceOwnerByName the function to get the resource owner by name
     * @param resourceManager        the resource manager
     */
    public ResourceControllerImpl(final Function<String, ResourceOwner> getResourceOwnerByName,
            final ResourceManager resourceManager) {
        this.bank = resourceManager.getBank();
        this.getResourceOwnerByName = getResourceOwnerByName;
        this.resourceManager = resourceManager;
    }

    @Override
    public Map<ResourceType, Integer> getPlayerResources(final String owner) {
        final Map<ResourceType, Integer> out = new HashMap<>();
        for (final ResourceType resource : ResourceType.values()) {
            out.put(resource, resourceManager.getResource(getResourceOwnerByName.apply(owner), resource));
        }
        return out;
    }

    @Override
    public int getOwnerResourceAmount(final String owner, final ResourceType resource) {
        return resourceManager.getResource(getResourceOwnerByName.apply(owner), resource);
    }

    @Override
    public boolean hasResources(final String owner, final Map<ResourceType, Integer> resource) {
        return resourceManager.hasResources(getResourceOwnerByName.apply(owner), resource);
    }

    @Override
    public Map<ResourceType, Integer> getBankResources() {
        return resourceManager.getResources(bank);
    }

    @Override
    public void acceptTrade(final String proposer, final String accepter,
            final Map<ResourceType, Integer> givingResouces,
            final Map<ResourceType, Integer> recivingResources) {
        resourceManager.acceptTrade(getResourceOwnerByName.apply(proposer), getResourceOwnerByName.apply(accepter),
                givingResouces,
                recivingResources);
    }

    @Override
    public Map<ResourceType, Integer> getResources(final String owner) {
        return resourceManager.getResources(getResourceOwnerByName.apply(owner));
    }

    @Override
    public boolean canTradeWithPlayer(String proposer, String accepter,
            Map<ResourceType, Integer> proposedResouces, Map<ResourceType, Integer> wantedResources) {
        return resourceManager.canTrade(getResourceOwnerByName.apply(proposer), getResourceOwnerByName.apply(accepter),
                proposedResouces, wantedResources);
    }

    @Override
    public boolean canTradeWithBank(String proposer, Map<ResourceType, Integer> proposedResouces,
            Map<ResourceType, Integer> wantedResources) {
        return resourceManager.canTrade(getResourceOwnerByName.apply(proposer), bank, proposedResouces,
                wantedResources);
    }

}
