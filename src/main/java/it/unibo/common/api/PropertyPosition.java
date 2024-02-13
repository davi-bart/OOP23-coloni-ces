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

    /**
     * 
     * @param position given position
     * @return true if the given position is near an existing property, false
     *         otherwise.
     */
    boolean isNear(PropertyPosition position);

}
