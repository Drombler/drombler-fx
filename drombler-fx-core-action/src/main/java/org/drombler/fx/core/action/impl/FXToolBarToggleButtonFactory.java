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
import org.drombler.commons.action.fx.ButtonUtils;
import org.drombler.commons.action.fx.FXToggleAction;
import org.drombler.commons.fx.scene.control.XToggleButton;

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
        XToggleButton toggleButton = new XToggleButton();
        ButtonUtils.configureToolbarToggleButton(toggleButton, action, iconSize);
        toggleGroupManager.configureToggle(toggleButton, toolBarToggleEntryDescriptor.getToggleGroupId());
        return toggleButton;
    }
}
