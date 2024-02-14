package it.unibo.model.api;

import java.util.List;

/**
 * GameManager.
 */
public interface GameManager {
    /**
     * Check whether the game is over.
     * 
     * @return true if the game is over, false otherwise.
     */
    boolean isGameOver();

    /**
     * @return the list of the players
     */
    List<Player> getPlayers();

    /**
     * Get the board.
     * 
     * @return the board
     */
    Board getBoard();

    /**
     * @return the property manager
     */
    PropertyManager getPropertyManager();

    /**
     * @return the road manager
     */
    RoadManager getRoadManager();

    /**
     * @return the turn manager
     */
    TurnManager getTurnManager();

}
