package it.unibo.view;

import it.unibo.common.card.CardType;
import it.unibo.controller.api.MainController;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * View class for the current player. It shows his resources and some buttons.
 */
public final class CurrentPlayerView extends HBox {
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
    public CurrentPlayerView(final MainController controller) {
        this.controller = controller;
        tradeView = new TradeView(this.controller);
        robberView = new RobberView(this.controller);
        roll1 = new ImageView();
        roll2 = new ImageView();
        roll1.setVisible(false);
        roll2.setVisible(false);
        draw();
    }

    /**
     * Draw the current player view.
     */
    public void draw() {
        super.getChildren().clear();
        drawResources();

        final VBox info = new VBox();

        final HBox buttonsBox = new HBox();
        buttonsBox.getChildren().add(tradeView.getTradeButton());
        buttonsBox.getChildren().add(getEndTurnButton());
        buttonsBox.getChildren().add(getRollButton());
        buttonsBox.getChildren().add(getBuyCardButton());
        buttonsBox.getChildren().add(new Label(controller.getCurrentPlayer()));

        final HBox dieImages = new HBox();
        dieImages.setSpacing(2);
        dieImages.getChildren().add(roll1);
        dieImages.getChildren().add(roll2);

        info.getChildren().add(buttonsBox);
        info.getChildren().add(dieImages);
        super.getChildren().add(info);
    }

    /**
     * Draw only the resources amount of the current player.
     */
    public void drawResources() {
        controller.getResourceController().getPlayerResources(controller.getCurrentPlayer()).entrySet()
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
                roll1.setImage(new Image("imgs/dice/" + roll.getLeft() + ".png", 60, 60, true, true));
                roll2.setImage(new Image("imgs/dice/" + roll.getRight() + ".png", 60, 60, true, true));
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
        final Button buyCardButton = new Button("Buy developement card");
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
