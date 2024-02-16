package it.unibo.model.property;

import it.unibo.common.property.PropertyPosition;
import it.unibo.common.property.PropertyType;
import it.unibo.model.player.Player;

/**
 * Property.
 */
public interface Property {
    /**
     * @return the player that owns the propriety.
     */
    Player getOwner();

    /**
     * @return the position of the property.
     */
    PropertyPosition getPosition();

    /**
     * 
     * @return the type of the property.
     */
    PropertyType getPropertyType();

    /**
     * Upgrade a settlemet to city.
     */
    void upgrade();
}
