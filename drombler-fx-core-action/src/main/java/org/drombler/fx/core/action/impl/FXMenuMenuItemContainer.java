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

import javafx.collections.ObservableList;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import org.drombler.acp.core.action.spi.AbstractMenuItemContainer;
import org.drombler.acp.core.action.spi.AbstractMenuItemRootContainer;
import org.drombler.acp.core.action.spi.MenuItemContainer;
import org.drombler.acp.core.action.spi.MenuItemSortingStrategy;
import org.drombler.acp.core.action.spi.MenuItemSupplierFactory;
import org.drombler.acp.core.action.spi.MenuMenuItemContainerFactory;
import org.drombler.acp.core.action.spi.SeparatorMenuItemFactory;

/**
 *
 * @param <F> the sorting strategy specific menu item supplier factory type
 * @author puce
 */
public class FXMenuMenuItemContainer<F extends MenuItemSupplierFactory<MenuItem>> extends AbstractMenuItemContainer<MenuItem, Menu, F> {

    private final Menu menu;
    private final AbstractMenuItemRootContainer<MenuItem, Menu, ?> rootContainer;

    public FXMenuMenuItemContainer(String id, Menu menu,
            MenuItemContainer<MenuItem, Menu, ?> parentContainer,
            AbstractMenuItemRootContainer<MenuItem, Menu, ?> rootContainer,
            MenuItemSortingStrategy<MenuItem, F> menuItemSortingStrategy,
            MenuMenuItemContainerFactory<MenuItem, Menu> menuMenuItemContainerFactory,
            SeparatorMenuItemFactory<? extends MenuItem> separatorMenuItemFactory) {
        super(id, true, parentContainer, menuItemSortingStrategy, menuMenuItemContainerFactory, separatorMenuItemFactory);
        this.menu = menu;
        this.rootContainer = rootContainer;
    }

    @Override
    protected ObservableList<? super Menu> getMenus() {
        return menu.getItems();
    }

    @Override
    protected ObservableList<MenuItem> getItems() {
        return menu.getItems();
    }

    @Override
    protected AbstractMenuItemRootContainer getMenuItemRootContainer() {
        return rootContainer;
    }

}
