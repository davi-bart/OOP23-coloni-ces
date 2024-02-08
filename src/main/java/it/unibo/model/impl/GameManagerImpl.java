package it.unibo.model.impl;

import java.util.ArrayList;
import java.util.List;

import it.unibo.model.api.Board;
import it.unibo.model.api.GameManager;
import it.unibo.model.api.Player;

/**
 * Implementation of GameManager.
 */
public final class GameManagerImpl implements GameManager {
    private final Board board;
    private final List<Player> players;

    /**
     * Constructor of GameManagerImpl.
     */
    public GameManagerImpl(final List<String> playersNames) {
        this.board = new BoardImpl(new BeginnerGameMapGenerator());
        this.players = new ArrayList<>();
        playersNames.forEach(name -> this.players.add(new PlayerImpl(name)));
    }

    @Override
    public void start() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'start'");
    }

    @Override
    public boolean isOver() {
        final int pointsToWin = 10;
        return players.stream().anyMatch(p -> p.getVictoryPoints() >= pointsToWin);
    }

    @Override
    public Board getBoard() {
        return this.board;
    }
}
