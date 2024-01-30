package it.unibo.view;

import java.io.IOException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * ResourceView class.
 */
public final class ResourcesView {

    /**
     * 
     * @param resource
     * @return the image view of the needed resource
     * @throws IOException
     */
    public ImageView getResource(final String resource) throws IOException {
        final Image brick = new Image("imgs/resources/" + resource + ".png");
        final ImageView image = new ImageView(brick);
        image.setFitHeight(100);
        image.setPreserveRatio(true);
        return image;
    }
}
