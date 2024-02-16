package it.unibo.model.impl;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import it.unibo.common.property.PropertyPosition;
import it.unibo.common.property.PropertyType;
import it.unibo.model.api.Player;
import it.unibo.model.api.Property;
import it.unibo.model.api.PropertyManager;

/**
 * Property manager implementation.
 */
public final class PropertyManagerImpl implements PropertyManager {
    private final Set<Property> properies = new LinkedHashSet<>();

    @Override
    public void addSettlement(final PropertyPosition position, final Player player) {
        if (properies.stream().anyMatch(r -> r.getPosition().equals(position))) {
            throw new IllegalArgumentException("Settlement was already present");
        }
        properies.add(new PropertyImpl(position, player));
    }

    @Override
    public Set<Property> getPlayerProperties(final Player player) {
        return properies.stream().filter(r -> r.getOwner().equals(player)).collect(Collectors.toSet());
    }

    @Override
    public void upgradeToCity(final PropertyPosition position) {
        final Optional<Property> property = properies.stream().filter(p -> p.getPosition().equals(position))
                .findFirst();
        if (property.isPresent()) {
            property.get().upgrade();
        }
    }

    @Override
    public PropertyType getPropertyType(final PropertyPosition position) {
        return properies.stream().filter(p -> p.getPosition().equals(position))
                .findFirst().map(p -> p.getPropertyType()).orElse(PropertyType.EMPTY);
    }

    @Override
    public Set<Property> getAllPlayersProperties(final List<Player> players) {
        final Set<Property> out = new HashSet<>();
        players.forEach(player -> {
            getPlayerProperties(player).forEach(property -> out.add(property));
        });
        return out;
    }

}
