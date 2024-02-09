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
     * @return the equivalent version (different tilecoordinates) of a road position
     */
    RoadPosition equivalent();

    /**
     * @return whether the road at the current position is nearby a road at position
     *         {@code position}
     */
    boolean isNearby(RoadPosition other);
}
