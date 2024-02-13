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
     * @param other
     * @return whether the road at the current position is nearby a road at
     *         {@code position}
     */
    boolean isNearby(RoadPosition other);

    /**
     * 
     * @param position
     * @return true if this road is near the property in the given position.
     */
    boolean isNearToProperty(PropertyPosition position);
}
