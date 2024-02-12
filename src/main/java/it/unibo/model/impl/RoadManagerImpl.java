package it.unibo.model.impl;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.BFSShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;

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
        if (getPlayerRoads(player).isEmpty()) {
            return 0;
        }
        /**
         * In order to find the longest road, a graph is created with player roads as
         * vertexes. Each pair of nearby roads is connected with an edge. Then a BFS is
         * performed for each vertex, in order to find the furthest road from the given
         * one. Finally, the maximum value is taken and the value is incremented because
         * we count the vertexes instead of the edges.
         */
        final Graph<Road, DefaultEdge> graph = new DefaultUndirectedGraph<>(DefaultEdge.class);
        final Set<Road> playerRoads = roads.stream().filter(road -> road.getOwner().equals(player))
                .collect(Collectors.toSet());
        playerRoads.forEach(road -> graph.addVertex(road));
        playerRoads.forEach(
                road -> getNearbyRoads(road).forEach(r -> graph.addEdge(road, r)));
        final BFSShortestPath<Road, DefaultEdge> bfs = new BFSShortestPath<>(graph);
        return graph.vertexSet().stream()
                .mapToInt(road -> getNearbyRoads(road).stream().mapToInt(r -> bfs.getPath(road, r).getLength())
                        .reduce(0, Integer::max))
                .reduce(0, Integer::max) + 1;
    }

    /**
     * @param road
     * @return a set of roads nearby {@code road}
     */
    private Set<Road> getNearbyRoads(final Road road) {
        return roads.stream().filter(r -> road.getPosition().isNearby(r.getPosition())).collect(Collectors.toSet());
    }

}
