package it.unibo.model.road;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.AllDirectedPaths;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import it.unibo.common.property.PropertyPosition;
import it.unibo.common.road.RoadPosition;
import it.unibo.model.player.Player;

/**
 * Road manager implementation.
 */
public final class RoadManagerImpl implements RoadManager {

    private final Set<Road> roads = new LinkedHashSet<>();
    private Optional<Player> longestRoadOwner = Optional.empty();

    @Override
    public boolean canBuildRoad(final RoadPosition position) {
        return !roads.stream().anyMatch(r -> r.getPosition().equals(position));
    }

    @Override
    public void buildRoad(final RoadPosition position, final Player player) {
        if (!canBuildRoad(position)) {
            throw new IllegalArgumentException(
                    "Player " + player + " cannot a build a road at position " + position + ": it is already present");
        }
        roads.add(new RoadImpl(position, player));
        checkLongestRoadOwner(player);
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
                .forEach(propertyPositions::add));
        propertyPositions.forEach(graph::addVertex);

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
        return allPaths.getAllPaths(propertyPositions, propertyPositions, true, null).stream()
                .mapToInt(GraphPath::getLength).reduce(0, Integer::max);
    }

    @Override
    public Optional<Player> getLongestRoadOwner() {
        return longestRoadOwner;
    }

    @Override
    public Optional<Player> getRoadOwner(final RoadPosition position) {
        return roads.stream().filter(r -> r.getPosition().equals(position)).findFirst().map(Road::getOwner);
    }

    /**
     * Checks whether player {@code player} can become the longest road owner.
     * If that is the case, it updates the longest road owner and the victory
     * points.
     * 
     * @param player
     */
    private void checkLongestRoadOwner(final Player player) {
        final int longestRoadVictoryPoints = 2;
        if (isFirstLongestRoadOwner(player) || isNewLongestRoadOwner(player)) {
            if (longestRoadOwner.isPresent()) {
                longestRoadOwner.get().decrementVictoryPoints(longestRoadVictoryPoints);
            }
            longestRoadOwner = Optional.of(player);
            longestRoadOwner.get().incrementVictoryPoints(longestRoadVictoryPoints);
        }
    }

    private boolean isFirstLongestRoadOwner(final Player player) {
        final int minimumLongestRoadLength = 5;
        return longestRoadOwner.isEmpty() && getLongestRoadLength(player) >= minimumLongestRoadLength;
    }

    private boolean isNewLongestRoadOwner(final Player player) {
        return longestRoadOwner.isPresent() && !player.equals(longestRoadOwner.get())
                && getLongestRoadLength(player) > getLongestRoadLength(longestRoadOwner.get());
    }

}
