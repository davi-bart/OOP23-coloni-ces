package it.unibo.model.impl;

import it.unibo.common.api.property.PropertyPosition;
import it.unibo.common.api.property.PropertyType;
import it.unibo.model.api.Player;
import it.unibo.model.api.Property;

/**
 * Property implementation.
 */
public final class PropertyImpl implements Property {
    private final PropertyPosition position;
    private final Player owner;
    private PropertyType type = PropertyType.SETTLEMENT;

    /**
     * Constructor of property implementation.
     * 
     * @param position the position of the property
     * @param owner    the owner of the property
     */
    public PropertyImpl(final PropertyPosition position, final Player owner) {
        this.position = position;
        this.owner = owner;
    }

    @Override
    public PropertyPosition getPosition() {
        return this.position;
    }

    @Override
    public Player getOwner() {
        return this.owner;
    }

    @Override
    public PropertyType getPropertyType() {
        return this.type;
    }

    @Override
    public void upgrade() {
        if (this.type.equals(PropertyType.CITY)) {
            throw new IllegalStateException("Cannot upgrade a city into a city");
        }
        this.type = PropertyType.CITY;
    }

}
