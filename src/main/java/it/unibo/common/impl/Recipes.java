package it.unibo.common.impl;

import java.util.Map;

import it.unibo.common.api.ResourceType;

public class Recipes {
	final static Map<ResourceType, Integer> settlementResources = Map.of(ResourceType.GRAIN, 1, ResourceType.BRICK, 1,
			ResourceType.WOOL, 1, ResourceType.LUMBER, 1);
	final static Map<ResourceType, Integer> cityResources = Map.of(ResourceType.GRAIN, 2, ResourceType.ORE, 3);
	final static Map<ResourceType, Integer> roadResources = Map.of(ResourceType.LUMBER, 1, ResourceType.BRICK, 1);

	static public Map<ResourceType, Integer> getSettlementRecipes() {
		return Map.copyOf(settlementResources);
	}

	static public Map<ResourceType, Integer> getCityRecipes() {
		return Map.copyOf(cityResources);
	}

	static public Map<ResourceType, Integer> getRoadRecipes() {
		return Map.copyOf(roadResources);
	}

}
