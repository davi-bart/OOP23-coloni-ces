package it.unibo.view;

import it.unibo.view.app.StartMenuView;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Application.
 */
public class JavaFXApp extends Application {
    /**
     * Start.
     * 
     * @param stage stage
     * @throws Exception exception
     * @see Application
     */
    @Override
    public void start(final Stage stage) throws Exception {
        final StartMenuView menu = new StartMenuView(stage);
        menu.draw();
    }

    /**
     * Program's entry point.
     * 
     * @param args command line arguments
     */
    public static void run(final String... args) {
        launch(args);
    }
}
