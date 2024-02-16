package it.unibo.controller.impl;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import it.unibo.common.property.PropertyDirection;
import it.unibo.common.property.PropertyPosition;
import it.unibo.common.property.PropertyPositionImpl;
import it.unibo.common.property.PropertyType;
import it.unibo.common.road.RoadDirection;
import it.unibo.common.road.RoadPosition;
import it.unibo.common.road.RoadPositionImpl;
import it.unibo.common.tile.TerrainType;
import it.unibo.common.tile.TilePosition;
import it.unibo.controller.api.BoardController;
import it.unibo.model.api.Board;
import it.unibo.model.api.Player;
import it.unibo.model.api.PropertyManager;
import it.unibo.model.api.RoadManager;

/**
 * Board controller implementation.
 */
public final class BoardControllerImpl implements BoardController {
    private final Function<String, Player> getPlayerByName;
    private final Board board;
    private final RoadManager roadManager;
    private final PropertyManager propertyManager;

    /**
     * Constructor of BoardControllerImpl.
     * 
     * @param getPlayerByName the function to get the player by name
     * @param board           the board
     * @param propertyManager the property manager
     * @param roadManager     the road manager
     */
    public BoardControllerImpl(final Function<String, Player> getPlayerByName, final Board board,
            final PropertyManager propertyManager, final RoadManager roadManager) {
        this.getPlayerByName = getPlayerByName;
        this.board = board;
        this.propertyManager = propertyManager;
        this.roadManager = roadManager;
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
    public Set<RoadPosition> getPlayerRoadPositions(final String playerName) {
        return this.roadManager.getPlayerRoads(getPlayerByName.apply(playerName)).stream().map(r -> r.getPosition())
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Pair<PropertyPosition, PropertyType>> getPlayerPropertyPositions(final String playerName) {
        return this.propertyManager.getPlayerProperties(getPlayerByName.apply(playerName)).stream()
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
    public PropertyType getPropertyType(final PropertyPosition position) {
        return this.propertyManager.getPropertyType(position);
    }

    @Override
    public int getLongestRoadLength(final String playerName) {
        return this.roadManager.getLongestRoadLength(getPlayerByName.apply(playerName));
    }

    @Override
    public TilePosition getRobberPosition() {
        return this.board.getRobberPosition();
    }

    @Override
    public void setRobberPosition(final TilePosition coordinates) {
        this.board.setRobberPosition(coordinates);
    }
}
