package it.unibo.model.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import it.unibo.common.TerrainType;
import it.unibo.model.api.Property;
import it.unibo.model.api.PropertyDirection;
import it.unibo.model.api.Road;
import it.unibo.model.api.Tile;
import it.unibo.model.api.RoadDirection;

/**
 * Tile implementation.
 */
public final class TileImpl implements Tile {
    private final int number;
    private final TerrainType terrainType;
    private final Map<RoadDirection, Road> roads = new HashMap<>();
    private final Map<PropertyDirection, Property> properties = new HashMap<>();

    TileImpl(final TerrainType terrainType, final int number) {
        this.terrainType = terrainType;
        this.number = number;
    }

    @Override
    public int getNumber() {
        return this.number;
    }

    @Override
    public Optional<Road> getRoad(final RoadDirection direction) {
        return Optional.ofNullable(this.roads.get(direction));
    }

    @Override
    public Optional<Property> getProperty(final PropertyDirection direction) {
        return Optional.ofNullable(this.properties.get(direction));
    }

    @Override
    public TerrainType getTerrainType() {
        return this.terrainType;
    }

}
