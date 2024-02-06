package it.unibo.model.impl;

import it.unibo.model.api.Board;
import it.unibo.model.api.GameManager;

/**
 * Implementation of GameManager.
 */
public final class GameManagerImpl implements GameManager {
    private final Board board;

    /**
     * Constructor of GameManagerImpl.
     */
    public GameManagerImpl() {
        this.board = new BoardImpl(new RandomGameMapGenerator());
    }

    @Override
    public void start() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'start'");
    }

    @Override
    public boolean isOver() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isOver'");
    }

    @Override
    public Board getBoard() {
        return this.board;
    }
}
