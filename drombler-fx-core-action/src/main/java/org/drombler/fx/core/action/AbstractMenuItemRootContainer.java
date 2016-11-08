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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import org.drombler.acp.core.action.spi.MenuItemContainerListener;
import org.drombler.acp.core.action.spi.MenuItemContainerMenuEvent;
import org.drombler.acp.core.action.spi.MenuItemContainerMenuItemEvent;
import org.drombler.acp.core.action.spi.MenuItemRootContainer;
import org.drombler.acp.core.action.spi.MenuItemSortingStrategy;
import org.drombler.acp.core.action.spi.MenuItemSupplier;
import org.drombler.acp.core.action.spi.MenuItemSupplierFactory;
import org.drombler.acp.core.action.spi.SeparatorMenuItemFactory;

/**
 *
 * @author puce
 */
public abstract class AbstractMenuItemRootContainer<F extends MenuItemSupplierFactory<MenuItem>> extends AbstractMenuItemContainer<F> implements
        MenuItemRootContainer<MenuItem, Menu, F> {

    private final List<MenuItemContainerListener<MenuItem, Menu>> containerListeners
            = Collections.synchronizedList(new ArrayList<>()); // TODO: synchronized needed?

    public AbstractMenuItemRootContainer(boolean supportingItems, MenuItemSortingStrategy menuItemSortingStrategy, SeparatorMenuItemFactory<? extends MenuItem> separatorMenuItemFactory) {
        super(null, supportingItems, null, menuItemSortingStrategy, separatorMenuItemFactory);
    }

    @Override
    public void addMenuContainerListener(MenuItemContainerListener<MenuItem, Menu> containerListener) {
        containerListeners.add(containerListener);
    }

    @Override
    public void removeMenuContainerListener(MenuItemContainerListener<MenuItem, Menu> containerListener) {
        containerListeners.remove(containerListener);
    }

    void fireMenuAddedEvent(MenuItemSupplier<? extends Menu> menuSupplier, String id, List<String> path) {
        MenuItemContainerMenuEvent<MenuItem, Menu> event = new MenuItemContainerMenuEvent<>(getMenuItemRootContainer(),
                menuSupplier, id, path);
        containerListeners.forEach(containerListener -> containerListener.menuAdded(event));
    }

    void fireMenuItemAddedEvent(MenuItemSupplier<? extends MenuItem> menuItemSupplier, List<String> path) {
        MenuItemContainerMenuItemEvent<MenuItem, Menu> event = new MenuItemContainerMenuItemEvent<>(
                getMenuItemRootContainer(), menuItemSupplier, path);
        containerListeners.stream().forEach((containerListener) -> containerListener.menuItemAdded(event));
    }

    @Override
    protected AbstractMenuItemRootContainer<F> getMenuItemRootContainer() {
        return this;
    }
}
