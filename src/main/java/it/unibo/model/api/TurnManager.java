package it.unibo.model.api;

/**
 * interface of TurnManager.
 */
public interface TurnManager {

    /**
     * 
     * @return get the player whose turn it currently is.
     */
    Player getCurrentPlayerTurn();

    /**
     * updates the current player.
     */
    void endTurn();
}
