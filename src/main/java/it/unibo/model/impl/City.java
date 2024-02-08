package it.unibo.model.impl;

import it.unibo.model.api.Player;
import it.unibo.model.api.Property;
/**
 * City implementation.
 */
public final class City implements Property {

    private final Player owner;
    static final int POINTS = 2;
    static final int RESOURCE_GENERATOR_MULTIPLIER = 2;
    /**
     * Get the point that the city values.
     * @return the point that the city values
    */
    public static int getPoints() {
        return POINTS;
    }
    /**
     * Get the multiplier for resource production of the city.
     * @return the multiplier for resource production of the city
     */
    public static int getResourceGeneratorMultiplier() {
        return RESOURCE_GENERATOR_MULTIPLIER;
    }
/**
 * Create a city.
 * @param owner is the owner of the city.
 */
    public City(final Player owner) {
        this.owner = owner;
    }

    @Override
    public Player getOwner() {
        return this.owner;
    }
}
