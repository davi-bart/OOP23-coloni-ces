package it.unibo.model.api;

import java.util.List;
import java.util.Set;

import it.unibo.common.api.PropertyPosition;
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
     * @return the road manager
     */
    RoadManager getRoadManager();

    /**
     * add a property to the board.
     * 
     * @param position the position of the property
     * @param owner    the owner of the property
     */
    void addProperty(PropertyPosition position, Player owner);

    /**
     * get the roads of a player.
     * 
     * @param player the player
     * @return the list of roads
     */
    Set<Road> getPlayerRoads(Player player);
}
