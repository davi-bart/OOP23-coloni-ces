package it.unibo.model.api;

import java.util.List;

import it.unibo.common.card.CardType;
import it.unibo.common.property.PropertyPosition;
import it.unibo.common.road.RoadPosition;

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
     * Build a settlement of player {@code player} at position {@code position}.
     * 
     * @param position where to build the settlement.
     * @param player   the player who wants to build the settlement.
     */
    void buildSettlement(PropertyPosition position, Player player);

    /**
     * Build a city of player {@code player} at position {@code position}.
     * 
     * @param player
     * @param position
     */
    void buildCity(PropertyPosition position, Player player);

    /**
     * Build a road of player {@code player} at position {@code position}.
     * 
     * @param position where to build the road
     * @param player   the player who wants to build the road
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
     * @param player
     * @param position
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
     * @param player
     * @param position
     * @return whether player {@code player} can build a road at position
     *         {@code position}
     */
    boolean canBuildRoad(RoadPosition position, Player player);

    /**
     * @param player
     * @return whether player {@code player} can buy a card
     */
    boolean canBuyCard(Player player);

    /**
     * @return whether the current player can end the turn
     */
    boolean canEndTurn();

    /**
     * Make each tile with number {@code number} produce its resource.
     * It automatically updates the resources of each player.
     * 
     * @param number
     */
    void produceResources(int number);

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
