package it.unibo.model.impl;

import it.unibo.model.api.Player;
import it.unibo.model.api.Property;
/**
 * Settlement implementation.
 */
public final class Settlement implements Property {  
    final Player owner;
    static final int POINTS = 1;
    static final int RESOURCE_GENERATOR_MULTIPLIER = 1;
    /**
     * Get the point that the city values.
	 * @return the point that the city values
     */
    public static int getPoints() {
        return POINTS;
    }
    /**
     * Get the multiplier for resource production of the settlement.
	 * @return the multiplier for resource production of the settlement
     */
    public static int getResourceGeneratorMultiplier() {
        return RESOURCE_GENERATOR_MULTIPLIER;
    }
    /**
    * Create a settlement.
    * @param owner is the owner of the settlement.
     */
    public Settlement(final Player owner) {
        this.owner =  owner;
    }

    @Override
	public Player getOwner() {
        return this.owner;
    }
    
}
