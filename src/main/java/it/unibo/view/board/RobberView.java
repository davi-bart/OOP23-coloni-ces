package it.unibo.view.board;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.common.tile.ResourceType;
import it.unibo.controller.main.MainController;
import it.unibo.view.Sizes;
import it.unibo.view.resource.ResourcesViewFactory;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
     * @param controller the main controller
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "The controller needs to be updated")
    public RobberView(final MainController controller) {
        this.controller = controller;
    }

    /**
     * Evoke the robber.
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

    /**
     * Shows the stage with resources to discard.
     * 
     * @param player player
     */
    private void showDiscardStage(final String player) {
        final Stage stage = new Stage();
        stage.setTitle("Discard window of " + player);
        final VBox resourcesContainer = new VBox();
        final HBox discardContainer = new HBox();
        final HBox discardResourcesBox = new HBox();
        final Button confirm = new Button("Confirm");
        final Map<ResourceType, Integer> discardResources = new HashMap<>();

        final Runnable reloadConfirmButton = () -> {
            confirm.setDisable(!controller.getResourceController().canDiscard(player, discardResources));
        };

        reloadConfirmButton.run();
        Stream.of(ResourceType.values()).forEach(resource -> {
            discardResourcesBox.getChildren().add(ResourcesViewFactory.resourceAndComboBox(resource,
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
                + controller.getResourceController().getResourcesToDiscard(player)
                + " cards"));
        resourcesContainer.getChildren().add(discardResourcesBox);
        discardContainer.getChildren().add(resourcesContainer);
        discardContainer.getChildren().add(confirm);

        final Scene stageScene = new Scene(discardContainer, Sizes.getWidth(500), Sizes.getHeight(300));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(stageScene);
        stage.setResizable(false);
        stage.show();
    }
}
