package it.unibo.controller.resource;

import java.util.Map;
import java.util.function.Function;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.HashMap;
import java.util.List;

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
    @SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "The resource manager needs to be updated")
    public ResourceControllerImpl(final Function<String, Player> getPlayerByName,
            final ResourceManager resourceManager) {
        this.getPlayerByName = getPlayerByName;
        this.resourceManager = resourceManager;
    }

    @Override
    public Map<ResourceType, Integer> getPlayerResources(final String owner) {
        final Map<ResourceType, Integer> out = new HashMap<>();
        List.of(ResourceType.values()).forEach(
                resource -> out.put(resource, resourceManager.getResource(getPlayerByName.apply(owner), resource)));
        return out;
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
    public int getResourcesToDiscard(final String player) {
        return resourceManager.getAmountToDiscard(getPlayerByName.apply(player));
    }

    @Override
    public boolean canDiscard(final String proposer, final Map<ResourceType, Integer> discardResources) {
        return resourceManager.isValidDiscard(getPlayerByName.apply(proposer),
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
