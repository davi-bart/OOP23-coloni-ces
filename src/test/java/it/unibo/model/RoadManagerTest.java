package it.unibo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import it.unibo.common.road.RoadDirection;
import it.unibo.common.road.RoadPositionImpl;
import it.unibo.common.tile.TilePositionImpl;
import it.unibo.model.player.Player;
import it.unibo.model.player.PlayerImpl;
import it.unibo.model.road.RoadManager;
import it.unibo.model.road.RoadManagerImpl;

class RoadManagerTest {

    final RoadManager roadManager = new RoadManagerImpl();
    final Player player1 = new PlayerImpl("first");
    final Player player2 = new PlayerImpl("second");
    final List<Player> players = List.of(player1, player2);

    @Test
    void testBuildRoad() {
        assertTrue(roadManager.getPlayerRoads(player1).isEmpty());
        assertTrue(roadManager.getPlayerRoads(player2).isEmpty());
        roadManager.buildRoad(new RoadPositionImpl(new TilePositionImpl(0, 1), RoadDirection.LEFT), player1);
        roadManager.buildRoad(new RoadPositionImpl(new TilePositionImpl(0, 2), RoadDirection.LEFT), player1);
        roadManager.buildRoad(new RoadPositionImpl(new TilePositionImpl(0, 3), RoadDirection.LEFT), player1);
        assertEquals(3, roadManager.getPlayerRoads(player1).size());
        assertTrue(roadManager.getPlayerRoads(player2).isEmpty());
        roadManager.buildRoad(new RoadPositionImpl(new TilePositionImpl(0, 1), RoadDirection.DOWNRIGHT), player1);
        roadManager.buildRoad(new RoadPositionImpl(new TilePositionImpl(1, 0), RoadDirection.RIGHT), player2);
        roadManager.buildRoad(new RoadPositionImpl(new TilePositionImpl(1, 1), RoadDirection.RIGHT), player2);
        assertEquals(4, roadManager.getPlayerRoads(player1).size());
        assertEquals(2, roadManager.getPlayerRoads(player2).size());
        players.forEach(player -> assertThrows(IllegalArgumentException.class, () -> roadManager.buildRoad(
                new RoadPositionImpl(new TilePositionImpl(1, 1), RoadDirection.UPLEFT), player)));
        players.forEach(player -> assertThrows(IllegalArgumentException.class, () -> roadManager.buildRoad(
                new RoadPositionImpl(new TilePositionImpl(0, 2), RoadDirection.LEFT), player)));
        players.forEach(player -> assertThrows(IllegalArgumentException.class, () -> roadManager.buildRoad(
                new RoadPositionImpl(new TilePositionImpl(0, 3), RoadDirection.LEFT), player)));
    }

    @Test
    void testLongestRoad() {
        assertEquals(0, roadManager.getLongestRoadLength(player1));
        roadManager.buildRoad(new RoadPositionImpl(new TilePositionImpl(0, 1), RoadDirection.DOWNLEFT), player1);
        assertEquals(1, roadManager.getLongestRoadLength(player1));
        assertThrows(IllegalArgumentException.class, () -> roadManager.buildRoad(
                new RoadPositionImpl(new TilePositionImpl(1, 0), RoadDirection.UPRIGHT), player1));
        roadManager.buildRoad(new RoadPositionImpl(new TilePositionImpl(1, 0), RoadDirection.RIGHT), player1);
        assertEquals(2, roadManager.getLongestRoadLength(player1));
        roadManager.buildRoad(new RoadPositionImpl(new TilePositionImpl(0, 0), RoadDirection.UPLEFT), player1);
        assertEquals(2, roadManager.getLongestRoadLength(player1));
        roadManager.buildRoad(new RoadPositionImpl(new TilePositionImpl(1, 0), RoadDirection.DOWNRIGHT), player1);
        assertEquals(3, roadManager.getLongestRoadLength(player1));
        roadManager.buildRoad(new RoadPositionImpl(new TilePositionImpl(3, 0), RoadDirection.DOWNLEFT), player1);
        roadManager.buildRoad(new RoadPositionImpl(new TilePositionImpl(3, 0), RoadDirection.DOWNRIGHT), player1);
        roadManager.buildRoad(new RoadPositionImpl(new TilePositionImpl(3, 1), RoadDirection.DOWNLEFT), player1);
        roadManager.buildRoad(new RoadPositionImpl(new TilePositionImpl(3, 1), RoadDirection.DOWNRIGHT), player1);
        roadManager.buildRoad(new RoadPositionImpl(new TilePositionImpl(3, 2), RoadDirection.DOWNLEFT), player1);
        assertEquals(5, roadManager.getLongestRoadLength(player1));
        roadManager.buildRoad(new RoadPositionImpl(new TilePositionImpl(4, 0), RoadDirection.DOWNLEFT), player1);
        assertEquals(5, roadManager.getLongestRoadLength(player1));
    }

    @Test
    void testLongestRoadOwner() {
        assertTrue(roadManager.getLongestRoadOwner().isEmpty());
        roadManager.buildRoad(new RoadPositionImpl(new TilePositionImpl(3, 0), RoadDirection.DOWNLEFT), player1);
        roadManager.buildRoad(new RoadPositionImpl(new TilePositionImpl(3, 0), RoadDirection.DOWNRIGHT), player1);
        roadManager.buildRoad(new RoadPositionImpl(new TilePositionImpl(3, 1), RoadDirection.DOWNLEFT), player1);
        roadManager.buildRoad(new RoadPositionImpl(new TilePositionImpl(3, 1), RoadDirection.DOWNRIGHT), player1);
        assertTrue(roadManager.getLongestRoadOwner().isEmpty());
        roadManager.buildRoad(new RoadPositionImpl(new TilePositionImpl(3, 2), RoadDirection.DOWNLEFT), player1);
        assertTrue(roadManager.getLongestRoadOwner().isPresent());
        assertEquals(player1, roadManager.getLongestRoadOwner().get());

        roadManager.buildRoad(new RoadPositionImpl(new TilePositionImpl(1, 0), RoadDirection.DOWNLEFT), player2);
        roadManager.buildRoad(new RoadPositionImpl(new TilePositionImpl(1, 0), RoadDirection.DOWNRIGHT), player2);
        roadManager.buildRoad(new RoadPositionImpl(new TilePositionImpl(1, 1), RoadDirection.DOWNLEFT), player2);
        roadManager.buildRoad(new RoadPositionImpl(new TilePositionImpl(1, 1), RoadDirection.DOWNRIGHT), player2);
        roadManager.buildRoad(new RoadPositionImpl(new TilePositionImpl(1, 2), RoadDirection.DOWNLEFT), player2);
        assertEquals(player1, roadManager.getLongestRoadOwner().get());
        roadManager.buildRoad(new RoadPositionImpl(new TilePositionImpl(1, 2), RoadDirection.DOWNRIGHT), player2);
        assertEquals(player2, roadManager.getLongestRoadOwner().get());

        roadManager.buildRoad(new RoadPositionImpl(new TilePositionImpl(3, 2), RoadDirection.DOWNRIGHT), player1);
        roadManager.buildRoad(new RoadPositionImpl(new TilePositionImpl(3, 3), RoadDirection.DOWNLEFT), player1);
        assertEquals(player1, roadManager.getLongestRoadOwner().get());
    }

}
