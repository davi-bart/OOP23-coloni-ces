package it.unibo.controller.api;

import java.util.List;
import java.util.Map;

import it.unibo.common.api.property.PropertyPosition;
import it.unibo.common.api.road.RoadPosition;
import it.unibo.common.api.tile.ResourceType;

/**
 * Main controller.
 */
public interface MainController {
    /**
     * @return the board controller
     */
    BoardController getBoardController();

    /**
     * @return the resource controller
     */
    ResourceController getResourceController();

    /**
     * @return the turn controller
     */
    TurnController getTurnController();

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
     * @param playerName
     * @return the victory points of the given player.
     */
    int getVictoryPoints(String playerName);

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
     * get the points of the specified player.
     * 
     * @param player the player
     * @return the points of the player
     */
    int getPlayerPoints(String player);

    /**
     * * Return if the given owner has the given resources.
     * 
     * @param playerName player's name
     * @param resources  map with the resources
     * @return true if the given owner has the given resources, false otherwise
     */
    boolean hasResources(String playerName, Map<ResourceType, Integer> resources);

    /**
     * @return whether the current player can end the turn
     */
    boolean canEndTurn();

    /**
     * @return whether the current player can start a trade.
     */
    boolean canStartTrade();

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
     * Modify the resources of the owners into the trade.
     * 
     * @param proposer          is the owner that propose the trade
     * @param accepter          is the owner that accept the trade
     * @param proposedResources are the resources that the proposer give to the
     *                          accepter
     * @param wantedResources   are the resources that the accepter give to the
     *                          proposer
     */
    void acceptTrade(String proposer, String accepter, Map<ResourceType, Integer> proposedResources,
            Map<ResourceType, Integer> wantedResources);

    /**
     * Update the view.
     */
    void updateView();
}
