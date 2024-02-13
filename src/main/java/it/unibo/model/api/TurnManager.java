package it.unibo.model.api;

import org.apache.commons.lang3.tuple.Pair;

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
     * end the current turn and updates the current player.
     */
    void endTurn();

    /**
     * 
     * @return the dice roll.
     */
    Pair<Integer, Integer> rollDie();

    /**
     * 
     * @return the number of the current turn.
     */
    int getTurnNumber();

    /**
     * 
     * @return the number of cycles.
     */
    int getCycle();

    /**
     * 
     * @return if the player has rolled.
     */
    boolean hasRolled();

}
