package it.unibo.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import it.unibo.model.player.Player;
import it.unibo.model.player.PlayerImpl;
import it.unibo.model.turn.TurnManager;
import it.unibo.model.turn.TurnManagerImpl;

class TurnManagerTest {


    private final Player player1 = new PlayerImpl("Alex");
    private final Player player2 = new PlayerImpl("Mattia");
    private final Player player3 = new PlayerImpl("Andrea");
    private final Player player4 = new PlayerImpl("Davide");
    private List<Player> players = List.of(player1, player2, player3, player4);
    private final TurnManager turnManager = new TurnManagerImpl(players, false);
    
    



    @Test
    void testTurn(){
        assertTrue(turnManager.getCurrentPlayer().equals(player1));
        turnManager.endTurn();
        assertTrue(turnManager.getCurrentPlayer().equals(player2));
        turnManager.endTurn();
        assertTrue(turnManager.getCurrentPlayer().equals(player3));
        turnManager.endTurn();
        assertTrue(turnManager.getCurrentPlayer().equals(player4));
    }

}
