package it.unibo.model.api;

public interface GameManager {

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

}
