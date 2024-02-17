package it.unibo.view.app;

import it.unibo.view.Drawable;

/**
 * Interface representing the application view.
 */
public interface AppView extends Drawable {
    /**
     * redraw the current player.
     */
    void redrawCurrentPlayer();

    /**
     * redraw the bank.
     */
    void redrawBank();

    /**
     * redraw the players.
     */
    void redrawPlayers();

    /**
     * Log the action.
     * 
     * @param name    name of the player
     * @param message message to display
     */
    void updateLog(String name, String message);

    /**
     * Draws the end game window.
     */
    void drawEndGame();
}
