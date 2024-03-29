package it.unibo.view.app;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.controller.main.MainController;
import it.unibo.controller.main.MainControllerImpl;
import it.unibo.view.Sizes;

/**
 * Application.
 */
public class StartMenuView {
    private static final int PLAYERS_LIMIT = 4;
    private final Stage stage;
    private final ObservableList<String> players = FXCollections.observableArrayList();

    /**
     * Constructor of Menu.
     * 
     * @param stage the stage
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "The stage needs to be updated")
    public StartMenuView(final Stage stage) {
        this.stage = stage;

    }

    /**
     * draw the menu.
     * 
     * @throws IOException exception
     */
    public void draw() throws IOException {
        stage.setTitle("The Settlers of Cesena");
        stage.setScene(getScene());
        stage.setMaximized(true);
        stage.show();
    }

    private void addPlayer(final int size, final ObservableList<String> list, final TextField textfield,
            final Alert alert) {
        if (size < PLAYERS_LIMIT) {
            if (list.contains(textfield.getText())) {
                alert.setHeaderText("You cannot choose this name");
                alert.showAndWait();
            } else {
                if ("".equals(textfield.getText())) {
                    alert.setHeaderText("An empty text field is not a valid name");
                    alert.showAndWait();
                } else {
                    players.add(textfield.getText());
                    textfield.clear();
                }
            }
        } else {
            alert.setHeaderText("The player limit has been reached");
            alert.showAndWait();
        }
    }

    /**
     * getScene.
     * 
     * @return the scene
     */
    public Scene getScene() {
        final BorderPane root = new BorderPane();
        final VBox playBox = new VBox();
        final Button playButton = new Button("PLAY");
        final Button addButton = new Button("ADD PLAYER");
        final Scene scene = new Scene(root);
        final TextField textField = new TextField();
        final TableView<String> tableView = new TableView<>();
        final Alert popUp = new Alert(AlertType.ERROR);
        final TableColumn<String, String> playerName = new TableColumn<>("Player Name");
        final double maxTableHeight = Sizes.getHeight(140);
        final double maxTableWidth = Sizes.getWidth(300);
        final int childrenSpacing = 5;
        final double maxTextAreaHeight = Sizes.getHeight(200);
        final double maxTextAreaWidth = Sizes.getWidth(200);

        playerName.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue()));
        tableView.getColumns().add(playerName);
        tableView.setMaxSize(maxTableWidth, maxTableHeight);

        playButton.setOnMouseClicked(e -> {
            if (players.size() >= 1) {
                final MainController controller = new MainControllerImpl(players);
                controller.start();
                this.stage.close();
            } else {
                popUp.setHeaderText("You need at least one player to start the game");
                popUp.showAndWait();
            }

        });

        addButton.setOnMouseClicked(e -> {
            addPlayer(players.size(), players, textField, popUp);
        });

        textField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                addPlayer(players.size(), players, textField, popUp);
            }
        });

        final Image image = new Image(ClassLoader.getSystemResourceAsStream("imgs/menu/SettlersOfCesena.png"));
        final BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, true);
        final BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        final Background background = new Background(backgroundImage);

        tableView.setItems(players);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        textField.setPromptText("Insert player name");
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
