package it.unibo.view;

import java.io.IOException;
import java.util.Locale;

import it.unibo.common.api.ResourceType;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


/**
 * ResourceView class.
 */
public final class ResourcesView {

    private VBox generateResource(final ResourceType resource) {
        final int defaultHeight = 100;
        final VBox resourceCard = new VBox();
        final Image resourceImage = new Image("imgs/resources/" + resource.toString().toLowerCase(Locale.US) + ".png");
        final ImageView image = new ImageView(resourceImage);

        resourceCard.setAlignment(Pos.CENTER);
        image.setFitHeight(defaultHeight);
        image.setPreserveRatio(true);
        resourceCard.getChildren().add(image);
        return resourceCard;
    }
    /**
     * @param resource
     * @return the image view of the needed resource.
     * @throws IOException
     */
    public VBox getResourceBasic(final ResourceType resource) {
        return generateResource(resource);
    }
    /**
     * @param resource
     * @param amount
     * @return the image view of the needed resource with a label representing the amount.
     * @throws IOException
     */
    public VBox getResourceLabelAmount(final ResourceType resource, final int amount) {
        final Label amountLabel = new Label();
        final VBox resourceAndAmount = new VBox(generateResource(resource));
        amountLabel.setText(String.valueOf(amount));
        resourceAndAmount.getChildren().add(amountLabel);
        return resourceAndAmount;
    }
    /**
     * @param resource
     * @return the image view of the needed resource with a combobox.
     * @throws IOException
     */
    public VBox getResourceComboBoxAmount(final ResourceType resource) {
        final ComboBox<Integer> amountBox = new ComboBox<>();
        final VBox resourceAndAmount = new VBox(generateResource(resource));

        amountBox.getItems().addAll(0, 1, 2, 3);
        amountBox.getSelectionModel().selectFirst();
        resourceAndAmount.getChildren().add(amountBox);
        return resourceAndAmount;
    }
    /**
     * 
     * @return an HBox representing all the resources (the hand of the player).
     */
    public HBox getAllResources() {
        final HBox hand = new HBox();
        for (final ResourceType resource : ResourceType.values()) {
            hand.getChildren().add(new VBox(getResourceComboBoxAmount(resource)));
        }
        return hand;
    }
}
