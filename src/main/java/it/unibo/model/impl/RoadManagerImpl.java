package it.unibo.model.impl;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.AllDirectedPaths;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import it.unibo.common.api.property.PropertyPosition;
import it.unibo.common.api.road.RoadPosition;
import it.unibo.model.api.Player;
import it.unibo.model.api.Road;
import it.unibo.model.api.RoadManager;

/**
 * Road manager implementation.
 */
public final class RoadManagerImpl implements RoadManager {

    private final Set<Road> roads = new LinkedHashSet<>();
    private Player longestRoadOwner;

    @Override
    public void buildRoad(final RoadPosition position, final Player player) {
        if (roads.stream().anyMatch(r -> r.getPosition().equals(position))) {
            throw new IllegalArgumentException("Road was already present");
        }
        roads.add(new RoadImpl(position, player));
        checkLongestRoad(player);
    }

    @Override
    public Set<Road> getPlayerRoads(final Player player) {
        return roads.stream().filter(r -> r.getOwner().equals(player)).collect(Collectors.toSet());
    }

    @Override
    public int getLongestRoadLength(final Player player) {
        if (getPlayerRoads(player).isEmpty()) {
            return 0;
        }
        /**
         * In order to find the longest road, a graph is created with road endpoints
         * (positions of the property connected by that road) as vertexes and edges
         * between two endpoints if there exists a road between them.
         * 
         * A directed graph is used because the method getAllPaths used to find all the
         * paths is only present in directed graphs.
         */
        final Graph<PropertyPosition, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        final Set<PropertyPosition> propertyPositions = new LinkedHashSet<>();
        final Set<Road> playerRoads = roads.stream().filter(road -> road.getOwner().equals(player))
                .collect(Collectors.toSet());
        playerRoads.forEach(road -> road.getPosition().getNearbyProperties()
                .forEach(propertyPosition -> propertyPositions.add(propertyPosition)));
        propertyPositions.forEach(propertyPos -> graph.addVertex(propertyPos));

        // for each distinct pair of near property positions, an edge is added if there
        // exists a road that connects them
        propertyPositions.forEach(propertyPos -> propertyPositions.stream()
                .filter(propertyPos2 -> !propertyPos.equals(propertyPos2) && propertyPos.isNear(
                        propertyPos2)
                        && playerRoads.stream()
                                .anyMatch(road -> road.getPosition().isNearToProperty(propertyPos)
                                        && road.getPosition().isNearToProperty(propertyPos2)))
                .forEach(nearPosition -> graph.addEdge(propertyPos, nearPosition)));

        final AllDirectedPaths<PropertyPosition, DefaultEdge> allPaths = new AllDirectedPaths<>(graph);
        return allPaths.getAllPaths(propertyPositions, propertyPositions, true, 20).stream()
                .mapToInt(graphPath -> graphPath.getLength()).reduce(0, Integer::max);
    }

    @Override
    public Player getLongestRoadOwner() {
        return longestRoadOwner;
    }

    /**
     * Checks whether player {@code player} can become the longest road owner. If
     * that is the case, it updates the longest road owner and the points.
     * 
     * @param player
     */
    private void checkLongestRoad(final Player player) {
        final int minimumLongestRoadLength = 5;
        final int longestRoadVictoryPoints = 2;
        System.out.println(getLongestRoadLength(player));
        if ((!longestRoadOwnerExists() && getLongestRoadLength(player) >= minimumLongestRoadLength)
                || (longestRoadOwnerExists() && !player.equals(longestRoadOwner)
                        && getLongestRoadLength(player) > getLongestRoadLength(longestRoadOwner))) {
            if (longestRoadOwner != null) {
                longestRoadOwner.decrementVictoryPoints(longestRoadVictoryPoints);
            }
            longestRoadOwner = player;
            longestRoadOwner.incrementVictoryPoints(longestRoadVictoryPoints);
        }
    }

    private boolean longestRoadOwnerExists() {
        return longestRoadOwner != null;
    }

}
