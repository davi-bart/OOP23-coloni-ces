package it.unibo.model.board;

import java.util.List;

import it.unibo.common.tile.TerrainType;
import it.unibo.common.tile.TilePosition;

/**
 * Game board.
 */
public interface Board {
    /**
     * get the tile positions.
     * 
     * @return the list of the tile positions
     */
    List<TilePosition> getTilePositions();

    /**
     * get the tile number.
     * 
     * @param pos the position of the tile
     * @return the number of the tile
     */
    int getTileNumber(TilePosition pos);

    /**
     * get the tile terrain type.
     * 
     * @param pos the position of the tile
     * @return the terrain type of the tile
     */
    TerrainType getTileTerrainType(TilePosition pos);

    /**
     * @return the position of the robber
     */
    TilePosition getRobberPosition();

    /**
     * Set the robber in the specified position, removing it from the previous
     * location.
     * 
     * @param coordinates coordinates of the new robber's position
     */
    void setRobberPosition(TilePosition coordinates);
}
