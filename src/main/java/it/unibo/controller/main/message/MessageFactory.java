package it.unibo.controller.main.message;

import it.unibo.common.card.CardType;
import it.unibo.common.property.PropertyType;
import it.unibo.common.tile.ResourceType;

public interface MessageFactory {
    Message createResourceMessage(ResourceType resource, int amount);

    Message createCardMessage(CardType card);

    Message createBuildPropertyMessage(PropertyType property);

    Message createBuildRoadMessage();
}
