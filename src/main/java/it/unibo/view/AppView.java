package it.unibo.view;

public interface AppView {
    /**
     * draw the full application.
     */
    void draw();

    /**
     * redraw the board.
     */
    void redrawBoard();

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
