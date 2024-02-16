package it.unibo.model.player;

import it.unibo.model.resource.ResourceOwner;

/**
 * Player.
 */
public interface Player extends ResourceOwner {
    /**
     * Increments the victory points of the player of {@code amount} points.
     * 
     * @param amount the amount of points to add
     */
    void incrementVictoryPoints(int amount);

    /**
     * Decrements the victory points of the player of {@code amount} points.
     * 
     * @param amount the amount of points to remove
     */
    void decrementVictoryPoints(int amount);

    /**
     * Get the victory points of the player.
     * 
     * @return victory points of the player
     */
    int getVictoryPoints();

    /**
     * @return the player's name
     */
    String getName();
}
