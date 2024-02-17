package it.unibo.view;

import javafx.stage.Screen;

/**
 * Utility class used for scaling sizes.
 */
public final class Sizes {
    private static final double DEFAULT_HEIGHT = 1080.0;
    private static final double DEFAULT_WIDTH = 1920.0;
    /**
     * Screen width.
     */
    public static final double SCREEN_WIDTH = Screen.getPrimary().getBounds().getWidth();
    /**
     * Screen height.
     */
    public static final double SCREEN_HEIGHT = Screen.getPrimary().getBounds().getHeight();
    /**
     * Minimum screen width.
     */
    public static final double MIN_SCREEN_WIDTH = SCREEN_HEIGHT * 16 / 9 * 0.6;
    /**
     * Minimum screen height.
     */
    public static final double MIN_SCREEN_HEIGHT = SCREEN_HEIGHT * 0.81;

    private Sizes() {
    }

    /**
     * @param height height
     * @return the scaled height
     */
    public static double getHeight(final double height) {
        return height * SCREEN_HEIGHT / DEFAULT_HEIGHT;
    }

    /**
     * @param width width
     * @return the scaled width
     */
    public static double getWidth(final double width) {
        return width * SCREEN_WIDTH / DEFAULT_WIDTH;
    }

}
