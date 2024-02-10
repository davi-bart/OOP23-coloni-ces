package it.unibo.model.api;

import it.unibo.common.api.TerrainType;

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

    /**
     * Adds the robber to the tile.
     */
    void addRobber();

    /**
     * Removes the robber (if present) from the tile.
     */
    void removeRobber();

    /**
     * @return whether the robber is on the tile.
     */
    boolean isRobberPresent();
}
