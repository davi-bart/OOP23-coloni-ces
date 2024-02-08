package it.unibo.model.api;

import java.util.Optional;

import it.unibo.common.api.PropertyDirection;
import it.unibo.common.api.RoadDirection;
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
     * @param direction
     * @return the road in the given direction.
     */
    Optional<Road> getRoad(RoadDirection direction);

    /**
     * @param direction
     * @return the property in the given direction.
     */
    Optional<Property> getProperty(PropertyDirection direction);

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
