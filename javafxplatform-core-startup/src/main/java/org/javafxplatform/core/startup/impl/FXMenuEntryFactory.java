/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.startup.impl;

import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.javafxplatform.core.startup.FXAction;
import org.richclientplatform.core.action.processing.MenuEntryDescriptor;
import org.richclientplatform.core.action.processing.MenuItemFactory;

/**
 *
 * @author puce
 */
@Component
@Service
public class FXMenuEntryFactory implements MenuItemFactory<MenuItem, FXAction> {

    @Override
    public MenuItem createMenuItem(MenuEntryDescriptor menuEntryDescriptor, FXAction action, int iconSize) {
        final MenuItem menuItem = new MenuItem(action.getDisplayName());
        menuItem.textProperty().bind(action.displayNameProperty());
        menuItem.setMnemonicParsing(true);
        menuItem.acceleratorProperty().bind(action.acceleratorProperty());
        menuItem.setOnAction(action);
        menuItem.disableProperty().bind(action.disabledProperty());
        Image iconImage = action.getIconImage(iconSize);
        if (iconImage != null) {
            menuItem.setGraphic(new ImageView(iconImage));
        }
        return menuItem;
    }
}
