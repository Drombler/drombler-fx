/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drombler.fx.core.action.impl;

import javafx.scene.control.MenuItem;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.drombler.acp.core.action.spi.MenuEntryDescriptor;
import org.drombler.acp.core.action.spi.MenuItemFactory;
import org.drombler.fx.core.action.FXAction;

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
