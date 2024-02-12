package it.unibo.controller.impl;

import org.apache.commons.lang3.tuple.Pair;

import it.unibo.controller.api.TurnController;
import it.unibo.model.api.Player;
import it.unibo.model.api.TurnManager;

public class TurnControllerImpl implements TurnController {

    private TurnManager turnManager;

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

}
