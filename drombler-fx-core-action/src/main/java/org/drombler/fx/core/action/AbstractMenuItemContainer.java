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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.collections.ObservableList;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import org.drombler.acp.core.action.spi.MenuItemContainer;
import org.drombler.acp.core.action.spi.MenuItemEntry;
import org.drombler.acp.core.action.spi.MenuItemSortingStrategy;
import org.drombler.acp.core.action.spi.MenuItemSupplier;
import org.drombler.acp.core.action.spi.MenuItemSupplierFactory;
import org.drombler.acp.core.action.spi.MenuItemSupplierFactoryEntry;
import org.drombler.acp.core.action.spi.SeparatorMenuItemFactory;
import org.drombler.fx.core.action.impl.MenuMenuItemContainer;

/**
 *
 * @author puce
 */
public abstract class AbstractMenuItemContainer<F extends MenuItemSupplierFactory<MenuItem>> implements MenuItemContainer<MenuItem, Menu, F> {

    private final Map<String, MenuItemContainer<MenuItem, Menu, ?>> menuContainers = new HashMap<>();
    private final String id;
    private final boolean supportingItems;
    private final List<MenuItemSupplierFactoryEntry<MenuItem, F>> xMenuItems = new ArrayList<>();
//    private final Map<String, List<PositionableMenuItemAdapter<?>>> unresolvedMenus = new HashMap<>();
    private final AbstractMenuItemContainer<?> parentContainer;
    private final MenuItemSortingStrategy<MenuItem, F> menuItemSortingStrategy;
    private final SeparatorMenuItemFactory<? extends MenuItem> separatorMenuItemFactory;

    public AbstractMenuItemContainer(String id, boolean supportingItems, AbstractMenuItemContainer<?> parentContainer,
            MenuItemSortingStrategy<MenuItem, F> menuItemSortingStrategy, SeparatorMenuItemFactory<? extends MenuItem> separatorMenuItemFactory) {
        this.id = id;
        this.supportingItems = supportingItems;
        this.parentContainer = parentContainer;
        this.menuItemSortingStrategy = menuItemSortingStrategy;
        this.separatorMenuItemFactory = separatorMenuItemFactory;
    }

    /**
     * @param id
     * @return the menuContainers
     */
    @Override
    public MenuItemContainer<MenuItem, Menu, ?> getMenuContainer(String id) {
        return menuContainers.get(id);
    }

    @Override
    public void addMenu(String id, Menu menu, F supplierFactory, MenuItemSortingStrategy<MenuItem, ?> sortingStrategy) {
        MenuMenuItemContainer<?> menuMenuItemContainer = new MenuMenuItemContainer<>(id, menu, this, getMenuItemRootContainer(), sortingStrategy, separatorMenuItemFactory);
        menuContainers.put(id, menuMenuItemContainer);

        addMenuItem(menu, supplierFactory, getMenus(), true);
        fireMenuAddedEvent(supplierFactory.createMenuItemSupplier(menu), id, menuMenuItemContainer);
    }

    private void fireMenuAddedEvent(MenuItemSupplier<? extends Menu> menuSupplier, String id, AbstractMenuItemContainer<?> menuMenuContainer) {
        getMenuItemRootContainer().fireMenuAddedEvent(menuSupplier, id, menuMenuContainer.getPath());
    }

    private List<String> getPath() {
        List<String> path = new ArrayList<>();
        AbstractMenuItemContainer currentParentContainer = parentContainer;
        while (currentParentContainer != null && currentParentContainer.id != null) {
            path.add(currentParentContainer.id);
            currentParentContainer = currentParentContainer.parentContainer;
        }
        Collections.reverse(path);
        return path;
    }

    private <T extends MenuItem> void addMenuItem(T menuItem, F supplierFactory, ObservableList<? super T> menuItemList, boolean menu) {
        MenuItemSupplierFactoryEntry<MenuItem, F> entry = new MenuItemSupplierFactoryEntry<>(supplierFactory, menuItem);
        int index = menuItemSortingStrategy.getInsertionPoint(xMenuItems, entry);

        addMenuItem(index, menuItem, supplierFactory, menuItemList, menu);

        // TODO: MenuItemEntry<MenuItem>?
        MenuItemEntry<MenuItemSupplierFactoryEntry<MenuItem, F>> separatorEntry = menuItemSortingStrategy.createSeparatorEntry(index, xMenuItems, entry, separatorMenuItemFactory);
        if (separatorEntry != null) {
            addSeparator(separatorEntry.getIndex(), separatorEntry.getMenuItem().getMenuItem(), supplierFactory);
        }

    }

    private <T extends MenuItem> void addMenuItem(final int index, final T menuItem, F supplierFactory,
            final ObservableList<? super T> menuItemList, final boolean menu) {
        MenuItemSupplierFactoryEntry<MenuItem, F> entry = new MenuItemSupplierFactoryEntry<>(supplierFactory, menuItem);
        xMenuItems.add(index, entry);
        menuItemList.add(index, menuItem);
        fireMenuItemAddedEvent(entry.getMenuItemSupplier(), menu);
    }

    private void fireMenuItemAddedEvent(MenuItemSupplier<? extends MenuItem> menuItemSupplier, boolean menu) {
        if (!menu) {
            getMenuItemRootContainer().fireMenuItemAddedEvent(menuItemSupplier, getPath());
        }
    }

    // TODO: still needed?
    private void addSeparator(int index, MenuItem separatorMenuItem, F supplierFactory) {
        if (isSupportingItems()) {
            addMenuItem(index, separatorMenuItem, supplierFactory, getItems(), false);
        }
    }

    protected abstract ObservableList<? super Menu> getMenus();

    @Override
    public void addMenuItem(MenuItem menuItem, F supplierFactory) {
        addMenuItem(menuItem, supplierFactory, getItems(), false);
    }

    protected abstract ObservableList<MenuItem> getItems();

    @Override
    public boolean isSupportingItems() {
        return supportingItems;
    }

    protected abstract AbstractMenuItemRootContainer<?> getMenuItemRootContainer();

    @Override
    public MenuItemSortingStrategy<MenuItem, F> getMenuItemSortingStrategy() {
        return menuItemSortingStrategy;
    }
}
