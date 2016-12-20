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
package org.drombler.fx.core.action;

import org.drombler.acp.core.action.spi.AbstractMenuItemRootContainer;
import javafx.collections.ObservableList;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import org.drombler.acp.core.action.spi.PositionSortingStrategy;
import org.drombler.acp.core.action.spi.PositionableMenuItemAdapterFactory;
import org.drombler.acp.core.action.spi.SeparatorMenuItemFactory;
import org.drombler.acp.core.action.spi.MenuMenuItemContainerFactory;

/**
 *
 * @author puce
 */
public class FXContextMenuMenuItemContainer extends AbstractMenuItemRootContainer<MenuItem, Menu, PositionableMenuItemAdapterFactory<MenuItem>> {

    private final ContextMenu contextMenu;

    public FXContextMenuMenuItemContainer(ContextMenu contextMenu, MenuMenuItemContainerFactory<MenuItem, Menu> menuMenuItemContainerFactory,
            SeparatorMenuItemFactory<? extends MenuItem> separatorMenuItemFactory) {
        super(true, new PositionSortingStrategy(), menuMenuItemContainerFactory, separatorMenuItemFactory);
        this.contextMenu = contextMenu;
    }

    @Override
    protected ObservableList<? super Menu> getMenus() {
        return contextMenu.getItems();
    }

    @Override
    protected ObservableList<MenuItem> getItems() {
        return contextMenu.getItems();
    }
}
