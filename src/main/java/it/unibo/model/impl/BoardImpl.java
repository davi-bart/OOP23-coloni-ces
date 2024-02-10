package it.unibo.model.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import it.unibo.common.api.PropertyPosition;
import it.unibo.common.api.TerrainType;
import it.unibo.common.api.TileCoordinates;
import it.unibo.model.api.Board;
import it.unibo.model.api.GameMapGenerator;
import it.unibo.model.api.Player;
import it.unibo.model.api.Property;
import it.unibo.model.api.Road;
import it.unibo.model.api.RoadManager;
import it.unibo.model.api.Tile;

/**
 * Board implementation.
 */
public final class BoardImpl implements Board {
    private final Map<TileCoordinates, Tile> board;
    private final List<Property> properties = new ArrayList<>();
    private final RoadManager roadManager = new RoadManagerImpl();
    private TileCoordinates robberPosition = null;

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
    public void addProperty(final PropertyPosition position, final Player owner) {
        if (this.properties.stream().anyMatch(p -> p.getPosition().equals(position))) {
            throw new IllegalArgumentException("Property already present");
        }
        this.properties.add(new City(position, owner));
    }

    @Override
    public RoadManager getRoadManager() {
        return roadManager;
    }

    @Override
    public Set<Road> getPlayerRoads(final Player player) {
        return roadManager.getPlayerRoads(player);
    }

    @Override
    public Optional<TileCoordinates> getRobberPosition() {
        return Optional.ofNullable(robberPosition);
    }

    @Override
    public void setRobberPosition(TileCoordinates coordinates) {
        if (robberPosition != null && robberPosition.equals(coordinates)) {
            throw new IllegalArgumentException("Robber is already present in that coordinate.");
        }
        robberPosition = coordinates;
    }
}
