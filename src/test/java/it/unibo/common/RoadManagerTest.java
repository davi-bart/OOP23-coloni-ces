package it.unibo.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import it.unibo.common.api.road.RoadDirection;
import it.unibo.common.impl.road.RoadPositionImpl;
import it.unibo.common.impl.tile.TilePositionImpl;
import it.unibo.model.api.Player;
import it.unibo.model.api.RoadManager;
import it.unibo.model.impl.PlayerImpl;
import it.unibo.model.impl.RoadManagerImpl;

class RoadManagerTest {

    @Test
    void testLongestRoad() {
        final Player player1 = new PlayerImpl("Matthew");

        final RoadManager roadManager = new RoadManagerImpl();
        assertEquals(0, roadManager.getLongestRoadLength(player1));
        roadManager.buildRoad(new RoadPositionImpl(new TilePositionImpl(0, 1), RoadDirection.DOWNLEFT), player1);
        assertEquals(1, roadManager.getLongestRoadLength(player1));
        assertThrows(IllegalArgumentException.class, () -> roadManager.buildRoad(
                new RoadPositionImpl(new TilePositionImpl(1, 0), RoadDirection.UPRIGHT), player1));
        roadManager.buildRoad(new RoadPositionImpl(new TilePositionImpl(1, 0), RoadDirection.RIGHT), player1);
        assertEquals(2, roadManager.getLongestRoadLength(player1));
        roadManager.buildRoad(new RoadPositionImpl(new TilePositionImpl(0, 0), RoadDirection.UPLEFT), player1);
        assertEquals(2, roadManager.getLongestRoadLength(player1));
    }

}
