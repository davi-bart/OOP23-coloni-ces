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
     * @param player the player
     * @return the set of the property positions and their types
     */
    Set<Pair<PropertyPosition, PropertyType>> getPlayerPropertyPositions(Player player);

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

}
