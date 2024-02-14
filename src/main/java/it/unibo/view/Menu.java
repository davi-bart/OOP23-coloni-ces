package it.unibo.view;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Application.
 */
public class Menu {
    private final Stage stage;
    private final ObservableList<String> players = FXCollections.observableArrayList();

    /**
     * Constructor of Menu.
     * 
     * @param stage the stage
     */
    public Menu(final Stage stage) {
        this.stage = stage;

    }

    /**
     * draw the menu.
     * 
     * @throws IOException exception
     */
    public void draw() throws IOException {
        stage.setTitle("I Coloni di Cesena");
        stage.setScene(getScene());
        stage.setMaximized(true);
        stage.show();
    }

    /**
     * getScene.
     * 
     * @throws IOException exception
     * @return the scene
     */
    public Scene getScene() throws IOException {
        final BorderPane root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/main.fxml"));
        final VBox playBox = new VBox();
        final Button playButton = new Button("PLAY");
        final Button addButton = new Button("ADD PLAYER");
        final Scene scene = new Scene(root);
        final TextField textField = new TextField();
        final TableView<String> tableView = new TableView<String>();
        final Alert popUp = new Alert(AlertType.ERROR);
        final TableColumn<String, String> playerName = new TableColumn<String, String>("Player Name");
        final int maxTableHeight = 140;
        final int maxTableWidth = 300;
        final int minTableHeight = 0;
        final int minTableWidth = 300;
        final int childrenSpacing = 5;
        final int maxTextAreaHeight = 200;
        final int maxTextAreaWidth = 200;


        playerName.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue()));
        tableView.getColumns().add(playerName);
        tableView.setMaxSize(maxTableWidth, maxTableHeight);
        tableView.setMinSize(minTableWidth, minTableHeight );
        playButton.setOnMouseClicked(e -> {
            if (players.size() >= 1) {
                stage.close();
                new AppViewImpl(stage, players).draw();
            } else {
                popUp.setHeaderText("You need atleast one player to start the game");
                popUp.showAndWait();
            }

        });

        addButton.setOnMouseClicked(e -> {
            if (players.size() < 4) {
                if (players.contains(textField.getText())) {
                    popUp.setHeaderText("The chosen name is already taken");
                    popUp.showAndWait();
                } else {
                    players.add(textField.getText());
                    textField.clear();
                }
            } else {
                popUp.setHeaderText("The player limit has been reached");
                popUp.showAndWait();
            }

        });

        Image image = new Image("imgs/menu/SettlersOfCesena.png");
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, true);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);

        tableView.setItems(players);
        playerName.prefWidthProperty().bind(tableView.widthProperty());
        textField.setPromptText("Insert player names");
        textField.setMaxSize(maxTextAreaWidth, maxTextAreaHeight);
        playBox.getChildren().addAll(textField, addButton, tableView, playButton);
        playBox.setSpacing(childrenSpacing);
        tableView.setPlaceholder(new Label(""));

        playBox.setAlignment(Pos.CENTER);
        root.setBackground(background);
        root.setCenter(playBox);
        return scene;
    }

}
