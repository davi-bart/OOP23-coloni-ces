package it.unibo.model.api;

import java.util.Set;

import it.unibo.common.api.property.PropertyPosition;
import it.unibo.common.api.property.PropertyType;

/**
 * Property manager.
 */
public interface PropertyManager {

    /**
     * Adds a settlement.
     * 
     * @param position the position of the settlement
     * @param player   the player who owns the settlement
     */
    void addSettlement(PropertyPosition position, Player player);

    /**
     * @param player
     * @return a set of the roads built by {@code player}
     */
    Set<Property> getPlayerProperties(Player player);

    /**
     * get the property type of the property at the given position.
     * 
     * @param position the position of the property
     * @return the type of the property
     */
    PropertyType getPropertyType(PropertyPosition position);

    /**
     * Upgrade the settlement to a city in the given position.
     * 
     * @param position of the settlement.
     */
    void upgradeToCity(PropertyPosition position);
}
