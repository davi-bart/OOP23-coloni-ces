package it.unibo.controller.main.message;

import it.unibo.common.card.CardType;
import it.unibo.common.property.PropertyType;
import it.unibo.common.tile.ResourceType;

public interface MessageFactory {
    /**
     * Create a message representing the resource and the amount.
     * 
     * @param resource resource
     * @param amount   amount
     * @return the message
     */
    Message createResourceMessage(ResourceType resource, int amount);

    /**
     * Create a message representing the card.
     * 
     * @param card card.
     * @return the message.
     */
    Message createCardMessage(CardType card);

    /**
     * Create a message representing the property.
     * 
     * @param property property
     * @return the message
     */
    Message createBuildPropertyMessage(PropertyType property);

    /**
     * Create a message representing the road.
     * 
     * @return the message
     */
    Message createBuildRoadMessage();
}
