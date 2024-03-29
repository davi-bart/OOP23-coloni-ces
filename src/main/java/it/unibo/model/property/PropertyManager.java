package it.unibo.model.property;

import java.util.Optional;
import java.util.Set;

import it.unibo.common.property.PropertyPosition;
import it.unibo.common.property.PropertyType;
import it.unibo.model.player.Player;

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
     * @param player the player
     * @return a set of the properties built by {@code player}
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

    /**
     * @param position the position of the property
     * @return the owner of the property at the given position
     */
    Optional<Player> getPropertyOwner(PropertyPosition position);
}
