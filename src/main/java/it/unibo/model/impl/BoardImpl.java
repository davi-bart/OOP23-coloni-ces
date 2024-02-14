package it.unibo.model.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import it.unibo.common.api.tile.TerrainType;
import it.unibo.common.api.tile.TilePosition;
import it.unibo.model.api.Board;
import it.unibo.model.api.GameMapGenerator;
import it.unibo.model.api.Tile;

/**
 * Board implementation.
 */
public final class BoardImpl implements Board {
    private final Map<TilePosition, Tile> board;
    private TilePosition robberPosition;

    /**
     * Constructor of the board.
     * 
     * @param generator the map generator
     */
    public BoardImpl(final GameMapGenerator generator) {
        this.board = generator.generate();
    }

    @Override
    public List<TilePosition> getTilePositions() {
        return this.board.keySet().stream().toList();
    }

    @Override
    public int getTileNumber(final TilePosition pos) {
        if (!this.board.containsKey(pos)) {
            throw new IllegalArgumentException("Position not found");
        }
        return this.board.get(pos).getNumber();
    }

    @Override
    public TerrainType getTileTerrainType(final TilePosition pos) {
        if (!this.board.containsKey(pos)) {
            throw new IllegalArgumentException("Position not found");
        }
        return this.board.get(pos).getTerrainType();
    }

    @Override
    public Optional<TilePosition> getRobberPosition() {
        return Optional.ofNullable(robberPosition);
    }

    @Override
    public void setRobberPosition(final TilePosition coordinates) {
        if (robberPosition != null && robberPosition.equals(coordinates)) {
            throw new IllegalArgumentException("Robber is already present in that coordinate.");
        }
        robberPosition = coordinates;
    }
}
