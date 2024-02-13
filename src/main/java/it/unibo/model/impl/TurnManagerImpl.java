package it.unibo.model.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import it.unibo.model.api.Player;
import it.unibo.model.api.TurnManager;
import java.util.Random;
import java.util.stream.Stream;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

/**
 * implementation of TurnManager.
 */
public final class TurnManagerImpl implements TurnManager {

    private int turnNumber = 1;
    private int currentTurn;
    private final Iterator<Integer> iterator;
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
        /**
         * iterator contains 3 streams:
         * a stream that goes from 1 to the number of players ,
         * a stream that goes in reverse from the number of players to 1
         * and a stream that goes from 1 to 4 infinite times.
         */
        this.iterator = Stream.concat(
                Stream.concat(Stream.iterate(0, i -> i + 1).limit(playerList.size()),
                        Stream.iterate(playerList.size() - 1, i -> i - 1).limit(playerList.size())),
                Stream.iterate(0, i -> (i + 1) % playerList.size())).iterator();
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
        currentTurn = this.iterator.next();
    }

    @Override
    public Pair<Integer, Integer> rollDie() {
        return new ImmutablePair<Integer, Integer>(random.nextInt(MIN_ROLL, MAX_ROLL + 1),
                random.nextInt(MIN_ROLL, MAX_ROLL + 1));
    }

    @Override
    public int getTurnNumber() {
        return turnNumber;
    }

}
