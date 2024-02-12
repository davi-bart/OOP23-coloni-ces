package it.unibo.common.api;

/**
 * Interface for the position of a property.
 */
public interface PropertyPosition {
    /**
     * @return the coordinates of the tile
     */
    TileCoordinates getCoordinates();

    /**
     * @return the direction of the property
     */
    PropertyDirection getDirection();

    boolean isNear(PropertyPosition position);

}
