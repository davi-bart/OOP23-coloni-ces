package it.unibo.model.api;

import it.unibo.common.api.property.PropertyPosition;
import it.unibo.common.api.property.PropertyType;

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
