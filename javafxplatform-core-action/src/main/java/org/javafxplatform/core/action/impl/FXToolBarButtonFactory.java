/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.action.impl;

import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.javafxplatform.core.action.FXAction;
import org.richclientplatform.core.action.spi.ToolBarButtonFactory;
import org.richclientplatform.core.action.spi.ToolBarEntryDescriptor;

/**
 *
 * @author puce
 */
@Component
@Service
public class FXToolBarButtonFactory implements ToolBarButtonFactory<Button, FXAction> {
    
    @Override
    public Button createToolBarButton(ToolBarEntryDescriptor toolBarEntryDescriptor, FXAction action, int iconSize) {
        Button button = new Button();
//        button.getStyleClass().add("tool-bar-overflow-button");
        button.setStyle("-fx-padding: 0.416667em 0.416667em 0.416667em 0.416667em; -fx-content-display: GRAPHIC_ONLY; -fx-background-color: transparent"); /* 5 5 5 5 */
        button.setFocusTraversable(false);

//        button.setMnemonicParsing(true);
//        button.acceleratorProperty().bind(action.acceleratorProperty());
        button.setOnAction(action);
        button.disableProperty().bind(action.disabledProperty());
        Image iconImage = action.getIconImage(iconSize);
        if (iconImage != null) {
            button.setGraphic(new ImageView(iconImage));
        } else {
            button.textProperty().bind(action.displayNameProperty());
        }
//        button.setTooltip(new Tooltip(action.getDisplayName() + " (" + action.getAccelerator() + ")"));
        return button;
    }
}
