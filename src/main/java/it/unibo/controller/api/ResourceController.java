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
     * @return the bank resources
     */
    Map<ResourceType, Integer> getBankResources();

    /**
     * @param owner    is the owner.
     * @param resource is the resource type.
     * @return the amount of the given resource of the given owner.
     */
    int getOwnerResourceAmount(ResourceOwner owner, ResourceType resource);

    /**
     * * Return if the given owner has the given resources.
     * 
     * @param owner     owner
     * @param resources
     * @return true if the given owner has the given resources, false otherwise
     */
    boolean hasResources(ResourceOwner owner, Map<ResourceType, Integer> resources);

    /**
     * @param player
     * @return whether {@code player} can build a settlement
     */
    boolean hasResourcesForSettlement(ResourceOwner player);

    /**
     * @param player
     * @return whether {@code player} can build a city
     */
    boolean hasResourcesForCity(ResourceOwner player);

    /**
     * @param player
     * @return whether {@code player} can build a road
     */
    boolean hasResourcesForRoad(ResourceOwner player);

    /**
     * Removes resources {@code resources} from player {@code owner}.
     * 
     * @param owner     owner of the resources
     * @param resources map from resource type to amount
     */
    void removeResources(ResourceOwner owner, Map<ResourceType, Integer> resources);

    /**
     * Add resources {@code resources} to player {@code owner}.
     * 
     * @param owner     owner of the resources
     * @param resources map from resource type to amount
     */
    void addResources(ResourceOwner owner, Map<ResourceType, Integer> resources);

    void removeBankResources(Map<ResourceType, Integer> resources);
}
