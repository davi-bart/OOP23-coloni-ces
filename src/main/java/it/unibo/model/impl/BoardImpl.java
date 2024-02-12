package it.unibo.model.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import it.unibo.common.api.PropertyPosition;
import it.unibo.common.api.PropertyType;
import it.unibo.common.api.RoadPosition;
import it.unibo.common.api.TerrainType;
import it.unibo.common.api.TileCoordinates;
import it.unibo.model.api.Board;
import it.unibo.model.api.GameMapGenerator;
import it.unibo.model.api.Player;
import it.unibo.model.api.Property;
import it.unibo.model.api.PropertyManager;
import it.unibo.model.api.Road;
import it.unibo.model.api.RoadManager;
import it.unibo.model.api.Tile;

/**
 * Board implementation.
 */
public final class BoardImpl implements Board {
    private final Map<TileCoordinates, Tile> board;
    private final RoadManager roadManager = new RoadManagerImpl();
    private final PropertyManager propertyManager = new PropertyManagerImpl();
    private TileCoordinates robberPosition;

    /**
     * Constructor of the board.
     * 
     * @param generator the map generator
     */
    public BoardImpl(final GameMapGenerator generator) {
        this.board = generator.generate();
    }

    @Override
    public List<TileCoordinates> getTilePositions() {
        return this.board.keySet().stream().toList();
    }

    @Override
    public int getTileNumber(final TileCoordinates pos) {
        if (!this.board.containsKey(pos)) {
            throw new IllegalArgumentException("Position not found");
        }
        return this.board.get(pos).getNumber();
    }

    @Override
    public TerrainType getTileTerrainType(final TileCoordinates pos) {
        if (!this.board.containsKey(pos)) {
            throw new IllegalArgumentException("Position not found");
        }
        return this.board.get(pos).getTerrainType();
    }

    @Override
    public Set<Road> getPlayerRoads(final Player player) {
        return roadManager.getPlayerRoads(player);
    }

    @Override
    public Set<Property> getPlayerProperties(final Player player) {
        return propertyManager.getPlayerProperties(player);
    }

    @Override
    public Optional<TileCoordinates> getRobberPosition() {
        return Optional.ofNullable(robberPosition);
    }

    @Override
    public void setRobberPosition(final TileCoordinates coordinates) {
        if (robberPosition != null && robberPosition.equals(coordinates)) {
            throw new IllegalArgumentException("Robber is already present in that coordinate.");
        }
        robberPosition = coordinates;
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
        return propertyManager.getPropertyType(position);
    }

    @Override
    public void buildRoad(final RoadPosition position, final Player player) {
        roadManager.addRoad(position, player);
    }
}
