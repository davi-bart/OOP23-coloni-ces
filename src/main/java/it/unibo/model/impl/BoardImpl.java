package it.unibo.model.impl;

import java.util.List;
import java.util.Map;

import it.unibo.common.tile.TerrainType;
import it.unibo.common.tile.TilePosition;
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
        board.entrySet().stream().filter(e -> e.getValue().getTerrainType().equals(TerrainType.DESERT)).findFirst()
                .ifPresent(e -> robberPosition = e.getKey());
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
    public TilePosition getRobberPosition() {
        return robberPosition;
    }

    @Override
    public void setRobberPosition(final TilePosition coordinates) {
        if (robberPosition.equals(coordinates)) {
            throw new IllegalArgumentException("Robber is already present in that coordinate.");
        }
        robberPosition = coordinates;
    }
}
