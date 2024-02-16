package it.unibo.view;

import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import it.unibo.common.tile.ResourceType;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 * Utility class used for generating view objects of resources.
 */
public final class ResourcesViewFactory {

    private ResourcesViewFactory() {
    }

    /**
     * @param resource
     * @return the image view of the needed resource.
     */
    public static VBox generateResource(final ResourceType resource) {
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
     * @param amount
     * @return the image view of the needed resource with a label representing the
     *         amount.
     */
    public static VBox getResourceLabelAmount(final ResourceType resource, final int amount) {
        final Label amountLabel = new Label();
        final VBox resourceAndAmount = new VBox(generateResource(resource));
        amountLabel.setText(String.valueOf(amount));
        amountLabel.setMaxWidth(Double.MAX_VALUE);
        amountLabel.setAlignment(Pos.CENTER);
        resourceAndAmount.getChildren().add(amountLabel);
        return resourceAndAmount;
    }

    /**
     * @param resource resource type.
     * @param amount   amount of resource.
     * @param listener action to perform on change.
     * @return the image view of the needed resource with a combobox representing
     *         the amount.
     */
    public static VBox resourceAndComboBox(final ResourceType resource, final int amount,
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

    /**
     * 
     * @param text text to put on the header.
     * @return an alert with the text on the header.
     */
    public static Alert getAlert(final String text) {
        final Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setHeaderText(text);
        return alert;
    }
}
