package it.unibo.controller.turn;

import org.apache.commons.lang3.tuple.Pair;

import it.unibo.model.player.Player;
import it.unibo.model.turn.TurnManager;

/**
 * Turn controller implementation.
 */
public final class TurnControllerImpl implements TurnController {

    private final TurnManager turnManager;

    /**
     * Constructori of TurnControllerImpl.
     * 
     * @param turnManager
     */
    public TurnControllerImpl(final TurnManager turnManager) {
        this.turnManager = turnManager;
    }

    @Override
    public Player getCurrentPlayerTurn() {
        return this.turnManager.getCurrentPlayerTurn();
    }

    @Override
    public void endTurn() {
        this.turnManager.endTurn();
    }

    @Override
    public Pair<Integer, Integer> rollDie() {
        return this.turnManager.rollDie();
    }

    @Override
    public int getCycle() {
        return this.turnManager.getCycle();
    }

    @Override
    public boolean hasRolled() {
        return this.turnManager.hasRolled();
    }

}
