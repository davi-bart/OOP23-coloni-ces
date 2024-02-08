package it.unibo.common.api;

/**
 * Road position.
 */
public interface RoadPosition {
    /**
     * @return the coordinates of the tile
     */
    TileCoordinates getCoordinates();

    /**
     * @return the direction of the road
     */
    RoadDirection getDirection();
}
