package it.unibo.view.app;

/**
 * interface of AppView.
 */
public interface AppView {
    /**
     * draw the full application.
     */
    void draw();

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
     * update the log.
     */
    void updateLog(String name, String message);

    /**
     * Draws the end game window.
     */
    void drawEndGame();
}
