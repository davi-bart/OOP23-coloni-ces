package it.unibo.controller.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import it.unibo.common.api.RoadPosition;
import it.unibo.common.api.TerrainType;
import it.unibo.common.api.TileCoordinates;
import it.unibo.controller.api.BoardController;
import it.unibo.model.api.Board;
import it.unibo.model.api.Player;

/**
 * Board controller implementation.
 */
public final class BoardControllerImpl implements BoardController {
    private final Board board;

    /**
     * Constructor of BoardControllerImpl.
     * 
     * @param board the board to start with
     */
    public BoardControllerImpl(final Board board) {
        this.board = board;
    }

    @Override
    public List<TileCoordinates> getTilePositions() {
        return this.board.getTilePositions();
    }

    @Override
    public int getTileNumber(final TileCoordinates pos) {
        return this.board.getTileNumber(pos);
    }

    @Override
    public TerrainType getTileTerrainType(final TileCoordinates pos) {
        return this.board.getTileTerrainType(pos);
    }

    @Override
    public Set<RoadPosition> getPlayerRoadPositions(Player player) {
        return this.board.getPlayerRoads(player).stream().map(r -> r.getPosition()).collect(Collectors.toSet());
    }

}
