/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.action.impl;

import javafx.scene.control.MenuItem;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.javafxplatform.core.action.FXAction;
import org.richclientplatform.core.action.spi.MenuEntryDescriptor;
import org.richclientplatform.core.action.spi.MenuItemFactory;

/**
 *
 * @author puce
 */
@Component
@Service
public class FXMenuItemFactory implements MenuItemFactory<MenuItem, FXAction> {

    @Override
    public MenuItem createMenuItem(MenuEntryDescriptor menuEntryDescriptor, FXAction action, int iconSize) {
        MenuItem menuItem = new MenuItem();
        MenuItemUtils.configureMenuItem(menuItem, menuEntryDescriptor, action, iconSize);
        return menuItem;
    }
}
