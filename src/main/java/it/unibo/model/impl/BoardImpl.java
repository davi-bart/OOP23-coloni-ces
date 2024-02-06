package it.unibo.model.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.unibo.model.api.Board;
import it.unibo.model.api.GameMapGenerator;
import it.unibo.model.api.Tile;

import org.apache.commons.lang3.tuple.Pair;

/**
 * Board implementation.
 */
public final class BoardImpl implements Board {
    private final Map<Pair<Integer, Integer>, Tile> board;

    /**
     * Constructor of the board.
     * 
     * @param generator the map generator
     */
    public BoardImpl(final GameMapGenerator generator) {
        this.board = new HashMap<>(generator.generate());
    }
}
