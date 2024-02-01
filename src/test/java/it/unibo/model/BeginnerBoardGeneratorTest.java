package it.unibo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.model.api.Board;
import it.unibo.model.api.BoardGenerator;
import it.unibo.model.impl.BeginnerBoardGenerator;

/**
 * Test for a trader istance.
 */
// CHECKSTYLE: MagicNumber OFF
public final class BeginnerBoardGeneratorTest {
    private BoardGenerator generator;
    private Board board;

    /**
     * Initialization.
     */
    @BeforeEach
    public void init() {
        this.generator = new BeginnerBoardGenerator();
        this.board = generator.generate();
    }

    /**
     * Test the beginner board generator.
     */
    @Test
    public void test() {
    }
}
// CHECKSTYLE: MagicNumber OFF
