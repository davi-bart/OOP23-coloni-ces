package it.unibo.view;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import it.unibo.common.api.tile.ResourceType;
import it.unibo.controller.api.MainController;
import javafx.beans.value.ChangeListener;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public final class RobberView {
    private final MainController controller;

    /**
     * RobberView.
     * 
     * @param controller
     */
    public RobberView(final MainController controller) {
        this.controller = controller;
    }

    /**
     * Get the trade button.
     * 
     * @return the trade button
     */
    public Button getRobberButton() {
        final Button robberButton = new Button("Robber");
        robberButton.setOnMouseClicked(event -> {
            controller.getPlayerNames().forEach(player -> {

                int sum = controller.getResourceController().getResources(player).entrySet().stream()
                        .mapToInt(e -> e.getValue()).sum();
                if (sum > 7) {
                    showRobberStage(player);
                }
            });
        });

        return robberButton;
    }

    private void showRobberStage(final String player) {
        final Stage stage = new Stage();
        stage.setTitle("Robber window of " + player);
        final VBox resourcesContainer = new VBox();
        final HBox discardContainer = new HBox();
        final HBox discardResourcesBox = new HBox();
        final Button confirm = new Button("Confirm");
        final Map<ResourceType, Integer> discardResources = new HashMap<>();

        Stream.of(ResourceType.values()).forEach(resource -> {
            discardResourcesBox.getChildren().add(resourceAndComboBox(resource,
                    controller.getPlayerResources(controller.getCurrentPlayer()).get(resource),
                    (options, oldValue, newValue) -> {
                        discardResources.put(resource, newValue);
                    }));
        });
        confirm.setOnMouseClicked(e -> {
            controller.getResourceController().removeResources(player, discardResources);
            stage.close();
            System.out
                    .println(player + controller.getResourceController()
                            .getOwnerResources(player));
            /*
             * TODO: chiamare la CurrentPlayerView.drawResources()
             * e la BankView.draw()
             */
        });
        resourcesContainer.getChildren().add(new Label("Select card(s) to discard "
                + controller.getResourceController().getResources(player).entrySet().stream()
                        .mapToInt(e -> e.getValue()).sum() / 2
                + " cards"));
        resourcesContainer.getChildren().add(discardResourcesBox);
        discardContainer.getChildren().add(resourcesContainer);
        discardContainer.getChildren().add(confirm);

        final Scene stageScene = new Scene(discardContainer, 500,
                300);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(stageScene);
        stage.setResizable(false);
        stage.show();
        System.out
                .println(player + controller.getResourceController()
                        .getOwnerResources(player));
    }

    private VBox resourceAndComboBox(final ResourceType resource, final int amount,
            final ChangeListener<Integer> listener) {
        final VBox resourceBox = new VBox();
        resourceBox.getChildren().add(ResourcesViewFactory.generateResource(resource));
        final ComboBox<Integer> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(IntStream.range(0, amount + 1).boxed().collect(Collectors.toList()));
        comboBox.getSelectionModel().selectFirst();
        comboBox.getSelectionModel().selectedItemProperty().addListener(listener);
        resourceBox.getChildren().add(comboBox);
        return resourceBox;
    }
}
