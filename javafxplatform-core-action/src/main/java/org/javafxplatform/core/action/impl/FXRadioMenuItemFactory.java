/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.action.impl;

import javafx.scene.control.RadioMenuItem;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.javafxplatform.core.action.FXToggleAction;
import org.richclientplatform.core.action.spi.RadioMenuItemFactory;
import org.richclientplatform.core.action.spi.RadioMenuEntryDescriptor;

/**
 *
 * @author puce
 */
@Component
@Service
public class FXRadioMenuItemFactory implements RadioMenuItemFactory<RadioMenuItem, FXToggleAction> {

    private final ToggleGroupManager toggleGroupManager = new ToggleGroupManager();

    @Override
    public RadioMenuItem createRadioMenuItem(RadioMenuEntryDescriptor radioMenuEntryDescriptor, FXToggleAction action, int iconSize) {
        RadioMenuItem menuItem = new RadioMenuItem(action.getDisplayName());
        MenuItemUtils.configureMenuItem(menuItem, radioMenuEntryDescriptor, action, iconSize);
        menuItem.selectedProperty().bindBidirectional(action.selectedProperty());
        toggleGroupManager.configureToggle(menuItem, radioMenuEntryDescriptor.getToggleGroupId());
        return menuItem;
    }
}
