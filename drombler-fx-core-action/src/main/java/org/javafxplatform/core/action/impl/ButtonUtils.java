/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.action.impl;

import javafx.scene.control.ButtonBase;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.drombler.acp.core.action.spi.ToolBarEntryDescriptor;
import org.javafxplatform.core.action.FXAction;

/**
 *
 * @author puce
 */
class ButtonUtils {

    private ButtonUtils() {
    }

    public static void configureButton(ButtonBase button, ToolBarEntryDescriptor toolBarEntryDescriptor, FXAction action, int iconSize) {



        //        button.getStyleClass().add("tool-bar-overflow-button");
//        button.setStyle(
//                "-fx-padding: 0.416667em 0.416667em 0.416667em 0.416667em; -fx-content-display: GRAPHIC_ONLY; -fx-background-color: transparent"); /*
//         * 5 5 5 5
//         */
        button.setFocusTraversable(false);

//        button.setMnemonicParsing(true);
//        button.acceleratorProperty().bind(action.acceleratorProperty());
        button.setOnAction(action);
        button.disableProperty().bind(action.disabledProperty());
        Image iconImage = action.getIconImage(iconSize);
        if (iconImage != null) {
            button.setGraphic(new ImageView(iconImage));
        } else {
            button.textProperty().bind(action.displayNameProperty()); // TODO: ok? -fx-content-display: GRAPHIC_ONLY ? 
        }
        button.setTooltip(new Tooltip(action.getDisplayName().replaceAll("_", "")));// + " (" + action.getAccelerator() + ")"));
    }
}
