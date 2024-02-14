package it.unibo.model.api;

import it.unibo.common.api.road.RoadPosition;

/**
 * Road.
 */
public interface Road {

    /**
     * @return the position of the road
     */
    RoadPosition getPosition();

    /**
     * @return the owner of the road
     */
    Player getOwner();

}
