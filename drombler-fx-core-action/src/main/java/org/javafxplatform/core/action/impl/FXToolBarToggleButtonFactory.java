/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.action.impl;

import javafx.scene.control.ToggleButton;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.javafxplatform.core.action.FXToggleAction;
import org.richclientplatform.core.action.spi.ToolBarToggleButtonFactory;
import org.richclientplatform.core.action.spi.ToolBarToggleEntryDescriptor;

/**
 *
 * @author puce
 */
@Component
@Service
public class FXToolBarToggleButtonFactory implements ToolBarToggleButtonFactory<ToggleButton, FXToggleAction> {

    private final ToggleGroupManager toggleGroupManager = new ToggleGroupManager();

    @Override
    public ToggleButton createToolBarToggleButton(ToolBarToggleEntryDescriptor toolBarToggleEntryDescriptor, FXToggleAction action, int iconSize) {
        ToggleButton toggleButton = new ToggleButton() {

            @Override
            public void fire() {
                if (getToggleGroup() == null || !isSelected()) {
                    super.fire();
                }
            }
        };
        ButtonUtils.configureButton(toggleButton, toolBarToggleEntryDescriptor, action, iconSize);
        toggleButton.selectedProperty().bindBidirectional(action.selectedProperty());
        toggleGroupManager.configureToggle(toggleButton, toolBarToggleEntryDescriptor.getToggleGroupId());
        return toggleButton;
    }
}
