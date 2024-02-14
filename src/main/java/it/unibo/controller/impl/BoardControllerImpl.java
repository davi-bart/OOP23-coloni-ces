package it.unibo.controller.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import it.unibo.common.api.property.PropertyDirection;
import it.unibo.common.api.property.PropertyPosition;
import it.unibo.common.api.property.PropertyType;
import it.unibo.common.api.road.RoadDirection;
import it.unibo.common.api.road.RoadPosition;
import it.unibo.common.api.tile.TerrainType;
import it.unibo.common.api.tile.TilePosition;
import it.unibo.common.impl.property.PropertyPositionImpl;
import it.unibo.common.impl.road.RoadPositionImpl;
import it.unibo.controller.api.BoardController;
import it.unibo.model.api.Board;
import it.unibo.model.api.Player;
import it.unibo.model.api.PropertyManager;
import it.unibo.model.api.RoadManager;
import it.unibo.model.impl.PropertyManagerImpl;
import it.unibo.model.impl.RoadManagerImpl;

/**
 * Board controller implementation.
 */
public final class BoardControllerImpl implements BoardController {
    private final Board board;
    private final RoadManager roadManager = new RoadManagerImpl();
    private final PropertyManager propertyManager = new PropertyManagerImpl();

    /**
     * Constructor of BoardControllerImpl.
     * 
     * @param board the board to start with
     */
    public BoardControllerImpl(final Board board) {
        this.board = board;
    }

    @Override
    public List<TilePosition> getTilePositions() {
        return this.board.getTilePositions();
    }

    @Override
    public int getTileNumber(final TilePosition pos) {
        return this.board.getTileNumber(pos);
    }

    @Override
    public TerrainType getTileTerrainType(final TilePosition pos) {
        return this.board.getTileTerrainType(pos);
    }

    @Override
    public Set<RoadPosition> getPlayerRoadPositions(final Player player) {
        return this.roadManager.getPlayerRoads(player).stream().map(r -> r.getPosition()).collect(Collectors.toSet());
    }

    @Override
    public Set<Pair<PropertyPosition, PropertyType>> getPlayerPropertyPositions(final Player player) {
        return this.propertyManager.getPlayerProperties(player).stream()
                .map(p -> new ImmutablePair<PropertyPosition, PropertyType>(p.getPosition(), p.getPropertyType()))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<PropertyPosition> getAllPropertyPositions() {
        return getTilePositions().stream()
                .flatMap(tilePos -> Stream.of(PropertyDirection.values())
                        .map(dir -> new PropertyPositionImpl(tilePos, dir)))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<RoadPosition> getAllRoadPositions() {
        return getTilePositions().stream()
                .flatMap(tilePos -> Stream.of(RoadDirection.values()).map(dir -> new RoadPositionImpl(tilePos, dir)))
                .collect(Collectors.toSet());
    }

    @Override
    public void buildSettlement(final PropertyPosition position, final Player player) {
        this.propertyManager.addSettlement(position, player);
    }

    @Override
    public void buildCity(final PropertyPosition position, final Player player) {
        this.propertyManager.upgradeToCity(position);
    }

    @Override
    public PropertyType getPropertyType(final PropertyPosition position) {
        return this.propertyManager.getPropertyType(position);
    }

    @Override
    public void buildRoad(final RoadPosition position, final Player player) {
        this.roadManager.addRoad(position, player);
    }

    @Override
    public int getLongestRoadLength(final Player player) {
        return this.roadManager.getLongestRoadLength(player);
    }

    @Override
    public TilePosition getRobberPosition() {
        return this.board.getRobberPosition();
    }

    @Override
    public boolean isNearToAnyProperty(final PropertyPosition position) {
        return getAllPropertyPositions().stream().anyMatch(propertyPosition -> {
            if (!getPropertyType(propertyPosition).equals(PropertyType.EMPTY)) {
                return propertyPosition.isNear(position);
            }
            return false;
        });
    }

    @Override
    public boolean isRoadNearToAnyOwnedProperty(final Player player, final RoadPosition position) {
        return this.getPlayerPropertyPositions(player).stream()
                .anyMatch(propertyPosition -> {
                    return position.isNearToProperty(propertyPosition.getKey());
                });
    }

    @Override
    public boolean isPropertyNearToAnyOwnerRoad(final Player player, final PropertyPosition position) {
        return this.getPlayerRoadPositions(player).stream()
                .anyMatch(roadPosition -> {
                    return roadPosition.isNearToProperty(position);
                });
    }

    @Override
    public boolean isRoadNearToAnyOwnedRoad(final Player player, final RoadPosition position) {
        return this.getPlayerRoadPositions(player).stream().anyMatch(roadPosition -> {
            return position.isNearby(roadPosition);
        });
    }
}
