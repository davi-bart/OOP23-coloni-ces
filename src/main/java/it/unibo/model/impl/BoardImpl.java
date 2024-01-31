package it.unibo.model.impl;

import java.util.HashMap;
import java.util.Map;

import it.unibo.model.api.Board;
import it.unibo.model.api.Tile;

import org.apache.commons.lang3.tuple.ImmutablePair;

/**
 * Board implementation.
 */
public final class BoardImpl implements Board {
    private final Map<ImmutablePair<Integer, Integer>, Tile> board;

    /**
     * Constructor of board impl
     * 
     * @param board the board to start with
     */
    public BoardImpl(Map<ImmutablePair<Integer, Integer>, Tile> board) {
        this.board = board;
    }

    @Override
    public void generateBoard() {
    }
}
