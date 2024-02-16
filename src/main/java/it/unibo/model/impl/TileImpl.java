package it.unibo.model.impl;

import it.unibo.common.tile.TerrainType;
import it.unibo.model.api.Tile;

/**
 * Tile implementation.
 */
public final class TileImpl implements Tile {
    private final int number;
    private final TerrainType terrainType;

    TileImpl(final TerrainType terrainType, final int number) {
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
