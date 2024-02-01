package it.unibo.model.api;

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
     * Get the victory points of the player.
     * 
     * @return victory points of the player
     */
    int getVictoryPoints();
}
