package it.unibo.view;

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
        final AppView appView = new AppView(stage);
        appView.draw();
    }

    /**
     * Program's entry point.
     * 
     * @param args
     */
    public static void run(final String... args) {
        launch(args);
    }
}
