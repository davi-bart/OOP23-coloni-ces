package it.unibo.model.impl;

import it.unibo.common.api.RoadPosition;
import it.unibo.model.api.Player;
import it.unibo.model.api.Road;

/**
 * Road implementation.
 */
public class RoadImpl implements Road {

    private final RoadPosition position;
    private final Player owner;

    /**
     * Constructor of road implementation.
     * 
     * @param position the position of the road
     */
    public RoadImpl(final RoadPosition position, final Player owner) {
        this.position = position;
        this.owner = owner;
    }

    @Override
    public RoadPosition getPosition() {
        return position;
    }

    @Override
    public Player getOwner() {
        return this.owner;
    }

}
