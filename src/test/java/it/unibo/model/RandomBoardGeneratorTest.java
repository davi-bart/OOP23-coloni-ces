package it.unibo.model;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.model.api.Board;
import it.unibo.model.api.BoardGenerator;
import it.unibo.model.impl.RandomBoardGenerator;

/**
 * Test for a trader istance.
 */
// CHECKSTYLE: MagicNumber OFF
class RandomBoardGeneratorTest {
    private BoardGenerator generator;
    private Board board;

    /**
     * Initialization.
     */
    @BeforeEach
    void init() {
        this.generator = new RandomBoardGenerator();
        this.board = generator.generate();
    }

    /**
     * Test the random board generator.
     */
    @Test
    void test() {
        assertNotNull(generator);
        assertNotNull(board);
    }
}
// CHECKSTYLE: MagicNumber ON
