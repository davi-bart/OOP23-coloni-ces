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

    /**
     * @param other
     * @return whether the road at the current position is nearby a road at
     *         {@code position}
     */
    boolean isNearby(RoadPosition other);
}
