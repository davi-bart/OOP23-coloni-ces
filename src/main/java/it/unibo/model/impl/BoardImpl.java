package it.unibo.model.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.unibo.model.api.Board;
import it.unibo.model.api.Tile;

import org.apache.commons.lang3.tuple.Pair;

/**
 * Board implementation.
 */
public final class BoardImpl implements Board {
    private final Map<Pair<Integer, Integer>, Tile> board;

    /**
     * Constructor of BoardImpl.
     * 
     * @param board the board to start with
     */
    public BoardImpl(final Map<Pair<Integer, Integer>, Tile> board) {
        this.board = new HashMap<>(board);
    }

    @Override
    public void generateBoard() {
    }

    @Override
    public List<Pair<Integer, Integer>> getTilePositions() {
        return board.keySet().stream().toList();
    }
}
