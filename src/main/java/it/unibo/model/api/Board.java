package it.unibo.model.api;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import it.unibo.common.api.PropertyPosition;
import it.unibo.common.api.PropertyType;
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
     * get the roads of a player.
     * 
     * @param player the player
     * @return the list of roads
     */
    Set<Road> getPlayerRoads(Player player);

    /**
     * @param player
     * @return a set of the roads built by {@code player}
     */
    Set<Property> getPlayerProperties(Player player);

    /**
     * get the property type of the property at the given position.
     * 
     * @param position the position of the property
     * @return the type of the property
     */
    PropertyType getPropertyType(PropertyPosition position);

    /**
     * @return the position of the robber
     */
    Optional<TileCoordinates> getRobberPosition();

    /**
     * Set the robber in the specified position, removing it from the previous
     * location.
     * 
     * @param coordinates coordinates of the new robber's position
     */
    void setRobberPosition(TileCoordinates coordinates);

    /**
     * Build a settlement in the specified position
     * 
     * @param position position
     * @param player   owner of the settlement
     */
    void buildSettlement(PropertyPosition position, Player player);

    /**
     * Build a city in the specified position
     * 
     * @param position position
     * @param player   owner of the settlement
     */
    void buildCity(PropertyPosition position, Player player);
}
