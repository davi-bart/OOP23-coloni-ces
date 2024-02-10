package it.unibo.model.api;

import it.unibo.common.api.PropertyPosition;

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
}
