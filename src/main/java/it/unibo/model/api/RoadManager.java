package it.unibo.model.api;

import java.util.Set;

import it.unibo.common.api.road.RoadPosition;

/**
 * Road manager.
 */
public interface RoadManager {

    /**
     * Adds a road.
     * 
     * @param position the position of the road
     * @param player   the player who owns the road
     */
    void buildRoad(RoadPosition position, Player player);

    /**
     * @param player
     * @return a set of the roads built by {@code player}
     */
    Set<Road> getPlayerRoads(Player player);

    /**
     * @param player
     * @return the length of the longest road (without cycles) owned by
     *         {@code player}
     */
    int getLongestRoadLength(Player player);

    /**
     * @return the player owning the longest road
     */
    Player getLongestRoadOwner();

}
