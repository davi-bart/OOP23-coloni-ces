package it.unibo.controller.turn;

import org.apache.commons.lang3.tuple.Pair;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.model.player.Player;
import it.unibo.model.turn.TurnManager;

/**
 * Turn controller implementation.
 */
public final class TurnControllerImpl implements TurnController {

    private final TurnManager turnManager;

    /**
     * Constructor of TurnControllerImpl.
     * 
     * @param turnManager the turn manager
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "The turn manager needs to be updated")
    public TurnControllerImpl(final TurnManager turnManager) {
        this.turnManager = turnManager;
    }

    @Override
    public Player getCurrentPlayer() {
        return this.turnManager.getCurrentPlayer();
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
