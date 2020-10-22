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

import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import org.apache.commons.lang3.StringUtils;
import org.drombler.acp.core.action.spi.ToggleMenuEntryDescriptor;
import org.drombler.acp.core.action.spi.ToggleMenuItemFactory;
import org.drombler.commons.action.fx.FXToggleAction;
import org.drombler.commons.action.fx.MenuItemUtils;
import org.osgi.service.component.annotations.Component;

/**
 *
 * @author puce
 */
@Component
public class FXToggleMenuItemFactory implements ToggleMenuItemFactory<MenuItem, MenuItem, FXToggleAction> {

    private final ToggleGroupManager toggleGroupManager = new ToggleGroupManager();

    @Override
    public MenuItem createToggleMenuItem(ToggleMenuEntryDescriptor<MenuItem, MenuItem, ?> toggleMenuEntryDescriptor, FXToggleAction toggleAction, int iconSize) {
        if (StringUtils.isNotEmpty(toggleMenuEntryDescriptor.getToggleGroupId())) {
            RadioMenuItem menuItem = new RadioMenuItem();
            MenuItemUtils.configureRadioMenuItem(menuItem, toggleAction, iconSize);
            toggleGroupManager.configureToggle(menuItem, toggleMenuEntryDescriptor.getToggleGroupId());
            return menuItem;
        } else {
            CheckMenuItem menuItem = new CheckMenuItem();
            MenuItemUtils.configureCheckMenuItem(menuItem, toggleAction, iconSize);
            return menuItem;
        }
    }
}
