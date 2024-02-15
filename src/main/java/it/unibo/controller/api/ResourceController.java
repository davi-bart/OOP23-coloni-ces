package it.unibo.controller.api;

import java.util.Map;

import it.unibo.common.api.tile.ResourceType;

/**
 * Resource controller.
 */
public interface ResourceController {
    /**
     * @param owner is the owner.
     * @return a map with all the resources that the passed owner has.
     */
    Map<ResourceType, Integer> getOwnerResources(String owner);

    /**
     * @return the bank resources
     */
    Map<ResourceType, Integer> getBankResources();

    /**
     * @param owner    is the owner.
     * @param resource is the resource type.
     * @return the amount of the given resource of the given owner.
     */
    int getOwnerResourceAmount(String owner, ResourceType resource);

    /**
     * * Return if the given owner has the given resources.
     * 
     * @param owner     owner
     * @param resources
     * @return true if the given owner has the given resources, false otherwise
     */
    boolean hasResources(String owner, Map<ResourceType, Integer> resources);

    /**
     * @param player
     * @return whether {@code player} can build a settlement
     */
    boolean hasResourcesForSettlement(String player);

    /**
     * @param player
     * @return whether {@code player} can build a city
     */
    boolean hasResourcesForCity(String player);

    /**
     * @param player
     * @return whether {@code player} can build a road
     */
    boolean hasResourcesForRoad(String player);

    /**
     * Removes resources {@code resources} from player {@code owner}.
     * 
     * @param owner     owner of the resources
     * @param resources map from resource type to amount
     */
    void removeResources(String owner, Map<ResourceType, Integer> resources);

    /**
     * Add resources {@code resources} to player {@code owner}.
     * 
     * @param owner     owner of the resources
     * @param resources map from resource type to amount
     */
    void addResources(String owner, Map<ResourceType, Integer> resources);

    /**
     * Remove given resources from bank.
     * 
     * @param resources map from resource type to amount
     */
    void removeBankResources(Map<ResourceType, Integer> resources);

    /**
     * Modify the resources of the owners into the trade.
     * 
     * @param proposer          is the owner that propose the trade
     * @param accepter          is the owner that accept the trade
     * @param givingResouces    are the resources that the proposer give to the
     *                          accepter
     * @param recivingResources are the resources that the accepter give to the
     *                          proposer
     */
    void acceptTrade(String proposer, String accepter, Map<ResourceType, Integer> givingResouces,
            Map<ResourceType, Integer> recivingResources);

    /**
     * 
     * @param owner
     * @return all the resources owned by the owner.
     */
    Map<ResourceType, Integer> getResources(String owner);
}
