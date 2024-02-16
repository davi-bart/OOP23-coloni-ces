package it.unibo.model.resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.unibo.common.tile.ResourceType;
import it.unibo.model.player.Player;

/**
 * An implementation of ResourceManager.
 */
public final class ResourceManagerImpl implements ResourceManager {

    private final Map<ResourceOwner, Map<ResourceType, Integer>> allEntityResources = new HashMap<>();
    private final ResourceOwner bank;
    static final int DISCARD_THRESHOLD = 7;

    /**
     * Create the ResourceManager from the list of the resource owner(such as
     * players and bank).
     * 
     * @param players             the list of players
     * @param bankResourcesAmount the amount of resources in the bank
     */
    public ResourceManagerImpl(final List<Player> players, final int bankResourcesAmount) {
        players.forEach(ro -> allEntityResources.put(ro, ro.getDefaultResources()));
        bank = new BankImpl(bankResourcesAmount);
        allEntityResources.put(bank, bank.getDefaultResources());
    }

    @Override
    public void addResources(final ResourceOwner owner, final ResourceType resource, final int amount) {
        if (amount >= 0) {
            allEntityResources.get(owner).compute(resource, (k, v) -> v + amount);
        } else {
            throw new IllegalArgumentException("amount must be positive");
        }
    }

    @Override
    public void removeResources(final ResourceOwner owner, final ResourceType resource, final int amount) {
        if (allEntityResources.get(owner).get(resource) >= amount) {
            allEntityResources.get(owner).compute(resource, (k, v) -> v - amount);
        } else {
            throw new IllegalArgumentException("amount must be lower than the total resource");
        }
    }

    @Override
    public int getResource(final ResourceOwner owner, final ResourceType resource) {
        return allEntityResources.get(owner).get(resource);
    }

    @Override
    public void trade(final ResourceOwner proposer, final ResourceOwner accepter,
            final Map<ResourceType, Integer> givingResouces,
            final Map<ResourceType, Integer> recivingResources) {
        givingResouces.entrySet().forEach(resource -> {
            removeResources(proposer, resource.getKey(), resource.getValue());
            addResources(accepter, resource.getKey(), resource.getValue());
        });
        recivingResources.entrySet().forEach(resource -> {
            addResources(proposer, resource.getKey(), resource.getValue());
            removeResources(accepter, resource.getKey(), resource.getValue());
        });
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
            final Map<ResourceType, Integer> proposedResouces,
            final Map<ResourceType, Integer> wantedResources) {
        /**
         * 
         */
        initializeResourceMap(proposedResouces);
        initializeResourceMap(wantedResources);
        if (proposedResouces.values().stream().allMatch(amount -> amount == 0)
                && wantedResources.values().stream().allMatch(amount -> amount == 0)) {
            return false;
        }
        if (!(hasResources(proposer, proposedResouces) && hasResources(accepter, wantedResources))) {
            return false;
        }
        if (accepter.equals(bank)) {
            return proposedResouces.values().stream().anyMatch(amount -> amount == 4)
                    && proposedResouces.values().stream().mapToInt(i -> i).sum() == 4
                    && wantedResources.values().stream().mapToInt(i -> i).sum() == 1;
        }
        return !(proposedResouces.values().stream().allMatch(amount -> amount == 0)
                || wantedResources.values().stream().allMatch(amount -> amount == 0));
    }

    private void initializeResourceMap(final Map<ResourceType, Integer> map) {
        List.of(ResourceType.values()).forEach(resource -> map.putIfAbsent(resource, 0));
    }

    @Override
    public int getResourcesToDiscard(final int amount) {
        if (amount <= DISCARD_THRESHOLD) {
            return 0;
        }
        return amount / 2;
    }

    @Override
    public boolean canDiscard(final ResourceOwner proposer, final int amount) {
        return amount == getResourcesToDiscard(getResourcesAmount(proposer));
    }

    @Override
    public int getResourcesAmount(final ResourceOwner owner) {
        return getResources(owner).values().stream().mapToInt(Integer::intValue).sum();
    }

    @Override
    public boolean shouldDiscard(final ResourceOwner player) {
        return getResourcesAmount(player) > DISCARD_THRESHOLD;
    }

}
