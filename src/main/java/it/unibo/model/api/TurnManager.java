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
     * @return true if it's the first cycle of turns.
     */
    boolean isFirstCycle();

    /**
     * 
     * @return true if it's the second cycle of turns.
     */
    boolean isSecondCycle();

}
