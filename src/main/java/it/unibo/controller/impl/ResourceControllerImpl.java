package it.unibo.controller.impl;

import java.util.Map;
import java.util.Map.Entry;
import java.util.HashMap;

import it.unibo.common.api.ResourceType;
import it.unibo.common.impl.Recipes;
import it.unibo.controller.api.ResourceController;
import it.unibo.model.api.ResourceManager;
import it.unibo.model.api.ResourceOwner;

/**
 * Resource controller implementation.
 */
public final class ResourceControllerImpl implements ResourceController {

    private final ResourceManager resourceManager;
    private final ResourceOwner bank;

    /**
     * Constructor of resource controller.
     * 
     * @param resourceManager
     * @param bank
     */
    public ResourceControllerImpl(final ResourceManager resourceManager, final ResourceOwner bank) {
        this.resourceManager = resourceManager;
        this.bank = bank;
    }

    @Override
    public Map<ResourceType, Integer> getOwnerResources(final ResourceOwner owner) {
        final Map<ResourceType, Integer> out = new HashMap<>();
        for (final ResourceType resource : ResourceType.values()) {
            out.put(resource, resourceManager.getResource(owner, resource));
        }
        return out;
    }

    @Override
    public int getOwnerResourceAmount(final ResourceOwner owner, final ResourceType resource) {
        return resourceManager.getResource(owner, resource);
    }

    @Override
    public boolean hasResources(final ResourceOwner owner, final Map<ResourceType, Integer> resource) {
        return resourceManager.hasResources(owner, resource);
    }

    @Override
    public Map<ResourceType, Integer> getBankResources() {
        return getOwnerResources(bank);
    }

    @Override
    public boolean hasResourcesForSettlement(final ResourceOwner player) {
        return resourceManager.hasResources(player, Recipes.getSettlementResources());
    }

    @Override
    public boolean hasResourcesForCity(final ResourceOwner player) {
        return resourceManager.hasResources(player, Recipes.getCityResources());
    }

    @Override
    public void removeResources(final ResourceOwner owner, final Map<ResourceType, Integer> resources) {
        for (final Entry<ResourceType, Integer> resource : resources.entrySet()) {
            resourceManager.removeResources(owner, resource.getKey(), resource.getValue());
        }
    }

    @Override
    public boolean hasResourcesForRoad(final ResourceOwner player) {
        return resourceManager.hasResources(player, Recipes.getRoadResources());
    }
}
