package it.unibo.controller.api;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

import it.unibo.common.api.property.PropertyPosition;
import it.unibo.common.api.property.PropertyType;
import it.unibo.common.api.road.RoadPosition;
import it.unibo.common.api.tile.TerrainType;
import it.unibo.common.api.tile.TilePosition;

/**
 * Board controller.
 */
public interface BoardController {
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
     * @param playerName the player
     * @return the set of the road positions
     */
    Set<RoadPosition> getPlayerRoadPositions(String playerName);

    /**
     * get the player's property positions.
     * 
     * @param playerName the player
     * @return the set of the property positions and their types
     */
    Set<Pair<PropertyPosition, PropertyType>> getPlayerPropertyPositions(String playerName);

    /**
     * get all the road positions, including empty ones.
     * 
     * @return the set of the road positions
     */
    Set<RoadPosition> getAllRoadPositions();

    /**
     * get all the property positions, including empty ones.
     * 
     * @return the set of the property positions
     */
    Set<PropertyPosition> getAllPropertyPositions();

    /**
     * get the property type of the property at the given position.
     * 
     * @param position the position of the property
     * @return the type of the property
     */
    PropertyType getPropertyType(PropertyPosition position);

    /**
     * Build a settlement in the given position.
     * 
     * @param position   where to build the settlement.
     * @param playerName the player
     */
    void buildSettlement(PropertyPosition position, String playerName);

    /**
     * Build a city in the given position.
     * 
     * @param position   where to build the city.
     * @param playerName the player
     */
    void buildCity(PropertyPosition position, String playerName);

    /**
     * Builds the road at position {@code position}.
     * 
     * @param position   position of the road
     * @param playerName the player
     */
    void buildRoad(RoadPosition position, String playerName);

    /**
     * @param player
     * @return the longest road length of {@code player}
     */
    int getLongestRoadLength(String playerName);

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

    /**
     * @param position
     * @return true if the property in the given position is near to an other
     *         property, false otherwise.
     */
    boolean isNearToAnyProperty(PropertyPosition position);

    /**
     * @param playerName the player
     * @param position
     * @return true if the road in the given position is near to an other
     *         property of current player, false otherwise.
     */
    boolean isRoadNearToAnyOwnedProperty(String playerName, RoadPosition position);

    /**
     * @param playerName the player
     * @param position
     * @return true if the property in the given position is near to an other
     *         road of current player, false otherwise.
     */
    boolean isPropertyNearToAnyOwnerRoad(String playerName, PropertyPosition position);

    /**
     * @param playerName the player
     * @param position
     * @return true if the road in the given position is near to an other
     *         road of current player, false otherwise.
     */
    boolean isRoadNearToAnyOwnedRoad(String playerName, RoadPosition position);
}
