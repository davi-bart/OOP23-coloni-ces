package it.unibo.model.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import it.unibo.common.api.RoadPosition;
import it.unibo.model.api.Player;
import it.unibo.model.api.Road;
import it.unibo.model.api.RoadManager;

/**
 * Road manager implementation.
 */
public final class RoadManagerImpl implements RoadManager {

    private final List<Road> roads = new ArrayList<>();

    @Override
    public void addRoad(final Player player, final RoadPosition position) {
        final Road newRoad = new RoadImpl(position, player);
        if (roads.contains(newRoad)) {
            throw new IllegalArgumentException("Road was already present");
        }
        roads.add(newRoad);
    }

    @Override
    public Set<Road> getPlayerRoads(final Player player) {
        return roads.stream().filter(r -> r.getOwner().equals(player)).collect(Collectors.toSet());
    }

}
