package it.unibo.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Application.
 */
public class App extends Application {
    /**
     * Start.
     * 
     * @param stage stage
     * @throws Exception exception
     * @see Application
     */
    @Override
    public void start(final Stage stage) throws Exception {
        final AppView appView = new AppView();

        final Scene scene = appView.getScene();

        stage.setTitle("I Coloni di Cesena");
        stage.setScene(scene);
        stage.setMaximized(true);

        stage.show();
    }

    /**
     * Program's entry point.
     * 
     * @param args
     */
    public static void run(final String... args) {
        launch(args);
    }

    /**
     * Entry point's class.
     */
    public static final class Main {
        private Main() {
        }

        /**
         * Program's entry point.
         * @param args
         */
        public static void main(final String... args) {
            App.run(args);
        }
    }
}
