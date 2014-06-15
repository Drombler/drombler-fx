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

import javafx.scene.control.MenuItem;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.drombler.acp.core.action.spi.MenuItemFactory;
import org.drombler.commons.action.fx.FXAction;

/**
 *
 * @author puce
 */
@Component
@Service
public class FXMenuItemFactory implements MenuItemFactory<MenuItem, FXAction> {

    @Override
    public MenuItem createMenuItem(FXAction action, int iconSize) {
        MenuItem menuItem = new MenuItem();
        MenuItemUtils.configureMenuItem(menuItem, action, iconSize);
        return menuItem;
    }
}
