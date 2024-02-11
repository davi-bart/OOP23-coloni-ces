package it.unibo.controller.api;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

import it.unibo.common.api.PropertyPosition;
import it.unibo.common.api.PropertyType;
import it.unibo.common.api.ResourceType;
import it.unibo.common.api.RoadPosition;
import it.unibo.common.api.TerrainType;
import it.unibo.common.api.TileCoordinates;

/**
 * Main controller.
 */
public interface MainController {
    /**
     * @return the list of the players' names
     */
    List<String> getPlayerNames();

    /**
     * @param playerName
     * @return resources of the player with name {@code playerName}
     */
    Map<ResourceType, Integer> getPlayerResources(String playerName);

    /**
     * @return resources of the bank
     */
    Map<ResourceType, Integer> getBankResources();

    /**
     * @param playerName
     * @return the victory points of the given player.
     */
    int getVictoryPoints(String playerName);

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
     * @param playerName the player's name
     * @return the set of the road positions
     */
    Set<RoadPosition> getPlayerRoadPositions(String playerName);

    /**
     * get all the road positions, including empty ones.
     * 
     * @return the set of the road positions
     */
    Set<RoadPosition> getAllRoadPositions();

    /**
     * get the player's property positions.
     * 
     * @param playerName the player's name
     * @return the set of the property positions and their types
     */
    Set<Pair<PropertyPosition, PropertyType>> getPlayerPropertyPositions(String playerName);

    /**
     * get all the property positions, including empty ones.
     * 
     * @return the set of the property positions
     */
    Set<PropertyPosition> getAllPropertyPositions();
}
