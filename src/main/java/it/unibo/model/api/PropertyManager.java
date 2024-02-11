package it.unibo.model.api;

import java.util.Set;

import it.unibo.common.api.PropertyPosition;

/**
 * Property manager.
 */
public interface PropertyManager {

    /**
     * Adds a settlement.
     * 
     * @param player   the player who owns the settlement
     * @param position the position of the settlement
     */
    void addSettlement(Player player, PropertyPosition position);

    /**
     * @param player
     * @return a set of the roads built by {@code player}
     */
    Set<Property> getPlayerProperties(Player player);

    /**
     * Upgrade the settlement to a city in the given position.
     * 
     * @param position of the settlement.
     */
    void upgradeToCity(PropertyPosition position);
}
