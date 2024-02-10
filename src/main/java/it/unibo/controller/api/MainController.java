package it.unibo.controller.api;

import java.util.List;
import java.util.Map;

import it.unibo.common.api.ResourceType;
import it.unibo.common.api.TerrainType;
import it.unibo.common.api.TileCoordinates;

/**
 * Main controller.
 */
public interface MainController {

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
}
