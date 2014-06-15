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
import javafx.scene.control.MenuItem;
import org.drombler.acp.core.action.spi.MenuEntryDescriptor;
import org.drombler.commons.action.fx.FXAction;

/**
 *
 * @author puce
 */
class MenuItemUtils {

    private MenuItemUtils() {
    }

    public static void configureMenuItem(MenuItem menuItem, MenuEntryDescriptor menuEntryDescriptor, FXAction action,
            int iconSize) {
        menuItem.textProperty().bind(action.displayNameProperty());
        menuItem.setMnemonicParsing(true);
        menuItem.acceleratorProperty().bind(action.acceleratorProperty());
        menuItem.setOnAction(action);
        menuItem.disableProperty().bind(action.disabledProperty());
        if (action.getGraphicFactory() != null) {
            Node graphic = action.getGraphicFactory().createGraphic(iconSize);
            if (graphic != null) {
                menuItem.setGraphic(graphic);
            }
        }
    }
}
