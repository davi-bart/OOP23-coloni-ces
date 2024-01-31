package it.unibo.model.api;

import it.unibo.common.ResourceType;

/**
 * Player.
 */
public interface Player {
    /**
     * Increment the victory point of the player by the given amount.
     * 
     * @param amount
     * 
     */
    void incrementVictoryPoints(int amount);

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

    /**
     * Get the amount of the given resource that the player have.
     * 
     * @param resource
     * @return the amount of the given resource
     */
    int getResource(ResourceType resource);

    /**
     * Get the victory points of the player.
     * 
     * @return victory points of the player
     */
    int getVictoryPoints();
}
