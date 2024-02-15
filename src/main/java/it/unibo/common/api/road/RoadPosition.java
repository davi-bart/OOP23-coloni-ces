package it.unibo.common.api.road;

import java.util.List;

import it.unibo.common.api.property.PropertyPosition;
import it.unibo.common.api.tile.TilePosition;

/**
 * Road position.
 */
public interface RoadPosition {
    /**
     * @return the coordinates of the tile
     */
    TilePosition getCoordinates();

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

    /**
     * @param position
     * @return true if this road is near the property in the given position.
     */
    boolean isNearToProperty(PropertyPosition position);

    /**
     * @return the list of the positions nearby the current road
     */
    List<PropertyPosition> getNearbyProperties();
}
