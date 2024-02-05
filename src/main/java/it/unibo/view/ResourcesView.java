package it.unibo.view;

import java.io.IOException;
import java.util.Locale;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import it.unibo.common.ResourceType;

/*
 *TODO: factory for Resource card: 
 *one with the label, one with the combobox, one with only the image
 */
/**
 * ResourceView class.
 */
public final class ResourcesView {

    /**
     * @param resource
     * @param amount
     * @return the image view of the needed resource
     * @throws IOException
     */
    public VBox getResource(final ResourceType resource, final int amount) throws IOException {

        final int defaultHeight = 100;
        final VBox resourceAndAmount = new VBox();
        final Label amountLabel = new Label();
        final Image resourceImage = new Image("imgs/resources/" + resource.toString().toLowerCase(Locale.US) + ".png");
        final ImageView image = new ImageView(resourceImage);

        amountLabel.setText(String.valueOf(amount));
        resourceAndAmount.setAlignment(Pos.CENTER);
        image.setFitHeight(defaultHeight);
        image.setPreserveRatio(true);
        // image.hoverProperty().addListener((ChangeListener<Boolean>) (observable,
        // oldValue, newValue) -> {
        // if (newValue) {
        // image.setFitHeight(defaultHeight + 0.5 * defaultHeight);
        // } else {
        // image.setFitHeight(defaultHeight);
        // }
        // });
        resourceAndAmount.getChildren().add(image);
        resourceAndAmount.getChildren().add(amountLabel);
        return resourceAndAmount;
    }

    public VBox getResourceNoAmount(final ResourceType resource) throws IOException {

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

    public HBox getAllResources() throws IOException {
        final HBox hand = new HBox();
        for (final ResourceType resource : ResourceType.values()) {
            hand.getChildren().add(new VBox(getResourceNoAmount(resource)));
        }
        return hand;
    }
}
