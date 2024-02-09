package it.unibo.controller.impl;

import java.util.Map;
import java.util.HashMap;

import java.util.List;
import java.util.ArrayList;

import it.unibo.common.api.ResourceType;
import it.unibo.controller.api.ResourceController;
import it.unibo.model.api.ResourceManager;
import it.unibo.model.api.ResourceOwner;
import it.unibo.model.impl.ResourceManagerImpl;

/**
 * Resource controller implementation.
 */
public final class ResourceControllerImpl implements ResourceController {

	final List<ResourceOwner> owners = new ArrayList<>();
	final ResourceManager resourceManager = new ResourceManagerImpl(owners);

	@Override
	public Map<ResourceType, Integer> getOwnerResources(final ResourceOwner owner) {
		final Map<ResourceType, Integer> out = new HashMap<>();
		for (final ResourceType resource : ResourceType.values()) {
			out.put(resource, resourceManager.getResource(owner, resource));
		}
		return out;
	}

	@Override
	public int getOwnerResourceAmount(final ResourceOwner owner, final ResourceType resource) {
		return resourceManager.getResource(owner, resource);
	}

	@Override
	public boolean canTrade(final ResourceOwner owner, final Map<ResourceType, Integer> recivingResources) {
		return resourceManager.canTrade(owner, recivingResources);
	}
}
