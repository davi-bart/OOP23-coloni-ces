package it.unibo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.model.api.Board;
import it.unibo.model.api.BoardGenerator;
import it.unibo.model.impl.RandomBoardGenerator;

/**
 * Test for a trader istance.
 */
// CHECKSTYLE: MagicNumber OFF
public final class RandomBoardGeneratorTest {
    private BoardGenerator generator;
    private Board board;

    /**
     * Initialization.
     */
    @BeforeEach
    public void init() {
        this.generator = new RandomBoardGenerator();
        this.board = generator.generate();
    }

    /**
     * Test the random board generator.
     */
    @Test
    public void test() {
    }
}
// CHECKSTYLE: MagicNumber OFF
