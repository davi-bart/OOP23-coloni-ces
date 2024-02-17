package it.unibo.controller.main.message;

import java.util.Locale;

import it.unibo.common.card.CardType;
import it.unibo.common.property.PropertyType;
import it.unibo.common.tile.ResourceType;

public class MessageFactoryImpl implements MessageFactory {

    @Override
    public Message createResourceMessage(ResourceType resource, int amount) {
        return new Message() {
            @Override
            public String getMessage() {
                return "got " + amount + " " + resource.toString().toLowerCase(Locale.US);
            }
        };
    }

    @Override
    public Message createCardMessage(CardType card) {
        return new Message() {
            @Override
            public String getMessage() {
                return "used " + card.toString().toLowerCase(Locale.US).replace("_", " ") + " card";
            }
        };
    }

    @Override
    public Message createBuildPropertyMessage(PropertyType property) {
        return new Message() {
            @Override
            public String getMessage() {
                return "built a " + property.toString().toLowerCase(Locale.US);
            }
        };
    }

    @Override
    public Message createBuildRoadMessage() {
        return new Message() {
            @Override
            public String getMessage() {
                return "built a road";
            }
        };
    }

}
