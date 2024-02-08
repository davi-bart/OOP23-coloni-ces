package it.unibo.common.api;

public interface PropertyPosition {
    /**
     * @return the coordinates of the tile
     */
    TileCoordinates getCoordinates();

    /**
     * @return the direction of the property
     */
    PropertyDirection getDirection();
    
}
