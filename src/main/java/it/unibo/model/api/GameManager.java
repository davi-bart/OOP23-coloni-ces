package it.unibo.model.api;

import java.util.List;

/**
 * GameManager.
 */
public interface GameManager {
    /**
     * Get the board.
     * 
     * @return the board
     */
    Board getBoard();

    /**
     * Starts the game.
     */
    void start();

    /**
     * Check whether the game is over.
     * 
     * @return true if the game is over, false otherwise.
     */
    boolean isOver();

    /**
     * @return the list of the players
     */
    List<Player> getPlayers();

}
