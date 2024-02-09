package it.unibo.model.impl;

import it.unibo.common.api.RoadPosition;
import it.unibo.model.api.Road;

/**
 * Road implementation.
 */
public class RoadImpl implements Road {

    private final RoadPosition position;

    /**
     * Constructor of road implementation.
     * 
     * @param position the position of the road
     */
    public RoadImpl(final RoadPosition position) {
        this.position = position;
    }

    @Override
    public RoadPosition getPosition() {
        return position;
    }

}
