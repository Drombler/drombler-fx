#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ${package}.sample;

import java.io.IOException;
import java.io.InputStream;
import javafx.scene.image.Image;

public enum ColoredRectangle {

    RED("impl/red-rectangle24.png"),
    YELLOW("impl/yellow-rectangle24.png"),
    BLUE("impl/blue-rectangle24.png");
    
    private final Image image;

    private ColoredRectangle(String imageResourcePath) {
        try (InputStream is = ColoredRectangle.class.getResourceAsStream(imageResourcePath)) {
            this.image = new Image(is);
        } catch (IOException ie) {
            throw new IllegalArgumentException(ie);
        }
    }

    public Image getImage() {
        return image;
    }
}
