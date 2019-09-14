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

import javafx.collections.ObservableList;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import org.drombler.acp.core.action.PositionSortingStrategy;
import org.drombler.acp.core.action.PositionableMenuItemAdapterFactory;
import org.drombler.acp.core.action.spi.AbstractMenuItemRootContainer;
import org.drombler.acp.core.action.spi.MenuItemRootContainer;
import org.drombler.acp.core.action.spi.MenuMenuItemContainerFactory;
import org.drombler.acp.core.action.spi.SeparatorMenuItemFactory;

/**
 * A JavaFX specific implementation of {@link MenuItemRootContainer} based on the JavaFX {@link ContextMenu}.
 *
 * @author puce
 */
public class FXContextMenuMenuItemContainer extends AbstractMenuItemRootContainer<MenuItem, Menu, PositionableMenuItemAdapterFactory<MenuItem>> {

    private final ContextMenu contextMenu;

    /**
     * Creates a new instance of this class.
     *
     * @param contextMenu a context menu
     * @param menuMenuItemContainerFactory a menu menu-item container factory
     * @param separatorMenuItemFactory a separator menu-item factory
     */
    public FXContextMenuMenuItemContainer(ContextMenu contextMenu, MenuMenuItemContainerFactory<MenuItem, Menu> menuMenuItemContainerFactory,
            SeparatorMenuItemFactory<? extends MenuItem> separatorMenuItemFactory) {
        super(true, new PositionSortingStrategy(), menuMenuItemContainerFactory, separatorMenuItemFactory);
        this.contextMenu = contextMenu;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    protected ObservableList<? super Menu> getMenus() {
        return contextMenu.getItems();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    protected ObservableList<MenuItem> getItems() {
        return contextMenu.getItems();
    }
}
