package it.unibo.controller.impl;

import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.HashMap;

import it.unibo.common.api.tile.ResourceType;
import it.unibo.common.impl.Recipes;
import it.unibo.controller.api.ResourceController;
import it.unibo.model.api.Player;
import it.unibo.model.api.ResourceManager;
import it.unibo.model.api.ResourceOwner;

/**
 * Resource controller implementation.
 */
public final class ResourceControllerImpl implements ResourceController {

    private final ResourceManager resourceManager;
    private final ResourceOwner bank;
    private final Function<String, Player> getPlayerByName;

    /**
     * Constructor of resource controller.
     * 
     * @param resourceManager
     * @param bank
     */
    public ResourceControllerImpl(final Function<String, Player> getPlayerByName,
            final ResourceManager resourceManager) {
        this.bank = resourceManager.getBank();
        this.getPlayerByName = getPlayerByName;
        this.resourceManager = resourceManager;
    }

    @Override
    public Map<ResourceType, Integer> getOwnerResources(final String owner) {
        final Map<ResourceType, Integer> out = new HashMap<>();
        for (final ResourceType resource : ResourceType.values()) {
            out.put(resource, resourceManager.getResource(getPlayerByName.apply(owner), resource));
        }
        return out;
    }

    @Override
    public int getOwnerResourceAmount(final String owner, final ResourceType resource) {
        return resourceManager.getResource(getPlayerByName.apply(owner), resource);
    }

    @Override
    public boolean hasResources(final String owner, final Map<ResourceType, Integer> resource) {
        return resourceManager.hasResources(getPlayerByName.apply(owner), resource);
    }

    @Override
    public Map<ResourceType, Integer> getBankResources() {
        return resourceManager.getResources(bank);
    }

    @Override
    public boolean hasResourcesForSettlement(final String player) {
        return resourceManager.hasResources(getPlayerByName.apply(player), Recipes.getSettlementResources());
    }

    @Override
    public boolean hasResourcesForCity(final String player) {
        return resourceManager.hasResources(getPlayerByName.apply(player), Recipes.getCityResources());
    }

    @Override
    public void removeResources(final String owner, final Map<ResourceType, Integer> resources) {
        for (final Entry<ResourceType, Integer> resource : resources.entrySet()) {
            resourceManager.removeResources(getPlayerByName.apply(owner), resource.getKey(), resource.getValue());
        }
    }

    @Override
    public boolean hasResourcesForRoad(final String player) {
        return resourceManager.hasResources(getPlayerByName.apply(player), Recipes.getRoadResources());
    }

    @Override
    public void addResources(final String owner, final Map<ResourceType, Integer> resources) {
        for (final Entry<ResourceType, Integer> resource : resources.entrySet()) {
            resourceManager.addResources(getPlayerByName.apply(owner), resource.getKey(),
                    resources.get(resource.getKey()));
        }
    }

    @Override
    public void removeBankResources(final Map<ResourceType, Integer> resources) {
        for (final Entry<ResourceType, Integer> resource : resources.entrySet()) {
            resourceManager.removeResources(bank, resource.getKey(), resource.getValue());
        }
    }

    @Override
    public void acceptTrade(final String proposer, final String accepter,
            final Map<ResourceType, Integer> givingResouces,
            final Map<ResourceType, Integer> recivingResources) {
        resourceManager.acceptTrade(getPlayerByName.apply(proposer), getPlayerByName.apply(accepter), givingResouces,
                recivingResources);
    }

    @Override
    public Map<ResourceType, Integer> getResources(String owner) {
        return resourceManager.getResources(getPlayerByName.apply(owner));
    }

}
