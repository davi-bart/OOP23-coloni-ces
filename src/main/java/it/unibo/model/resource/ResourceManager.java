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
     * 
     * @param owner
     * @return all the resources owned by the owner.
     */
    int getResourcesAmount(ResourceOwner owner);

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
     * @param resources
     * @return true if the given owner has the given resources, false otherwise
     */
    boolean hasResources(ResourceOwner owner, Map<ResourceType, Integer> resources);

    /**
     * 
     * @return the bank
     */
    ResourceOwner getBank();

    /**
     * 
     * @param owner
     * @return all the resources owned by the owner.
     */
    Map<ResourceType, Integer> getResources(ResourceOwner owner);

    /**
     * 
     * @param proposer
     * @param accepter
     * @param proposedResources
     * @param wantedResources
     * @return whether proposer can trade with accepter based on the resources
     *         provided.
     */
    boolean canTrade(ResourceOwner proposer, ResourceOwner accepter, Map<ResourceType, Integer> proposedResources,
            Map<ResourceType, Integer> wantedResources);

    /***
     * 
     * @param amount
     * @return the amount of card that the player must discard.
     */
    int getResourcesToDiscard(int amount);

    /**
     * 
     * @param proposer
     * @param amount
     * @return whether the player proposer can discard the amount.
     */
    boolean canDiscard(ResourceOwner proposer, int amount);

    /**
     * 
     * @param playerName
     * @return whether the player should discard cards.
     */
    boolean shouldDiscard(ResourceOwner playerName);
}
