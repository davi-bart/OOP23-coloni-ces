package it.unibo.model.api;

import java.util.Map;

import it.unibo.common.ResourceType;

/**
 * Trader interface.
 **/
public interface Trader {
    /**
     * Method for accepting a trade.
     * 
     * @param givenResouces     are the resources that the caller recive after the
     *                          trade.
     * @param recivingResources are the resources that the caller give to the trade
     *                          caller.
     * 
     */
    void acceptTrade(Map<ResourceType, Integer> givenResouces, Map<ResourceType, Integer> recivingResources);

    /**
     * Method for checking if the trader has the resources to actually trade.
     * 
     * @param tradeResource the resources that the caller need to have before
     *                      accepting the trade.
     * @return whether the trader has enough resources
     */
    boolean canTrade(Map<ResourceType, Integer> tradeResource);
}
