package it.unibo.view.player;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.controller.main.MainController;
import it.unibo.view.Drawable;
import it.unibo.view.board.RobberView;
import it.unibo.view.resource.ResourcesViewFactory;
import it.unibo.view.resource.TradeView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * View class for the current player. It shows his resources and some buttons.
 */
public final class CurrentPlayerView extends HBox implements Drawable {
    private final MainController controller;
    private final TradeView tradeView;
    private final RobberView robberView;
    private final ImageView roll1;
    private final ImageView roll2;

    /**
     * Constructor of CurrentPlayerView.
     * 
     * @param controller the main controller
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "The controller needs to be updated")
    public CurrentPlayerView(final MainController controller) {
        this.controller = controller;
        tradeView = new TradeView(this.controller);
        robberView = new RobberView(this.controller);
        roll1 = new ImageView();
        roll2 = new ImageView();
        roll1.setVisible(false);
        roll2.setVisible(false);
    }

    @Override
    public void draw() {
        super.getChildren().clear();
        drawResources();

        final VBox info = new VBox();

        final HBox buttonsBox = new HBox();
        buttonsBox.getChildren().add(tradeView.getTradeButton());
        buttonsBox.getChildren().add(getEndTurnButton());
        buttonsBox.getChildren().add(getRollButton());
        buttonsBox.getChildren().add(getBuyCardButton());

        final HBox dieImages = new HBox();
        dieImages.setSpacing(2);
        dieImages.getChildren().add(roll1);
        dieImages.getChildren().add(roll2);

        info.getChildren().add(new Label("Current player: " + controller.getCurrentPlayerName()));
        info.getChildren().add(buttonsBox);
        info.getChildren().add(dieImages);
        super.getChildren().add(info);
    }

    /**
     * Draw only the resources amount of the current player.
     */
    public void drawResources() {
        controller.getResourceController().getPlayerResources(controller.getCurrentPlayerName()).entrySet()
                .forEach(entry -> {
                    super.getChildren()
                            .remove(ResourcesViewFactory.getResourceLabelAmount(entry.getKey(), entry.getValue()));
                    super.getChildren()
                            .add(ResourcesViewFactory.getResourceLabelAmount(entry.getKey(), entry.getValue()));
                });
    }

    private Button getEndTurnButton() {
        final Button endTurnButton = new Button("End turn");
        endTurnButton.setOnAction(e -> {
            if (controller.canEndTurn()) {
                controller.endTurn();
                roll1.setVisible(false);
                roll2.setVisible(false);
                draw();
            }
        });
        endTurnButton.setDisable(!controller.canEndTurn());
        return endTurnButton;
    }

    private Button getRollButton() {
        final Button rollButton = new Button("Roll die");
        rollButton.setOnAction(e -> {
            if (controller.canRollDie()) {
                final var roll = controller.rollDie();
                final int width = 60, height = 60;
                roll1.setImage(new Image(ClassLoader.getSystemResourceAsStream("imgs/dice/" + roll.getLeft() + ".png"),
                        width, height, true, true));
                roll2.setImage(new Image(ClassLoader.getSystemResourceAsStream("imgs/dice/" + roll.getRight() + ".png"),
                        width, height, true, true));
                roll1.setVisible(true);
                roll2.setVisible(true);
                draw();
                if (controller.mustPlaceRobber()) {
                    robberView.evokeRobber();
                }
            }
        });
        rollButton.setDisable(!controller.canRollDie());
        return rollButton;
    }

    private Button getBuyCardButton() {
        final Button buyCardButton = new Button("Buy development card");
        buyCardButton.setOnAction(e -> {
            if (controller.canBuyCard()) {
                controller.buyCard();
            }
            draw();
        });
        if (!controller.canBuyCard()) {
            buyCardButton.setDisable(true);
        }

        return buyCardButton;
    }
}
