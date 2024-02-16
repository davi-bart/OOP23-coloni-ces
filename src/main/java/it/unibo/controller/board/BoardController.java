package it.unibo.controller.board;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import it.unibo.common.property.PropertyPosition;
import it.unibo.common.property.PropertyType;
import it.unibo.common.road.RoadPosition;
import it.unibo.common.tile.TerrainType;
import it.unibo.common.tile.TilePosition;

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
     * @param playerName the player
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
     * @param position the position of the property
     * @return the name of the owner of the property at the given position
     */
    Optional<String> getPropertyOwner(PropertyPosition position);

    /**
     * @param position the position of the road
     * @return the owner of the road at the given position
     */
    Optional<String> getRoadOwner(RoadPosition position);
}
