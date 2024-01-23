package it.unibo.model.api;

import java.util.Optional;

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
}
