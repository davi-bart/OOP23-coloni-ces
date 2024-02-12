package it.unibo.controller.api;

import java.util.Map;

import it.unibo.common.api.ResourceType;
import it.unibo.model.api.ResourceOwner;

/**
 * Resource controller.
 */
public interface ResourceController {
	/**
	 * @param owner is the owner.
	 * @return a map with all the resources that the passed owner has.
	 */
	Map<ResourceType, Integer> getOwnerResources(ResourceOwner owner);

	/**
	 * @return the bank resources
	 */
	Map<ResourceType, Integer> getBankResources();

	/**
	 * @param owner    is the owner.
	 * @param resource is the resource type.
	 * @return the amount of the given resource of the given owner.
	 */
	int getOwnerResourceAmount(ResourceOwner owner, ResourceType resource);

	/**
	 * * Return if the given owner has the given resources.
	 * 
	 * @param owner     owner
	 * @param resources
	 * @return true if the given owner has the given resources, false otherwise
	 */
	boolean hasResources(ResourceOwner owner, Map<ResourceType, Integer> resources);

	boolean canBuildSettlemet(ResourceOwner player);

	boolean canBuildCity(ResourceOwner player);
	
	boolean canBuildRoad(ResourceOwner player);

	void removeResources(final ResourceOwner owner, Map<ResourceType, Integer> resources);

}
