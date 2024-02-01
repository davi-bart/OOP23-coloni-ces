package it.unibo.model.impl;

import it.unibo.common.ResourceType;
import it.unibo.model.api.ResourceManager;
import it.unibo.model.api.Trader;

/**
 * An implementation of ResourceManager.
 */
public class ResourceManagerImpl implements ResourceManager {
    private final Trader trader;

    /**
     * Create a ResourceManagerImpl.
     * 
     * @param trader is the trader that the ResourceManager is managing.
     */
    public ResourceManagerImpl(final Trader trader) {
        this.trader = trader;
    }

    @Override
    public final void addResources(final ResourceType resource, final int amount) {
        if (amount > 0) {
            this.trader.setResource(resource, amount + this.trader.getResource(resource));
        } else {
            throw new IllegalArgumentException("amount must be positive");
        }
    }

    @Override
    public final void removeResources(final ResourceType resource, final int amount) {
        if (this.trader.getResource(resource) >= amount) {
            this.trader.setResource(resource, this.trader.getResource(resource) - amount);
        } else {
            throw new IllegalArgumentException("amount must be minor than the total resource");
        }
    }

}
