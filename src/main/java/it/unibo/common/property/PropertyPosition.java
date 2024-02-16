package it.unibo.common.property;

import java.util.List;

import it.unibo.common.tile.TilePosition;

/**
 * Interface for the position of a property.
 */
public interface PropertyPosition {
    /**
     * @return the coordinates of the tile
     */
    TilePosition getTilePosition();

    /**
     * @return the direction of the property
     */
    PropertyDirection getDirection();

    /**
     * @return all the positions of the current property, each from a different tile
     */
    List<PropertyPosition> getEquivalentPositions();

    /**
     * 
     * @param position given position
     * @return true if the given position is near an existing property, false
     *         otherwise.
     */
    boolean isNear(PropertyPosition position);

}
