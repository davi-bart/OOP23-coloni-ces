package it.unibo.controller.api;

import java.util.List;

import it.unibo.common.TerrainType;
import it.unibo.common.TileCoordinates;

/**
 * Board controller.
 */
public interface BoardController {
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
}
