package it.unibo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import it.unibo.model.player.Player;
import it.unibo.model.player.PlayerImpl;
import it.unibo.model.turn.TurnManager;
import it.unibo.model.turn.TurnManagerImpl;

/**
 * Tester for turnManager.
 */
class TurnManagerTest {

    private final Player player1 = new PlayerImpl("Alex");
    private final Player player2 = new PlayerImpl("Mattia");
    private final Player player3 = new PlayerImpl("Andrea");
    private final Player player4 = new PlayerImpl("Davide");
    private final List<Player> players = List.of(player1, player2, player3, player4);
    private final TurnManager turnManager = new TurnManagerImpl(players, false);

    @Test
    void testCurrentPlayer() {
        assertEquals(player1, turnManager.getCurrentPlayer());
        turnManager.endTurn();
        assertEquals(player2, turnManager.getCurrentPlayer());
        turnManager.endTurn();
        assertEquals(player3, turnManager.getCurrentPlayer());
        turnManager.endTurn();
        assertEquals(player4, turnManager.getCurrentPlayer());
        turnManager.endTurn();
        assertEquals(player4, turnManager.getCurrentPlayer());
        turnManager.endTurn();
        assertEquals(player3, turnManager.getCurrentPlayer());
    }

    @Test
    void testTurnNumber() {
        assertEquals(0, turnManager.getTurnNumber());
        turnManager.endTurn();
        assertEquals(1, turnManager.getTurnNumber());
        turnManager.endTurn();
        assertEquals(2, turnManager.getTurnNumber());
        turnManager.endTurn();
        assertEquals(3, turnManager.getTurnNumber());
        assertEquals(1, turnManager.getCycle());
        turnManager.endTurn();
        assertEquals(4, turnManager.getTurnNumber());
        assertEquals(2, turnManager.getCycle());
    }
}
