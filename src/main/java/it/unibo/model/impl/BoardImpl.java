package it.unibo.model.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.unibo.common.api.PropertyPosition;
import it.unibo.common.api.RoadPosition;
import it.unibo.common.api.TerrainType;
import it.unibo.common.api.TileCoordinates;
import it.unibo.model.api.Board;
import it.unibo.model.api.GameMapGenerator;
import it.unibo.model.api.Player;
import it.unibo.model.api.Property;
import it.unibo.model.api.Road;
import it.unibo.model.api.Tile;

/**
 * Board implementation.
 */
public final class BoardImpl implements Board {
    private final Map<TileCoordinates, Tile> board;
    private final List<Road> roads = new ArrayList<>();
    private final List<Property> properties = new ArrayList<>();

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
    public void addRoad(RoadPosition position, Player owner) {
        if (this.roads.stream().anyMatch(r -> r.getPosition().equals(position))) {
            throw new IllegalArgumentException("Road already present");
        }
        this.roads.add(new RoadImpl(position, owner));
    }

    @Override
    public void addProperty(PropertyPosition position, Player owner) {
        if (this.properties.stream().anyMatch(p -> p.getPosition().equals(position))) {
            throw new IllegalArgumentException("Property already present");
        }
        this.properties.add(new City(position, owner));
    }
}
