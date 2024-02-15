package it.unibo.model.api;

import it.unibo.common.api.card.CardType;

/**
 * DevelopmentCards interface.
 */
public interface DevelopmentCards {
    /**
     * @return the type of the pulled card.
     */
    CardType getCard();

    /**
     * @return if the deck has 0 cards.
     */
    boolean isDeckEmpty();
}
