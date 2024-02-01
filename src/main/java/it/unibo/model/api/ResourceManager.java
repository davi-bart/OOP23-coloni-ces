package it.unibo.model.api;

import it.unibo.common.ResourceType;

/**
 * A manager that change the amount of resources, by adding or removing them.
 */
public interface ResourceManager {
    /**
     * Add the given amount of the given resource to the player.
     * 
     * @param resource
     * @param amount
     */
    void addResources(ResourceType resource, int amount);

    /**
     * Remove the given amount of the given resource to the player.
     * 
     * @param resource
     * @param amount
     */
    void removeResources(ResourceType resource, int amount);

}
