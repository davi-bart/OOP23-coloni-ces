package it.unibo.model.road;

import it.unibo.common.road.RoadPosition;
import it.unibo.model.player.Player;

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
