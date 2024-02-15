package it.unibo.model.api;

/**
 * Player.
 */
public interface Player extends ResourceOwner {
    /**
     * Increments the victory points of the player of {@code amount} points.
     * 
     * @param amount
     */
    void incrementVictoryPoints(int amount);

    /**
     * Decrements the victory points of the player of {@code amount} points.
     * 
     * @param amount
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
