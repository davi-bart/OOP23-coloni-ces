package it.unibo.model.property;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import it.unibo.common.property.PropertyPosition;
import it.unibo.common.property.PropertyType;
import it.unibo.model.player.Player;

/**
 * Property manager implementation.
 */
public final class PropertyManagerImpl implements PropertyManager {
    private final Set<Property> properties = new LinkedHashSet<>();

    @Override
    public void addSettlement(final PropertyPosition position, final Player player) {
        if (properties.stream().anyMatch(r -> r.getPosition().equals(position))) {
            throw new IllegalArgumentException("Settlement was already present");
        }
        properties.add(new PropertyImpl(position, player));
        player.incrementVictoryPoints(1);
    }

    @Override
    public Set<Property> getPlayerProperties(final Player player) {
        return properties.stream().filter(r -> r.getOwner().equals(player)).collect(Collectors.toSet());
    }

    @Override
    public void upgradeToCity(final PropertyPosition position) {
        final Optional<Property> property = properties.stream().filter(p -> p.getPosition().equals(position))
                .findFirst();
        if (property.isPresent()) {
            property.get().upgrade();
            property.get().getOwner().incrementVictoryPoints(1);
        } else {
            throw new IllegalArgumentException("Cannot build a city in an empty position");
        }
    }

    @Override
    public PropertyType getPropertyType(final PropertyPosition position) {
        return properties.stream().filter(p -> p.getPosition().equals(position))
                .findFirst().map(p -> p.getPropertyType()).orElse(PropertyType.EMPTY);
    }

    @Override
    public Optional<Player> getPropertyOwner(final PropertyPosition position) {
        return properties.stream().filter(p -> p.getPosition().equals(position))
                .findFirst().map(p -> p.getOwner());
    }

}
