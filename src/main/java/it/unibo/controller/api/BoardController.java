package it.unibo.controller.api;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

import it.unibo.common.api.property.PropertyPosition;
import it.unibo.common.api.property.PropertyType;
import it.unibo.common.api.road.RoadPosition;
import it.unibo.common.api.tile.TerrainType;
import it.unibo.common.api.tile.TilePosition;
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
     * @param player the player
     * @return the set of the road positions
     */
    Set<RoadPosition> getPlayerRoadPositions(Player player);

    /**
     * get the player's property positions.
     * 
     * @param player the player
     * @return the set of the property positions and their types
     */
    Set<Pair<PropertyPosition, PropertyType>> getPlayerPropertyPositions(Player player);

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
     * @param position where to build the settlement.
     * @param player   who own the settlement.
     */
    void buildSettlement(PropertyPosition position, Player player);

    /**
     * Build a city in the given position.
     * 
     * @param position where to build the city.
     * @param player   who own the city.
     */
    void buildCity(PropertyPosition position, Player player);

    /**
     * Builds the road at position {@code position}.
     * 
     * @param position position of the road
     * @param player   owner of the road
     */
    void buildRoad(RoadPosition position, Player player);

    /**
     * @param player
     * @return the longest road length of {@code player}
     */
    int getLongestRoadLength(Player player);

    /**
     * @return the position of the robber
     */
    TilePosition getRobberPosition();

    /**
     * @param position
     * @return true if the property in the given position is near to an other
     *         property, false otherwise.
     */
    boolean isNearToAnyProperty(PropertyPosition position);

    /**
     * @param player
     * @param position
     * @return true if the road in the given position is near to an other
     *         property of current player, false otherwise.
     */
    boolean isRoadNearToAnyOwnedProperty(Player player, RoadPosition position);

    /**
     * @param player
     * @param position
     * @return true if the property in the given position is near to an other
     *         road of current player, false otherwise.
     */
    boolean isPropertyNearToAnyOwnerRoad(Player player, PropertyPosition position);

    /**
     * @param player
     * @param position
     * @return true if the road in the given position is near to an other
     *         road of current player, false otherwise.
     */
    boolean isRoadNearToAnyOwnedRoad(Player player, RoadPosition position);
}
