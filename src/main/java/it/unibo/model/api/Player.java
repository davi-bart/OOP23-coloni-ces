package it.unibo.model.api;

/**
 * Player.
 */
public interface Player extends ResourceOwner {
    /**
     * Increment the victory points of the player.
     */
    void incrementVictoryPoints();

    /**
     * Get the victory points of the player.
     * 
     * @return victory points of the player
     */
    int getVictoryPoints();
}
