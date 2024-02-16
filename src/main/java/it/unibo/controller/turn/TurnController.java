package it.unibo.controller.turn;

import org.apache.commons.lang3.tuple.Pair;

import it.unibo.model.player.Player;

/**
 * interface of TurnController.
 */
public interface TurnController {
    /**
     * @return get the player currently playing.
     */
    Player getCurrentPlayer();

    /**
     * end the current turn and updates the current player.
     */
    void endTurn();

    /**
     * @return the dice roll.
     */
    Pair<Integer, Integer> rollDie();

    /**
     * @return the number of cycles.
     */
    int getCycle();

    /**
     * @return if the player has rolled.
     */
    boolean hasRolled();

}
