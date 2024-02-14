package it.unibo.controller.api;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

import it.unibo.common.api.property.PropertyPosition;
import it.unibo.common.api.property.PropertyType;
import it.unibo.common.api.road.RoadPosition;
import it.unibo.common.api.tile.ResourceType;
import it.unibo.common.api.tile.TerrainType;
import it.unibo.common.api.tile.TilePosition;

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
     * get the property type of the property at the given position.
     * 
     * @param position the position of the property
     * @return the type of the property
     */
    PropertyType getPropertyType(PropertyPosition position);

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

    /**
     * Build a settlement in the given position.
     * 
     * @param position where to build the settlement.
     */
    void buildSettlement(PropertyPosition position);

    /**
     * Build a city in the given position.
     * 
     * @param position where to build the city.
     */
    void buildCity(PropertyPosition position);

    /**
     * Build a road in the given position.
     * 
     * @param position where to build the road
     */
    void buildRoad(RoadPosition position);

    /**
     * @return whether the current player can build a settlement
     * @param position where to build the settlemet
     */
    boolean canBuildSettlement(PropertyPosition position);

    /**
     * @return whether the current player can build a city
     * @param position where to build the city
     */
    boolean canBuildCity(PropertyPosition position);

    /**
     * @return whether the current player can build a road
     * @param position where to build the rode
     */
    boolean canBuildRoad(RoadPosition position);

    /**
     * @return get the name of the player whose turn it currently is.
     */
    String getCurrentPlayer();

    /**
     * end the current turn and updates the current player.
     */
    void endTurn();

    /**
     * get the points of the specified player.
     * 
     * @param player the player
     * @return the points of the player
     */
    int getPlayerPoints(String player);


    /**
     * 
     * @return the dice roll.
     */
    Pair<Integer, Integer> rollDie();

    /**
     * * Return if the given owner has the given resources.
     * 
     * @param playerName player's name
     * @param resources  map with the resources
     * @return true if the given owner has the given resources, false otherwise
     */
    boolean hasResources(String playerName, Map<ResourceType, Integer> resources);

    /**
     * @param playerName
     * @return the longest road length of the player with name {@code playerName}
     */
    int getLongestRoadLength(String playerName);

    /**
     * @return whether the current player can end the turn
     */
    boolean canEndTurn();

    /**
     * 
     * @return if the player can roll.
     */
    boolean canRollDie();

    /**
     * give the resources produced by the tile with the given number.
     * 
     * @param rollSum sum of the 2 dices.
     */
    void giveResources(int rollSum);

    /**
     * 
     * @return the number of cycles.
     */
    int getCycle();
}
