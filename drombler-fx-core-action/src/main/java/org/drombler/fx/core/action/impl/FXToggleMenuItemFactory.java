/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drombler.fx.core.action.impl;

import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import org.apache.commons.lang.StringUtils;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.drombler.acp.core.action.spi.ToggleMenuEntryDescriptor;
import org.drombler.acp.core.action.spi.ToggleMenuItemFactory;
import org.drombler.fx.core.action.FXToggleAction;

/**
 *
 * @author puce
 */
@Component
@Service
public class FXToggleMenuItemFactory implements ToggleMenuItemFactory<MenuItem, FXToggleAction> {

    private final ToggleGroupManager toggleGroupManager = new ToggleGroupManager();

    @Override
    public MenuItem createToggleMenuItem(ToggleMenuEntryDescriptor toggleMenuEntryDescriptor, FXToggleAction action, int iconSize) {
        if (StringUtils.isNotEmpty(toggleMenuEntryDescriptor.getToggleGroupId())) {
            RadioMenuItem menuItem = new RadioMenuItem(action.getDisplayName());
            MenuItemUtils.configureMenuItem(menuItem, toggleMenuEntryDescriptor, action, iconSize);
            menuItem.selectedProperty().bindBidirectional(action.selectedProperty());
            toggleGroupManager.configureToggle(menuItem, toggleMenuEntryDescriptor.getToggleGroupId());
            return menuItem;
        } else {
            CheckMenuItem menuItem = new CheckMenuItem();
            MenuItemUtils.configureMenuItem(menuItem, toggleMenuEntryDescriptor, action, iconSize);
            menuItem.selectedProperty().bindBidirectional(action.selectedProperty());
            return menuItem;
        }
    }
}
