package it.unibo.common.impl;

import java.util.Map;

import it.unibo.common.api.tile.ResourceType;

/**
 * Utility class used for getting the amount of resources needed to perform
 * certain actions.
 */
public final class Recipes {

    private Recipes() {
    }

    /**
     * @return a map containing the amount of each resources necessary to build a
     *         settlement
     */
    public static Map<ResourceType, Integer> getSettlementResources() {
        return Map.of(ResourceType.GRAIN, 1, ResourceType.BRICK, 1,
                ResourceType.WOOL, 1, ResourceType.LUMBER, 1);
    }

    /**
     * @return a map containing the amount of each resources necessary to build a
     *         city
     */
    public static Map<ResourceType, Integer> getCityResources() {
        return Map.of(ResourceType.GRAIN, 2, ResourceType.ORE, 3);
    }

    /**
     * @return a map containing the amount of each resources necessary to build a
     *         road
     */
    public static Map<ResourceType, Integer> getRoadResources() {
        return Map.of(ResourceType.LUMBER, 1, ResourceType.BRICK, 1);
    }

}
