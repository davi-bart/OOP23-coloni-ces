package it.unibo.model.impl;

import it.unibo.common.api.PropertyPosition;
import it.unibo.model.api.Player;
import it.unibo.model.api.Property;

/**
 * City implementation.
 */
public final class City implements Property {
    static final int POINTS = 2;
    static final int RESOURCE_GENERATOR_MULTIPLIER = 2;
    private final Player owner;
    private final PropertyPosition position;

    /**
     * Get the point that the city values.
     * 
     * @return the point that the city values
     */
    public static int getPoints() {
        return POINTS;
    }

    /**
     * Get the multiplier for resource production of the city.
     * 
     * @return the multiplier for resource production of the city
     */
    public static int getResourceGeneratorMultiplier() {
        return RESOURCE_GENERATOR_MULTIPLIER;
    }

    /**
     * Create a city.
     * 
     * @param position position of the city.
     * @param owner    owner of the city.
     */
    public City(final PropertyPosition position, final Player owner) {
        this.position = position;
        this.owner = owner;
    }

    @Override
    public Player getOwner() {
        return this.owner;
    }

    @Override
    public PropertyPosition getPosition() {
        return this.position;
    }
}
