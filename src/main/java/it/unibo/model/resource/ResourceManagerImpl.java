package it.unibo.model.resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import it.unibo.common.tile.ResourceType;
import it.unibo.model.player.Player;

/**
 * An implementation of ResourceManager.
 */
public final class ResourceManagerImpl implements ResourceManager {

    private final Map<ResourceOwner, Map<ResourceType, Integer>> allEntityResources = new HashMap<>();
    private final ResourceOwner bank;
    static final int DISCARD_THRESHOLD = 7;
    static final int DEFAULT_BANK_RESOURCES = 19;

    /**
     * Create the ResourceManager from the list of the resource owner(such as
     * players and bank).
     * 
     * @param players the list of players
     */
    public ResourceManagerImpl(final List<Player> players) {
        players.forEach(player -> allEntityResources.put(player, new HashMap<>()));
        bank = new Bank();
        allEntityResources.put(bank, Stream.of(ResourceType.values())
                .collect(Collectors.toMap(Function.identity(), v -> DEFAULT_BANK_RESOURCES)));
    }

    private boolean checkResourcesPositive(final Map<ResourceType, Integer> resources) {
        return resources.values().stream().allMatch(amount -> amount >= 0);
    }

    @Override
    public void addResources(final ResourceOwner owner, final Map<ResourceType, Integer> resources) {
        if (!checkResourcesPositive(resources)) {
            throw new IllegalArgumentException("amount must be positive");
        }
        Stream.of(ResourceType.values()).forEach(resource -> {
            int current = allEntityResources.get(owner).getOrDefault(resource, 0);
            current += resources.getOrDefault(resource, 0);
            allEntityResources.get(owner).put(resource, current);
        });
    }

    @Override
    public void removeResources(final ResourceOwner owner, final Map<ResourceType, Integer> resources) {
        if (!checkResourcesPositive(resources)) {
            throw new IllegalArgumentException("amount must be positive");
        }
        if (!hasResources(owner, resources)) {
            throw new IllegalArgumentException("amount must be lower than the total resource");
        }
        resources.forEach((resource, amount) -> allEntityResources.get(owner).compute(resource, (k, v) -> v - amount));
    }

    @Override
    public int getResource(final ResourceOwner owner, final ResourceType resource) {
        return allEntityResources.get(owner).getOrDefault(resource, 0);
    }

    @Override
    public void trade(final ResourceOwner proposer, final ResourceOwner accepter,
            final Map<ResourceType, Integer> proposedResources,
            final Map<ResourceType, Integer> wantedResources) {

        addResources(proposer, wantedResources);
        addResources(accepter, proposedResources);
        removeResources(proposer, proposedResources);
        removeResources(accepter, wantedResources);
    }

    @Override
    public boolean hasResources(final ResourceOwner owner, final Map<ResourceType, Integer> resources) {
        return resources.entrySet().stream()
                .allMatch(resource -> resource.getValue() <= getResource(owner, resource.getKey()));
    }

    @Override
    public ResourceOwner getBank() {
        return this.bank;
    }

    @Override
    public Map<ResourceType, Integer> getResources(final ResourceOwner owner) {
        return allEntityResources.get(owner);
    }

    @Override
    public boolean canTrade(final ResourceOwner proposer, final ResourceOwner accepter,
            final Map<ResourceType, Integer> proposedResources,
            final Map<ResourceType, Integer> wantedResources) {
        if (proposedResources.values().stream().allMatch(amount -> amount == 0)
                && wantedResources.values().stream().allMatch(amount -> amount == 0)) {
            return false;
        }
        if (!(hasResources(proposer, proposedResources) && hasResources(accepter, wantedResources))) {
            return false;
        }
        if (accepter.equals(bank)) {
            return proposedResources.values().stream().anyMatch(amount -> amount == 4)
                    && proposedResources.values().stream().mapToInt(i -> i).sum() == 4
                    && wantedResources.values().stream().mapToInt(i -> i).sum() == 1;
        }
        return !(proposedResources.values().stream().allMatch(amount -> amount == 0)
                || wantedResources.values().stream().allMatch(amount -> amount == 0));
    }

    @Override
    public int getAmountToDiscard(final ResourceOwner player) {
        int amount = getResourcesAmount(player);
        if (amount <= DISCARD_THRESHOLD) {
            return 0;
        }
        return Math.max(amount / 2, amount - DISCARD_THRESHOLD);
    }

    @Override
    public boolean isValidDiscard(final ResourceOwner player, final int amount) {
        return amount == getAmountToDiscard(player);
    }

    private int getResourcesAmount(final ResourceOwner owner) {
        return getResources(owner).values().stream().mapToInt(Integer::intValue).sum();
    }

    @Override
    public boolean shouldDiscard(final ResourceOwner player) {
        return getResourcesAmount(player) > DISCARD_THRESHOLD;
    }

    /**
     * Bank. 
     * 
     */
    private final class Bank implements ResourceOwner {

    }
}
