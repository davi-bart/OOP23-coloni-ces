package it.unibo.view;

import java.util.HashMap;
import java.util.List;
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

/**
 * RobberView.
 */
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
     * Evoke the robber.
     * Test function.
     */
    public void evokeRobber() {
        if (controller.mustPlaceRobber()) {
            controller.getPlayerNames().forEach(player -> {
                if (controller.getResourceController().shouldDiscard(player)) {
                    showDiscardStage(player);
                }
            });
        }
    }

    private void showDiscardStage(final String player) {
        final Stage stage = new Stage();
        stage.setTitle("Discard window of " + player);
        final VBox resourcesContainer = new VBox();
        final HBox discardContainer = new HBox();
        final HBox discardResourcesBox = new HBox();
        final Button confirm = new Button("Confirm");
        final Map<ResourceType, Integer> discardResources = new HashMap<>();
        List.of(ResourceType.values()).forEach(resource -> discardResources.put(resource, 0));

        final Runnable reloadConfirmButton = () -> {
            confirm.setDisable(!controller.getResourceController().canDiscard(player, discardResources));
        };

        reloadConfirmButton.run();
        Stream.of(ResourceType.values()).forEach(resource -> {
            discardResourcesBox.getChildren().add(resourceAndComboBox(resource,
                    controller.getResourceController().getPlayerResources(player).get(resource),
                    (options, oldValue, newValue) -> {
                        discardResources.put(resource, newValue);
                        reloadConfirmButton.run();
                    }));
        });

        confirm.setOnMouseClicked(e -> {
            controller.tradeWithBank(player, discardResources,
                    new HashMap<>());
            stage.close();
        });

        resourcesContainer.getChildren().add(new Label("Select card(s) to discard "
                + controller.getResourceController()
                        .getResourcesToDiscard(controller.getResourceController().getResourcesAmount(player))
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
