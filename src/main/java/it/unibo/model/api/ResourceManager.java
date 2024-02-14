package it.unibo.model.api;

import java.util.Map;

import it.unibo.common.api.tile.ResourceType;

/**
 * A manager that monitor the amount of resources of all players, by adding or
 * removing them.
 */
public interface ResourceManager {

    /**
     * Add the given amount of the given resource to the given owner.
     * 
     * @param owner    owner of the resources
     * @param resource resource type
     * @param amount   amount of resource
     */
    void addResources(ResourceOwner owner, ResourceType resource, int amount);

    /**
     * Remove the given amount of the given resource to the given owner.
     * 
     * @param owner    owner of the resources
     * @param resource resource type
     * @param amount   amount of resource
     */
    void removeResources(ResourceOwner owner, ResourceType resource, int amount);

    /**
     * Get the amount of the given resource of the given owner.
     * 
     * @param owner    owner of the resources
     * @param resource resource type
     * @return the resource amount
     **/
    int getResource(ResourceOwner owner, ResourceType resource);

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
    void acceptTrade(ResourceOwner proposer, ResourceOwner accepter, Map<ResourceType, Integer> givingResouces,
            Map<ResourceType, Integer> recivingResources);

    /**
     * * Return if the given owner has the given resources.
     * 
     * @param owner     owner
     * @param resources
     * @return true if the given owner has the given resources, false otherwise
     */
    boolean hasResources(ResourceOwner owner, Map<ResourceType, Integer> resources);
}
