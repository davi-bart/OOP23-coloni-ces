package it.unibo.model.api;

import it.unibo.common.tile.TerrainType;

/**
 * Hexagonal tile.
 */
public interface Tile {

    /**
     * @return the number on the tile.
     */
    int getNumber();

    /**
     * @return the terrain type of the tile.
     */
    TerrainType getTerrainType();
}
