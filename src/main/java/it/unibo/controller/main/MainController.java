package it.unibo.controller.main;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import it.unibo.common.property.PropertyPosition;
import it.unibo.common.road.RoadPosition;
import it.unibo.common.tile.ResourceType;
import it.unibo.common.tile.TilePosition;
import it.unibo.controller.board.BoardController;
import it.unibo.controller.resource.ResourceController;

/**
 * Main controller.
 */
public interface MainController {

    /**
     * Starts the application view.
     */
    void start();

    /**
     * Build a city in the given position.
     * 
     * @param position where to build the city.
     */
    void buildCity(PropertyPosition position);

    /**
     * Build a settlement in the given position.
     * 
     * @param position where to build the settlement.
     */
    void buildSettlement(PropertyPosition position);

    /**
     * Build a road in the given position.
     * 
     * @param position where to build the road
     */
    void buildRoad(RoadPosition position);

    /**
     * The current player buys a development card.
     */
    void buyCard();

    /**
     * @return whether the current player can build a city
     * @param position where to build the city
     */
    boolean canBuildCity(PropertyPosition position);

    /**
     * @return whether the current player can build a settlement
     * @param position where to build the settlement
     */
    boolean canBuildSettlement(PropertyPosition position);

    /**
     * @return whether the current player can build a road
     * @param position where to build the rode
     */
    boolean canBuildRoad(RoadPosition position);

    /**
     * @return whether the current player can buy a development card.
     */
    boolean canBuyCard();

    /**
     * @return whether the current player can end the turn
     */
    boolean canEndTurn();

    /**
     * Set the robber in the specified position, removing it from the previous
     * location.
     * 
     * @param coordinates coordinates of the new robber's position
     */
    void setRobberPosition(TilePosition coordinates);

    /**
     * 
     * @return true if the player must place the robber.
     */
    boolean mustPlaceRobber();

    /**
     * 
     * @return the dice roll.
     */
    Pair<Integer, Integer> rollDie();

    /**
     * 
     * @return if the player can roll.
     */
    boolean canRollDie();

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
    void tradeWithPlayer(String proposer, String accepter, Map<ResourceType, Integer> proposedResources,
            Map<ResourceType, Integer> wantedResources);

    /**
     * Modify the resources of the owners into the trade (player and bank).
     * 
     * @param proposer          is the player that propose the trade
     * @param proposedResources are the resources that the proposer give to the
     *                          bank
     * @param wantedResources   are the resources that the bank give to the
     *                          proposer
     */
    void tradeWithBank(String proposer, Map<ResourceType, Integer> proposedResources,
            Map<ResourceType, Integer> wantedResources);

    /**
     * @return whether the current player can start a trade.
     */
    boolean canStartTrade();

    /**
     * end the current turn and updates the current player.
     */
    void endTurn();

    /**
     * @return the board controller
     */
    BoardController getBoardController();

    /**
     * @return the resource controller
     */
    ResourceController getResourceController();

    /**
     * @return the list of the players' names
     */
    List<String> getPlayerNames();

    /**
     * @return get the name of the player currently playing.
     */
    String getCurrentPlayerName();

    /**
     * get the points of the specified player.
     * 
     * @param player the player
     * @return the points of the player
     */
    int getPlayerPoints(String player);

    /**
     * 
     * @return the name of the winner.
     */
    Optional<String> getWinner();
}
