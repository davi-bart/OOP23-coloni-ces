package it.unibo.controller.api;

import java.util.Map;

import it.unibo.common.api.tile.ResourceType;
import it.unibo.model.api.ResourceOwner;

/**
 * Resource controller.
 */
public interface ResourceController {
    /**
     * @param owner is the owner.
     * @return a map with all the resources that the passed owner has.
     */
    Map<ResourceType, Integer> getPlayerResources(String owner);

    /**
     * @return the bank resources
     */
    Map<ResourceType, Integer> getBankResources();

    /**
     * @param owner    is the owner.
     * @param resource is the resource type.
     * @return the amount of the given resource of the given owner.
     */
    int getOwnerResourceAmount(String owner, ResourceType resource);

    /**
     * * Return if the given owner has the given resources.
     * 
     * @param owner     owner
     * @param resources
     * @return true if the given owner has the given resources, false otherwise
     */
    boolean hasResources(String owner, Map<ResourceType, Integer> resources);

    /**
     * Modify the resources of the owners into the trade.
     * 
     * @param proposer         is the owner that propose the trade
     * @param accepter         is the owner that accept the trade
     * @param proposedResouces are the resources that the proposer give to the
     *                         accepter
     * @param wantedResources  are the resources that the accepter give to the
     *                         proposer
     */
    void acceptTrade(String proposer, String accepter, Map<ResourceType, Integer> proposedResouces,
            Map<ResourceType, Integer> wantedResources);

    /**
     * 
     * @param owner
     * @return all the resources owned by the owner.
     */
    Map<ResourceType, Integer> getResources(String owner);

    /***
     * 
     * @param proposer
     * @param accepter
     * @param proposedResouces
     * @param wantedResources
     * @return
     */
    boolean canTradeWithPlayer(String proposer, String accepter,
            Map<ResourceType, Integer> proposedResouces,
            Map<ResourceType, Integer> wantedResources);

    /***
     * 
     * @param proposer
     * @param proposedResouces
     * @param wantedResources
     * @return
     */
    boolean canTradeWithBank(String proposer, Map<ResourceType, Integer> proposedResouces,
            Map<ResourceType, Integer> wantedResources);
}
