/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drombler.fx.core.action.impl;

import javafx.application.Platform;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.drombler.acp.core.action.spi.MenuEntryDescriptor;
import org.drombler.fx.core.action.FXAction;

/**
 *
 * @author puce
 */
class MenuItemUtils {

    private MenuItemUtils() {
    }

    public static void configureMenuItem(MenuItem menuItem, MenuEntryDescriptor menuEntryDescriptor, FXAction action, int iconSize) {
        System.out.println(MenuItemUtils.class.getName()+": isFxApplicationThread: "+Platform.isFxApplicationThread());
        menuItem.textProperty().bind(action.displayNameProperty());
        menuItem.setMnemonicParsing(true);
        menuItem.acceleratorProperty().bind(action.acceleratorProperty());
        menuItem.setOnAction(action);
        menuItem.disableProperty().bind(action.disabledProperty());
        Image iconImage = action.getIconImage(iconSize);
        if (iconImage != null) {
            menuItem.setGraphic(new ImageView(iconImage));
        }
    }
}
