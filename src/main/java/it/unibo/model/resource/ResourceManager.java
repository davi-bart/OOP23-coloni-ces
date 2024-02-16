package it.unibo.model.resource;

import java.util.Map;

import it.unibo.common.tile.ResourceType;

/**
 * A manager that monitor the amount of resources of all players, by adding or
 * removing them.
 */
public interface ResourceManager {

    /**
     * Add the given amount of the given resource to the given owner.
     * 
     * @param owner     owner of the resources
     * @param resources map from resource to amount
     */
    void addResources(ResourceOwner owner, Map<ResourceType, Integer> resources);

    /**
     * Remove the given amount of the given resource to the given owner.
     * 
     * @param owner     owner of the resources
     * @param resources map from resource to amount
     */
    void removeResources(ResourceOwner owner, Map<ResourceType, Integer> resources);

    /**
     * @param owner        owner of the resources
     * @param resourceType resource type
     * @return the amount of that resource
     **/
    int getResource(ResourceOwner owner, ResourceType resourceType);

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
    void trade(ResourceOwner proposer, ResourceOwner accepter, Map<ResourceType, Integer> proposedResources,
            Map<ResourceType, Integer> wantedResources);

    /**
     * * Return if the given owner has the given resources.
     * 
     * @param owner     owner
     * @param resources map from resource to amount
     * @return true if the given owner has the given resources, false otherwise
     */
    boolean hasResources(ResourceOwner owner, Map<ResourceType, Integer> resources);

    /**
     * @return the bank
     */
    ResourceOwner getBank();

    /**
     * @param owner owner of the resources
     * @return all the resources owned by the owner.
     */
    Map<ResourceType, Integer> getResources(ResourceOwner owner);

    /**
     * @param proposer          is the owner that proposes the trade
     * @param accepter          is the owner that accepts the trade
     * @param proposedResources are the resources proposed by the proposer
     * @param wantedResources   are the resources wanted by the proposer
     * @return whether proposer can trade with accepter based on the resources
     *         provided.
     */
    boolean canTrade(ResourceOwner proposer, ResourceOwner accepter, Map<ResourceType, Integer> proposedResources,
            Map<ResourceType, Integer> wantedResources);

    /**
     * @param player that must discard.
     * @return the amount of card that the player must discard.
     */
    int getAmountToDiscard(ResourceOwner player);

    /**
     * @param owner  is the owner of the resources
     * @param amount the amount of resources he has to discard
     * @return whether the player proposer can discard the amount.
     */
    boolean isValidDiscard(ResourceOwner owner, int amount);

    /**
     * @param player the player that has to discard
     * @return whether the player should discard cards.
     */
    boolean shouldDiscard(ResourceOwner player);
}
