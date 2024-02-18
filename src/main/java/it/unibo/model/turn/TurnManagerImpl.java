package it.unibo.model.turn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import it.unibo.model.player.Player;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

/**
 * implementation of TurnManager.
 */
public final class TurnManagerImpl implements TurnManager {

    private boolean hasRolled;
    private int turnNumber;
    private int currentTurn;
    private final boolean canShuffle;
    private final Iterator<Integer> iterator;
    private final List<Player> playerList = new ArrayList<>();
    private final Random random = new Random();
    private static final int MAX_ROLL = 6;
    private static final int MIN_ROLL = 1;

    /**
     * constructor of TurnManager.
     * 
     * @param players list of players
     * @param shuffles wether the player list gets shuffled or not
     */
    public TurnManagerImpl(final List<Player> players, boolean shuffles) {
        players.forEach(playerList::add);
        this.canShuffle = shuffles;
        setRandomOrder();
        /**
         * iterator contains 3 streams:
         * a stream that goes from 1 to the number of players - 1 ,
         * a stream that goes in reverse from the number of players - 1 to 0
         * and a stream that goes from 0 to number of players - 1 infinite times.
         * This stream starts from the second turn since "currentTurn" is already the
         * first turn (it's 0 by default).
         */
        this.iterator = Stream.concat(
                Stream.concat(Stream.iterate(1, i -> i + 1).limit(playerList.size() - 1),
                        Stream.iterate(playerList.size() - 1, i -> i - 1).limit(playerList.size())),
                Stream.iterate(0, i -> (i + 1) % playerList.size())).iterator();
    }

    private void setRandomOrder() {
        if (canShuffle) {
            Collections.shuffle(playerList);
        }
    }

    @Override
    public Player getCurrentPlayer() {
        return playerList.get(currentTurn);
    }

    @Override
    public void endTurn() {
        turnNumber++;
        currentTurn = this.iterator.next();
        hasRolled = false;
    }

    @Override
    public Pair<Integer, Integer> rollDie() {
        hasRolled = true;
        return new ImmutablePair<Integer, Integer>(random.nextInt(MIN_ROLL, MAX_ROLL + 1),
                random.nextInt(MIN_ROLL, MAX_ROLL + 1));
    }

    @Override
    public int getTurnNumber() {
        return turnNumber;
    }

    @Override
    public int getCycle() {
        return turnNumber / playerList.size() + 1;
    }

    @Override
    public boolean hasRolled() {
        return hasRolled;
    }

}
