package it.unibo.model.api;

import java.util.List;

import it.unibo.common.api.PropertyPosition;
import it.unibo.common.api.RoadPosition;
import it.unibo.common.api.TerrainType;
import it.unibo.common.api.TileCoordinates;

/**
 * Game board.
 */
public interface Board {
    /**
     * get the tile positions.
     * 
     * @return the list of the tile positions
     */
    List<TileCoordinates> getTilePositions();

    /**
     * get the tile number.
     * 
     * @param pos the position of the tile
     * @return the number of the tile
     */
    int getTileNumber(TileCoordinates pos);

    /**
     * get the tile terrain type.
     * 
     * @param pos the position of the tile
     * @return the terrain type of the tile
     */
    TerrainType getTileTerrainType(TileCoordinates pos);

    /**
     * add a road to the board.
     * 
     * @param position the position of the road
     * @param owner    the owner of the road
     */
    void addRoad(RoadPosition position, Player owner);

    /**
     * add a property to the board.
     * 
     * @param position the position of the property
     * @param owner    the owner of the property
     */
    void addProperty(PropertyPosition position, Player owner);
}
