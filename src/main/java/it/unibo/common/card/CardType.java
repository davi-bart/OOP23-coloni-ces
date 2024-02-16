package it.unibo.common.card;

/**
 * CardType.
 */
// CHECKSTYLE: JavadocVariable OFF
public enum CardType {
    /**
     * gives the player the ability to move the robber
     */
    KNIGHT,
    /**
     * gives the player the exact resources needed to build a settlement
     */
    FREE_SETTLEMENT,
    /**
     * gives the player the exact resources needed to build a road
     */
    FREE_ROAD,
    /**
     * steals all the resources of a random type from every player
     */
    MONOPOLY,
    /**
     * gives the player 1 additional victory point
     */
    VICTORY_POINT;
}
