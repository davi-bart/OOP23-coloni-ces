package it.unibo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.common.property.PropertyDirection;
import it.unibo.common.property.PropertyPosition;
import it.unibo.common.property.PropertyPositionImpl;
import it.unibo.common.road.RoadDirection;
import it.unibo.common.road.RoadPositionImpl;
import it.unibo.common.tile.ResourceType;
import it.unibo.common.tile.TilePositionImpl;
import it.unibo.model.player.Player;
import it.unibo.model.player.PlayerImpl;

// CHECKSTYLE: MagicNumber OFF
class GameManagerTest {

    private final Player player1 = new PlayerImpl("first");
    private final Player player2 = new PlayerImpl("second");
    private final Player player3 = new PlayerImpl("third");
    private final GameManager gameManager = new GameManagerImpl(
            List.of(player1.getName(), player2.getName(), player3.getName()));

    @BeforeEach
    void testFirstTwoCycles() {
        final PropertyPosition property1 = new PropertyPositionImpl(new TilePositionImpl(1, 0),
                PropertyDirection.DOWNRIGHT);
        final PropertyPosition property2 = new PropertyPositionImpl(new TilePositionImpl(2, 3),
                PropertyDirection.DOWNRIGHT);
        final PropertyPosition property3 = new PropertyPositionImpl(new TilePositionImpl(2, 0),
                PropertyDirection.DOWNRIGHT);
        final PropertyPosition property4 = new PropertyPositionImpl(new TilePositionImpl(0, 1),
                PropertyDirection.DOWNRIGHT);
        final PropertyPosition property5 = new PropertyPositionImpl(new TilePositionImpl(3, 0),
                PropertyDirection.DOWNRIGHT);
        final PropertyPosition property6 = new PropertyPositionImpl(new TilePositionImpl(3, 2),
                PropertyDirection.DOWNRIGHT);
        assertTrue(gameManager.canBuildSettlement(property1,
                gameManager.getTurnManager().getCurrentPlayer()));
        assertTrue(gameManager.canBuildSettlement(property2,
                gameManager.getTurnManager().getCurrentPlayer()));
        assertTrue(gameManager.canBuildSettlement(property3,
                gameManager.getTurnManager().getCurrentPlayer()));
        assertTrue(gameManager.canBuildSettlement(property4,
                gameManager.getTurnManager().getCurrentPlayer()));
        assertTrue(gameManager.canBuildSettlement(property5,
                gameManager.getTurnManager().getCurrentPlayer()));
        assertTrue(gameManager.canBuildSettlement(property6,
                gameManager.getTurnManager().getCurrentPlayer()));

        // first turn of first cycle
        assertFalse(gameManager.canBuildRoad(
                new RoadPositionImpl(property1.getTilePosition(), RoadDirection.DOWNRIGHT),
                gameManager.getTurnManager().getCurrentPlayer()));
        gameManager.buildSettlement(property1, gameManager.getTurnManager().getCurrentPlayer());
        assertFalse(gameManager.canEndTurn());
        gameManager.buildRoad(new RoadPositionImpl(property1.getTilePosition(), RoadDirection.DOWNRIGHT),
                gameManager.getTurnManager().getCurrentPlayer());
        assertTrue(gameManager.canEndTurn());
        gameManager.getTurnManager().endTurn();

        // second turn of first cycle
        gameManager.buildSettlement(property2, gameManager.getTurnManager().getCurrentPlayer());
        gameManager.buildRoad(new RoadPositionImpl(property2.getTilePosition(), RoadDirection.DOWNRIGHT),
                gameManager.getTurnManager().getCurrentPlayer());
        gameManager.getTurnManager().endTurn();

        // third turn of first cycle
        gameManager.buildSettlement(property3, gameManager.getTurnManager().getCurrentPlayer());
        gameManager.buildRoad(new RoadPositionImpl(property3.getTilePosition(), RoadDirection.DOWNRIGHT),
                gameManager.getTurnManager().getCurrentPlayer());
        gameManager.getTurnManager().endTurn();

        // first turn of second cycle
        gameManager.buildSettlement(property4, gameManager.getTurnManager().getCurrentPlayer());
        assertFalse(gameManager.canEndTurn());
        gameManager.buildRoad(new RoadPositionImpl(property4.getTilePosition(), RoadDirection.DOWNRIGHT),
                gameManager.getTurnManager().getCurrentPlayer());
        assertTrue(gameManager.canEndTurn());
        gameManager.getTurnManager().endTurn();

        // second turn of second cycle
        gameManager.buildSettlement(property5, gameManager.getTurnManager().getCurrentPlayer());
        gameManager.buildRoad(new RoadPositionImpl(property5.getTilePosition(), RoadDirection.DOWNRIGHT),
                gameManager.getTurnManager().getCurrentPlayer());
        gameManager.getTurnManager().endTurn();

        // third turn of second cycle
        gameManager.buildSettlement(property6, gameManager.getTurnManager().getCurrentPlayer());
        gameManager.buildRoad(new RoadPositionImpl(property6.getTilePosition(), RoadDirection.DOWNRIGHT),
                gameManager.getTurnManager().getCurrentPlayer());
        gameManager.getTurnManager().endTurn();

        assertFalse(gameManager.canEndTurn());
        gameManager.getTurnManager().rollDie();
        assertTrue(gameManager.canEndTurn());
    }

    @Test
    void testProduceResources() {
        final Map<ResourceType, Integer> emptyResources = new HashMap<>();
        Stream.of(ResourceType.values()).forEach(resource -> emptyResources.put(resource, 0));
        assertEquals(0,
                gameManager.getPlayers().stream().map(player -> gameManager.getResourceManager()
                        .getResources(player).values().stream().mapToInt(Integer::intValue)
                        .sum())
                        .mapToInt(Integer::intValue).sum());
        for (int i = 0; i < 19; i++) {
            for (int j = 2; j <= 12; j++) {
                gameManager.produceResources(j);
            }
        }
        assertEquals(emptyResources,
                gameManager.getResourceManager()
                        .getResources(gameManager.getResourceManager().getBank()));
        assertEquals(19 * 5,
                gameManager.getPlayers().stream().map(player -> gameManager.getResourceManager()
                        .getResources(player).values().stream().mapToInt(Integer::intValue)
                        .sum())
                        .mapToInt(Integer::intValue).sum());
    }
}
