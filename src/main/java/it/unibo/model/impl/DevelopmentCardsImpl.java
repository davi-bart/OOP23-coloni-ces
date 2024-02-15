package it.unibo.model.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.Map;

import it.unibo.common.api.card.CardType;
import it.unibo.model.api.DevelopmentCards;

/**
 * Development cards implementation.
 */
public final class DevelopmentCardsImpl implements DevelopmentCards {
    private final List<CardType> deck = new ArrayList<>();
    private final Random random = new Random();

    /**
     * Constructor of DevelopmentCardsImpl.
     */
    public DevelopmentCardsImpl() {
        final int knightCards = 15;
        final int roadBuildCards = 5;
        final int yearOfPlentyCards = 2;
        final int monopolyCards = 2;
        final int victoryPointCards = 2;
        /*final Map<CardType, Integer> cards = Map.of(CardType.KNIGHT, knightCards, CardType.ROADBUILD, roadBuildCards,
                CardType.YEAROFPLENTY, yearOfPlentyCards, CardType.MONOPOLY, monopolyCards, CardType.VICTORYPOINT,
                victoryPointCards);*/
                final Map<CardType, Integer> cards = Map.of(CardType.VICTORYPOINT,
                victoryPointCards);
        cards.forEach((card, amount) -> {
            deck.addAll(IntStream.range(0, amount).mapToObj(i -> card).collect(Collectors.toList()));
        });
    }

    @Override
    public CardType getCard() {
        if (!isDeckEmpty()) {
            final int index = random.nextInt(deck.size());
            final CardType pulledCard = deck.get(index);
            deck.remove(index);
            return pulledCard;
        } else {
            throw new IllegalStateException("There are no remaining cards");
        }

    }

    @Override
    public boolean isDeckEmpty() {
        return deck.isEmpty();
    }

}
