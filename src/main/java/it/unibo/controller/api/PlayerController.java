package it.unibo.controller.api;

import it.unibo.model.api.Player;

/**
 * Player controller.
 */
public interface PlayerController {
    /**
     * @param player
     * @return get the given player's name.
     */
    String getPlayerName(Player player);

    /**
     * Increment victory points of the given player.
     * 
     * @param player
     */
    void incrementVictoryPoints(Player player);

    /**
     * @param player
     * @return the victory points of the given player.
     */
    int getVictoryPoints(Player player);

}
