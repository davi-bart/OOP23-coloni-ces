package it.unibo.model.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import it.unibo.model.api.Player;
import it.unibo.model.api.TurnManager;
import java.util.Random;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

/**
 * implementation of TurnManager.
 */
public final class TurnManagerImpl implements TurnManager {

    private int turnNumber = 1;
    private int currentTurn;
    private final List<Player> playerList = new ArrayList<>();
    private final Random random = new Random();
    private static final int MAX_ROLL = 6;
    private static final int MIN_ROLL = 1;

    /**
     * constructor of TurnManager.
     * 
     * @param players list of players
     */
    public TurnManagerImpl(final List<Player> players) {
        players.forEach(playerList::add);
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
        turnNumber++;
        currentTurn = (currentTurn + 1) % playerList.size();
    }

    @Override
    public Pair<Integer, Integer> rollDie() {
        return new ImmutablePair<Integer, Integer>(random.nextInt(MIN_ROLL, MAX_ROLL + 1),
                random.nextInt(MIN_ROLL, MAX_ROLL + 1));
    }

    @Override
    public int getTurn() {
        return turnNumber;
    }

}
