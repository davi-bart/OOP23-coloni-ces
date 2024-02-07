package it.unibo.model.impl;

import java.util.List;
import java.util.Map;

import it.unibo.common.TerrainType;
import it.unibo.common.TileCoordinates;
import it.unibo.model.api.Board;
import it.unibo.model.api.GameMapGenerator;
import it.unibo.model.api.Tile;

/**
 * Board implementation.
 */
public final class BoardImpl implements Board {
    private final Map<TileCoordinates, Tile> board;

    /**
     * Constructor of the board.
     * 
     * @param generator the map generator
     */
    public BoardImpl(final GameMapGenerator generator) {
        this.board = generator.generate();
    }

    @Override
    public List<TileCoordinates> getTilePositions() {
        return this.board.keySet().stream().toList();
    }

    @Override
    public int getTileNumber(final TileCoordinates pos) {
        if (!this.board.containsKey(pos)) {
            throw new IllegalArgumentException("Position not found");
        }
        return this.board.get(pos).getNumber();
    }

    @Override
    public TerrainType getTileTerrainType(final TileCoordinates pos) {
        if (!this.board.containsKey(pos)) {
            throw new IllegalArgumentException("Position not found");
        }
        return this.board.get(pos).getTerrainType();
    }
}
