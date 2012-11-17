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

import javafx.scene.control.ToggleButton;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.drombler.acp.core.action.spi.ToolBarToggleButtonFactory;
import org.drombler.acp.core.action.spi.ToolBarToggleEntryDescriptor;
import org.drombler.fx.core.action.FXToggleAction;

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
