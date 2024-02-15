package it.unibo.view;
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
}
