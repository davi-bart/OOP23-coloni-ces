package it.unibo.model;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.model.api.Board;
import it.unibo.model.api.GameMapGenerator;
import it.unibo.model.impl.BeginnerGameMapGenerator;
import it.unibo.model.impl.BoardImpl;

/**
 * Test for a trader istance.
 */
// CHECKSTYLE: MagicNumber OFF
class BeginnerBoardGeneratorTest {
    private GameMapGenerator generator;
    private Board board;

    /**
     * Initialization.
     */
    @BeforeEach
    void init() {
        this.generator = new BeginnerGameMapGenerator();
        this.board = new BoardImpl(generator);
    }

    /**
     * Test the beginner board generator.
     */
    @Test
    void test() {
        assertNotNull(generator);
        assertNotNull(board);
    }
}
// CHECKSTYLE: MagicNumber ON
