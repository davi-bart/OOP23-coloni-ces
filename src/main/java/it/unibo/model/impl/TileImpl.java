package it.unibo.model.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import it.unibo.model.api.Property;
import it.unibo.model.api.PropertyDirection;
import it.unibo.model.api.Street;
import it.unibo.model.api.Tile;
import it.unibo.model.api.StreetDirection;

public class TileImpl implements Tile {
    private final int number;
    private final Map<StreetDirection, Street> streets = new HashMap<>();
    private final Map<PropertyDirection, Property> properties = new HashMap<>();

    TileImpl(final int number) {
        this.number = number;
    }

    @Override
    public int getNumber() {
        return this.number;
    }

    @Override
    public Optional<Street> getStreet(StreetDirection direction) {
        return Optional.ofNullable(this.streets.get(direction));
    }

    @Override
    public Optional<Property> getProperty(PropertyDirection direction) {
        return Optional.ofNullable(this.properties.get(direction));
    }

}
