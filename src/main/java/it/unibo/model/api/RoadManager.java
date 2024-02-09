package it.unibo.model.api;

import java.util.Set;

import it.unibo.common.api.RoadPosition;

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

}
