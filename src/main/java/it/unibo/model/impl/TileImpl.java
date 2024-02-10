package it.unibo.model.impl;

import it.unibo.common.api.TerrainType;
import it.unibo.model.api.Tile;

/**
 * Tile implementation.
 */
public final class TileImpl implements Tile {
    private final int number;
    private final TerrainType terrainType;
    private boolean robberIsPresent;

    TileImpl(final TerrainType terrainType, final int number) {
        this.terrainType = terrainType;
        this.number = number;
        this.robberIsPresent = false;
    }

    @Override
    public int getNumber() {
        return this.number;
    }

    @Override
    public TerrainType getTerrainType() {
        return this.terrainType;
    }

    @Override
    public void addRobber() {
        if (isRobberPresent()) {
            throw new IllegalStateException("Robber was already present on the tile.");
        }
        robberIsPresent = true;
    }

    @Override
    public void removeRobber() {
        if (!isRobberPresent()) {
            throw new IllegalStateException("Robber was not present on the tile.");
        }
        robberIsPresent = false;
    }

    @Override
    public boolean isRobberPresent() {
        return robberIsPresent;
    }
}
