/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.action.impl;

import javafx.scene.control.CheckMenuItem;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.javafxplatform.core.action.FXCheckAction;
import org.richclientplatform.core.action.spi.CheckMenuItemFactory;
import org.richclientplatform.core.action.spi.MenuEntryDescriptor;

/**
 *
 * @author puce
 */
@Component
@Service
public class FXCheckMenuItemFactory implements CheckMenuItemFactory<CheckMenuItem, FXCheckAction> {

    @Override
    public CheckMenuItem createCheckMenuItem(MenuEntryDescriptor menuEntryDescriptor, FXCheckAction action, int iconSize) {
        CheckMenuItem menuItem = new CheckMenuItem();
        MenuItemUtils.configureMenuItem(menuItem, menuEntryDescriptor, action, iconSize);
        menuItem.selectedProperty().bind(action.selectedProperty());
        return menuItem;
    }
}
