package it.unibo.model.impl;

import java.util.ArrayList;
import java.util.List;

import it.unibo.model.api.Board;
import it.unibo.model.api.GameManager;
import it.unibo.model.api.GameMapGenerator;
import it.unibo.model.api.Player;

/**
 * Implementation of GameManager.
 */
public final class GameManagerImpl implements GameManager {
    private static final int DEFAULT_POINTS_TO_WIN = 10;

    private final Board board;
    private final List<Player> players = new ArrayList<>();
    private final int pointsToWin;

    /**
     * Game manager constructor.
     * 
     * @param generator    game map generator
     * @param playersNames list of players' names
     * @param pointsToWin  points to win the game
     */
    public GameManagerImpl(final GameMapGenerator generator, final List<String> playersNames, final int pointsToWin) {
        this.board = new BoardImpl(generator);
        playersNames.forEach(name -> players.add(new PlayerImpl(name)));
        this.pointsToWin = pointsToWin;
    }

    /**
     * Game manager constructor.
     * 
     * @param playersNames list of players' names
     * @see GameManagerImpl#GameManagerImpl(GameMapGenerator, List, int)
     */
    public GameManagerImpl(final List<String> playersNames) {
        this(new BeginnerGameMapGenerator(), playersNames, DEFAULT_POINTS_TO_WIN);
    }

    @Override
    public void start() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'start'");
    }

    @Override
    public boolean isOver() {
        return players.stream().anyMatch(p -> p.getVictoryPoints() >= pointsToWin);
    }

    @Override
    public Board getBoard() {
        return this.board;
    }

    @Override
    public List<Player> getPlayers() {
        return new ArrayList<>(this.players);
    }
}
