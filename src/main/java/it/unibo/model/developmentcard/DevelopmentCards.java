package it.unibo.model.developmentcard;

import it.unibo.common.card.CardType;

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
