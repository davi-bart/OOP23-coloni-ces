package it.unibo.model.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import it.unibo.model.api.Player;
import it.unibo.model.api.TurnManager;

/**
 * implementation of TurnManager.
 */
public final class TurnManagerImpl implements TurnManager {

    private int currentTurn = 0;
    private List<Player> playerList = new ArrayList<>();

    /**
     * constructor of TurnManager.
     * 
     * @param players list of players
     */
    public TurnManagerImpl(final List<Player> players) {
        this.playerList = players;
        setRandomOrder();
    }

    private void setRandomOrder() {
        Collections.shuffle(playerList);
    }

    @Override
    public Player getCurrentPlayerTurn() {
        return playerList.get(currentTurn);
    }

    @Override
    public void endTurn() {
        currentTurn = (currentTurn + 1) % playerList.size();
    }

}
