package it.unibo.model.board;

import it.unibo.common.tile.TerrainType;

/**
 * Tile implementation.
 */
public final class TileImpl implements Tile {
    private final int number;
    private final TerrainType terrainType;

    /**
     * Constructor of the tile.
     * 
     * @param terrainType the terrain type
     * @param number      the number on the tile
     */
    public TileImpl(final TerrainType terrainType, final int number) {
        this.terrainType = terrainType;
        this.number = number;
    }

    @Override
    public int getNumber() {
        return this.number;
    }

    @Override
    public TerrainType getTerrainType() {
        return this.terrainType;
    }
}
