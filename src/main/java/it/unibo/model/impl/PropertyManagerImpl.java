package it.unibo.model.impl;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import it.unibo.common.api.PropertyPosition;
import it.unibo.model.api.Player;
import it.unibo.model.api.Property;
import it.unibo.model.api.PropertyManager;

public class PropertyManagerImpl implements PropertyManager {

    private final Set<Property> properies = new LinkedHashSet<>();

    @Override
    public void addSettlement(Player player, PropertyPosition position) {
        if (properies.stream().anyMatch(r -> r.getPosition().equals(position))) {
            throw new IllegalArgumentException("Settlement was already present");
        }
        properies.add(new PropertyImpl(position, player));
    }

    @Override
    public Set<Property> getPlayerProperties(Player player) {
        return properies.stream().filter(r -> r.getOwner().equals(player)).collect(Collectors.toSet());
    }

    @Override
    public void upgradeToCity(PropertyPosition position) {

        final Optional<Property> property = properies.stream().filter(p -> p.getPosition().equals(position))
                .findFirst();
        if (property.isPresent()) {
            property.get().upgrade();
        }
    }

}
