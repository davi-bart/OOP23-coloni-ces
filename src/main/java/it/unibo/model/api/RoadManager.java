package it.unibo.model.api;

import java.util.Set;

import it.unibo.common.api.RoadPosition;

/**
 * Road manager.
 */
public interface RoadManager {

    /**
     * Adds a road.
     * 
     * @param player   the player who owns the road
     * @param position the position of the road
     */
    void addRoad(Player player, RoadPosition position);

    /**
     * @param player
     * @return a set of the roads built by {@code player}
     */
    Set<Road> getPlayerRoads(Player player);

    /**
     * @param player
     * @return the length of the longest road owned by {@code player}
     */
    int getLongestRoadLength(Player player);

}
