package it.unibo.model.road;

import java.util.Optional;
import java.util.Set;

import it.unibo.common.road.RoadPosition;
import it.unibo.model.player.Player;

/**
 * Road manager.
 */
public interface RoadManager {

    /**
     * @param position
     * @return whether a road can be built at position {@code position}
     */
    boolean canBuildRoad(RoadPosition position);

    /**
     * Builds a road.
     * 
     * @param position the position of the road
     * @param player   the player who owns the road
     * @throws IllegalArgumentException if {@link #canBuildRoad} is false
     * 
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
     * @return an optional containing, if present, the player owning the longest
     *         road
     */
    Optional<Player> getLongestRoadOwner();

}
