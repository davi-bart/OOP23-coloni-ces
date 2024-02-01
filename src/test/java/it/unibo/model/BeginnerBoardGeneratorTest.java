package it.unibo.model;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.model.api.Board;
import it.unibo.model.api.BoardGenerator;
import it.unibo.model.impl.BeginnerBoardGenerator;

/**
 * Test for a trader istance.
 */
// CHECKSTYLE: MagicNumber OFF
class BeginnerBoardGeneratorTest {
    private BoardGenerator generator;
    private Board board;

    /**
     * Initialization.
     */
    @BeforeEach
    void init() {
        this.generator = new BeginnerBoardGenerator();
        this.board = generator.generate();
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
