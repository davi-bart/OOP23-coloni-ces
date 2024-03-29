package it.unibo.controller.resource;

import java.util.Map;

import it.unibo.common.tile.ResourceType;

/**
 * Resource controller.
 */
public interface ResourceController {
    /**
     * @param player is the owner.
     * @return a map with all the resources that the passed owner has.
     */
    Map<ResourceType, Integer> getPlayerResources(String player);

    /**
     * @return the bank resources
     */
    Map<ResourceType, Integer> getBankResources();

    /**
     * Modify the resources of the players into the trade.
     * 
     * @param proposer          is the player that propose the trade
     * @param accepter          is the player that accept the trade
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
     * @param playerName name of the player.
     * @return the amount of card that the player must discard.
     */
    int getResourcesToDiscard(String playerName);

    /**
     * @param proposer          is the player that propose the trade
     * @param accepter          is the player that accept the trade
     * @param proposedResources are the resources that the proposer give to the
     *                          accepter
     * @param wantedResources   are the resources that the accepter give to the
     *                          proposer
     * @return whether the two player involved in the trade have the resources.
     */
    boolean canTradeWithPlayer(String proposer, String accepter,
            Map<ResourceType, Integer> proposedResources,
            Map<ResourceType, Integer> wantedResources);

    /***
     * 
     * @param proposer          is the player that propose the trade
     * @param proposedResources are the resources that the proposer want to give to
     *                          the
     *                          bank
     * @param wantedResources   are the resources that the bank want to give to the
     *                          proposer
     * @return whether the proposer and the bank have the resources.
     */
    boolean canTradeWithBank(String proposer, Map<ResourceType, Integer> proposedResources,
            Map<ResourceType, Integer> wantedResources);

    /**
     * 
     * @param player           player that have to discard
     * @param discardResources map of the resources
     * @return whether the player proposer can discard the amount.
     */
    boolean canDiscard(String player, Map<ResourceType, Integer> discardResources);

    /**
     * 
     * @param player the player that has to discard
     * @return whether the player should discard cards.
     */
    boolean shouldDiscard(String player);
}
