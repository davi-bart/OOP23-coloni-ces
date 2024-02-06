package it.unibo.model.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.unibo.common.TerrainType;
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
        this.board = generator.generate();
    }

    @Override
    public List<Pair<Integer, Integer>> getTilePositions() {
        return this.board.keySet().stream().toList();
    }

    @Override
    public int getTileNumber(Pair<Integer, Integer> pos) {
        if (!this.board.containsKey(pos)) {
            throw new IllegalArgumentException("Position not found");
        }
        return this.board.get(pos).getNumber();
    }

    @Override
    public TerrainType getTileTerrainType(Pair<Integer, Integer> pos) {
        if (!this.board.containsKey(pos)) {
            throw new IllegalArgumentException("Position not found");
        }
        return this.board.get(pos).getTerrainType();
    }
}
