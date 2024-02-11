package it.unibo.controller.api;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

import it.unibo.common.api.PropertyPosition;
import it.unibo.common.api.PropertyType;
import it.unibo.common.api.RoadPosition;
import it.unibo.common.api.TerrainType;
import it.unibo.common.api.TileCoordinates;
import it.unibo.model.api.Player;

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

    /**
     * get the player's road positions.
     * 
     * @param player the player
     * @return the set of the road positions
     */
    Set<RoadPosition> getPlayerRoadPositions(Player player);

    /**
     * get the player's property positions.
     * 
     * @param playerName the player
     * @return the set of the property positions and their types
     */
    Set<Pair<PropertyPosition, PropertyType>> getPlayerPropertyPositions(Player player);
}
