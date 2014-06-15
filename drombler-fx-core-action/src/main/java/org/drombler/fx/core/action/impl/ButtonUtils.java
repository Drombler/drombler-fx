/*
 *         COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Notice
 *
 * The contents of this file are subject to the COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL)
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.opensource.org/licenses/cddl1.txt
 *
 * The Original Code is Drombler.org. The Initial Developer of the
 * Original Code is Florian Brunner (Sourceforge.net user: puce).
 * Copyright 2012 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.fx.core.action.impl;

import javafx.scene.Node;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Tooltip;
import org.drombler.acp.core.action.spi.ToolBarEntryDescriptor;
import org.drombler.commons.action.fx.FXAction;

/**
 *
 * @author puce
 */
class ButtonUtils {

    private ButtonUtils() {
    }

    public static void configureButton(ButtonBase button, ToolBarEntryDescriptor toolBarEntryDescriptor, FXAction action,
            int iconSize) {



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
        if (action.getGraphicFactory() != null) {
            Node graphic = action.getGraphicFactory().createGraphic(iconSize);
            if (graphic != null) {
                button.setGraphic(graphic);
            }
        } else {
            button.textProperty().bind(action.displayNameProperty()); // TODO: ok? -fx-content-display: GRAPHIC_ONLY ? 
        }
        button.setTooltip(new Tooltip(action.getDisplayName().replaceAll("_", "")));// + " (" + action.getAccelerator() + ")"));
    }
}
