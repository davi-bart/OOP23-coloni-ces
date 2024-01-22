package it.unibo.model.api;

import java.util.Optional;

public interface Tile {

    /**
     * @return the number on the tile.
     */
    int getNumber();

    /**
     * @return the street in the given direction.
     */
    Optional<Street> getStreet(StreetDirection direction);

    /**
     * @return the property in the given direction.
     */
    Optional<Property> getProperty(PropertyDirection direction);
}
