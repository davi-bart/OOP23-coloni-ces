package it.unibo.model.api;

import java.util.Map;

import it.unibo.common.api.ResourceType;

/**
 * A manager that monitor the amount of resources of all players, by adding or
 * removing them.
 */
public interface ResourceManager {

    /**
     * Add the given amount of the given resource to the given player.
     * 
     * @param name     player name
     * @param resource resource type
     * @param amount   amount of resource
     */
    void addResources(String name, ResourceType resource, int amount);

    /**
     * Remove the given amount of the given resource to the given player.
     * 
     * @param name     player name
     * @param resource resource type
     * @param amount   amount of resource
     */
    void removeResources(String name, ResourceType resource, int amount);

    /**
     * Get the amount of the given resource of the given player.
     * 
     * @param name     player name
     * @param resource resource type
     * @return the resource amount
     **/
    int getResource(String name, ResourceType resource);

    /**
     * Modify the resources of the players into the trade.
     * 
     * @param proposer          is the player that propose the trade
     * @param accepter          is the player that accept the trade
     * @param givingResouces    are the resources that the proposer give to the
     *                          accepter
     * @param recivingResources are the resources that the accepter give to the
     *                          proposer
     */
    void acceptTrade(String proposer, String accepter, Map<ResourceType, Integer> givingResouces,
            Map<ResourceType, Integer> recivingResources);

    /**
     * * Return if the given player can trade.
     * The player can trade if it has all the recivingResources.
     * 
     * @param name              player name
     * @param recivingResources
     * @return true if the given player can trade, false otherwise
     */
    boolean canTrade(String name, Map<ResourceType, Integer> recivingResources);
}
