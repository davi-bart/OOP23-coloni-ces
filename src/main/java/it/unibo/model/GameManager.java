package it.unibo.model;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import it.unibo.common.card.CardType;
import it.unibo.common.property.PropertyPosition;
import it.unibo.common.road.RoadPosition;
import it.unibo.common.tile.ResourceType;
import it.unibo.model.board.Board;
import it.unibo.model.player.Player;
import it.unibo.model.property.PropertyManager;
import it.unibo.model.resource.ResourceManager;
import it.unibo.model.road.RoadManager;
import it.unibo.model.turn.TurnManager;

/**
 * GameManager.
 */
public interface GameManager {
    /**
     * Check whether the game is over.
     * 
     * @return true if the game is over, false otherwise.
     */
    boolean isGameOver();

    /**
     * 
     * @return the player that reached winning points.
     */
    Optional<Player> getWinner();

    /**
     * Build a settlement of player {@code player} at position {@code position}.
     * 
     * @param position the position where the settlement will be built
     * @param player   the player who wants to build the settlement
     * @throws IllegalArgumentException if {@link #canBuildSettlement} fails
     */
    void buildSettlement(PropertyPosition position, Player player);

    /**
     * Build a city of player {@code player} at position {@code position}.
     * 
     * @param position the position where the city will be built
     * @param player   the player who wants to build the city
     * @throws IllegalArgumentException if {@link #canBuildCity} fails
     */
    void buildCity(PropertyPosition position, Player player);

    /**
     * Build a road of player {@code player} at position {@code position}.
     * 
     * @param position the position where the road will be built
     * @param player   the player who wants to build the road
     * @throws IllegalArgumentException if {@link #canBuildRoad} fails
     */
    void buildRoad(RoadPosition position, Player player);

    /**
     * Player {@code player} buys a development card.
     * 
     * @param player the player who wants to buy a card
     * @return the card type of the bought card
     */
    CardType buyCard(Player player);

    /**
     * @param player   the player who wants to build a settlement
     * @param position the position where he wants to build
     * @return whether player {@code player} can build a settlement at position
     *         {@code position}
     */
    boolean canBuildSettlement(PropertyPosition position, Player player);

    /**
     * @param position where the city will be built
     * @param player   the player who wants to build the city
     * @return whether player {@code player} can build a city at position
     *         {@code position}
     */
    boolean canBuildCity(PropertyPosition position, Player player);

    /**
     * @param position the position where he wants to build
     * @param player   the player who wants to build a road
     * @return whether player {@code player} can build a road at position
     *         {@code position}
     */
    boolean canBuildRoad(RoadPosition position, Player player);

    /**
     * @param player the player who wants to buy a card
     * @return whether player {@code player} can buy a card
     */
    boolean canBuyCard(Player player);

    /**
     * @return whether the current player can end the turn
     */
    boolean canEndTurn();

    /**
     * Makes each tile with number {@code number} produce its resource.
     * It automatically updates the resources of each player.
     * 
     * @param number the rolled number
     * @return a map from each player to the resources produced by the tiles near
     *         its properties
     */
    Map<Player, Map<ResourceType, Integer>> produceResources(int number);

    /**
     * @return the list of the players
     */
    List<Player> getPlayers();

    /**
     * Get the board.
     * 
     * @return the board
     */
    Board getBoard();

    /**
     * @return the property manager
     */
    PropertyManager getPropertyManager();

    /**
     * @return the road manager
     */
    RoadManager getRoadManager();

    /**
     * @return the turn manager
     */
    TurnManager getTurnManager();

    /**
     * @return the resource manager
     */
    ResourceManager getResourceManager();

}
