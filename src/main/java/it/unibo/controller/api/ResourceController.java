package it.unibo.controller.api;

import java.util.Map;

import it.unibo.common.api.ResourceType;
import it.unibo.model.api.ResourceOwner;

/**
 * Resource controller.
 */
public interface ResourceController {
    /**
     * @param owner is the owner.
     * @return a map with all the resources that the passed owner has.
     */
    Map<ResourceType, Integer> getOwnerResources(ResourceOwner owner);

    /**
     * @param owner    is the owner.
     * @param resource is the resource type.
     * @return the amount of the given resource of the given owner.
     */
    int getOwnerResourceAmount(ResourceOwner owner, ResourceType resource);

    /**
     * * Return if the given player can trade.
     * The player can trade if it has all the recivingResources.
     * 
     * @param owner             is the owner.
     * @param recivingResources it the map with the resources that someone want to
     *                          trade.
     * @return true if the given player can trade, false otherwise.
     */
    boolean canTrade(ResourceOwner owner, Map<ResourceType, Integer> recivingResources);

    /**
     * @return the bank
     */
    ResourceOwner getBank();
}
