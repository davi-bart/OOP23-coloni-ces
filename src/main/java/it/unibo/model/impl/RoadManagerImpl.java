package it.unibo.model.impl;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.unibo.common.api.RoadPosition;
import it.unibo.model.api.Player;
import it.unibo.model.api.Road;
import it.unibo.model.api.RoadManager;

/**
 * Road manager implementation.
 */
public final class RoadManagerImpl implements RoadManager {

    private final Set<Road> roads = new LinkedHashSet<>();

    @Override
    public void addRoad(final Player player, final RoadPosition position) {
        if (roads.stream().anyMatch(r -> r.getPosition().equals(position))) {
            throw new IllegalArgumentException("Road was already present");
        }
        roads.add(new RoadImpl(position, player));
    }

    @Override
    public Set<Road> getPlayerRoads(final Player player) {
        return roads.stream().filter(r -> r.getOwner().equals(player)).collect(Collectors.toSet());
    }

    @Override
    public int getLongestRoadLength(final Player player) {
        final Graph<Road, DefaultEdge> graph = new DefaultUndirectedGraph<>(DefaultEdge.class);
        final Set<Road> playerRoads = roads.stream().filter(road -> road.getOwner().equals(player))
                .collect(Collectors.toSet());
        playerRoads.forEach(road -> graph.addVertex(road));
        playerRoads.forEach(
                road -> getNearbyRoads(road).forEach(r -> graph.addEdge(road, r)));
        final BreadthFirstIterator<Road, DefaultEdge> bfsIterator = new BreadthFirstIterator<>(graph);
        return graph.vertexSet().stream().mapToInt(road -> bfsIterator.getDepth(road)).max().orElse(0);
    }

    /**
     * @param road
     * @return a set of roads nearby {@code road}
     */
    private Set<Road> getNearbyRoads(final Road road) {
        return roads.stream().filter(r -> road.getPosition().isNearby(r.getPosition())).collect(Collectors.toSet());
    }

}
