package it.unibo.controller.resource;

import java.util.Map;
import java.util.function.Function;
import java.util.HashMap;

import it.unibo.common.tile.ResourceType;
import it.unibo.model.player.Player;
import it.unibo.model.resource.ResourceManager;

/**
 * Resource controller implementation.
 */
public final class ResourceControllerImpl implements ResourceController {

    private final ResourceManager resourceManager;
    private final Function<String, Player> getPlayerByName;

    /**
     * Constructor of resource controller.
     * 
     * @param getPlayerByName the function to get the resource owner by name
     * @param resourceManager the resource manager
     */
    public ResourceControllerImpl(final Function<String, Player> getPlayerByName,
            final ResourceManager resourceManager) {
        this.getPlayerByName = getPlayerByName;
        this.resourceManager = resourceManager;
    }

    @Override
    public Map<ResourceType, Integer> getPlayerResources(final String owner) {
        final Map<ResourceType, Integer> out = new HashMap<>();
        for (final ResourceType resource : ResourceType.values()) {
            out.put(resource, resourceManager.getResource(getPlayerByName.apply(owner), resource));
        }
        return out;
    }

    @Override
    public boolean hasResources(final String owner, final Map<ResourceType, Integer> resource) {
        return resourceManager.hasResources(getPlayerByName.apply(owner), resource);
    }

    @Override
    public Map<ResourceType, Integer> getBankResources() {
        return resourceManager.getResources(resourceManager.getBank());
    }

    @Override
    public void tradeWithPlayer(final String proposer, final String accepter,
            final Map<ResourceType, Integer> proposedResources,
            final Map<ResourceType, Integer> wantedResources) {
        resourceManager.trade(getPlayerByName.apply(proposer), getPlayerByName.apply(accepter),
                proposedResources,
                wantedResources);
    }

    @Override
    public int getResourcesAmount(final String owner) {
        return resourceManager.getResourcesAmount(getPlayerByName.apply(owner));
    }

    @Override
    public boolean canTradeWithPlayer(final String proposer, final String accepter,
            final Map<ResourceType, Integer> proposedResources, final Map<ResourceType, Integer> wantedResources) {
        return resourceManager.canTrade(getPlayerByName.apply(proposer), getPlayerByName.apply(accepter),
                proposedResources, wantedResources);
    }

    @Override
    public boolean canTradeWithBank(final String proposer, final Map<ResourceType, Integer> proposedResources,
            final Map<ResourceType, Integer> wantedResources) {
        return resourceManager.canTrade(getPlayerByName.apply(proposer), resourceManager.getBank(),
                proposedResources,
                wantedResources);
    }

    @Override
    public int getResourcesToDiscard(final int amount) {
        return resourceManager.getResourcesToDiscard(amount);
    }

    @Override
    public boolean canDiscard(final String proposer, final Map<ResourceType, Integer> discardResources) {
        return resourceManager.canDiscard(getPlayerByName.apply(proposer),
                discardResources.values().stream().mapToInt(Integer::intValue).sum());
    }

    @Override
    public boolean shouldDiscard(final String playerName) {
        return resourceManager.shouldDiscard(getPlayerByName.apply(playerName));
    }

    @Override
    public void tradeWithBank(final String proposer, final Map<ResourceType, Integer> proposedResources,
            final Map<ResourceType, Integer> wantedResources) {
        resourceManager.trade(getPlayerByName.apply(proposer), resourceManager.getBank(), proposedResources,
                wantedResources);
    }

}
