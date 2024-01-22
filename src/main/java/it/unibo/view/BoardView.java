package it.unibo.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

public class BoardView {
    public StackPane getBoard() throws Exception {
        final StackPane pane = FXMLLoader.load(ClassLoader.getSystemResource("layouts/board.fxml"));
        // add the exagons and properties/road to the board
        pane.getChildren().add(0, new Button("Hello World!"));
        return pane;
    }
}
