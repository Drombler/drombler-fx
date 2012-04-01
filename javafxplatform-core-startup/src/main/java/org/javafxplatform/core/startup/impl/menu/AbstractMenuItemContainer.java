/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.startup.impl.menu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

/**
 *
 * @author puce
 */
public abstract class AbstractMenuItemContainer implements MenuItemContainer {

    private static final int SEPARATOR_STEPS = 1000;
    private final Map<String, MenuItemContainer> menuContainers = new HashMap<>();
    private final boolean supportingItems;
    private final List<MenuItemWrapper<?>> xMenuItems = new ArrayList<>();
    private final Map<String, List<MenuItemWrapper<?>>> unresolvedMenus = new HashMap<>();

    public AbstractMenuItemContainer(boolean supportingItems) {
        this.supportingItems = supportingItems;
    }

    /**
     * @return the menuContainers
     */
    @Override
    public MenuItemContainer getMenuContainer(String id) {
        return menuContainers.get(id);
    }

    @Override
    public void addMenu(String id, MenuItemWrapper<? extends Menu> menu) {
        MenuMenuItemContainer menuMenuContainer = new MenuMenuItemContainer(menu.getMenuItem());
        menuContainers.put(id, menuMenuContainer);

        addMenuItem(menu, getMenus());
    }

    private <T extends MenuItem> void addMenuItem(MenuItemWrapper<? extends T> menuItemWrapper, ObservableList<? super T> menuItemList) {
        int index = getInsertionPoint(menuItemWrapper);

        addMenuItem(index, menuItemWrapper, menuItemList);

        if (index < xMenuItems.size() - 1
                && (xMenuItems.get(index + 1).getPosition() - menuItemWrapper.getPosition()) >= SEPARATOR_STEPS
                && !xMenuItems.get(index + 1).isSeparator()) {
            addSeparator(index + 1, xMenuItems.get(index + 1).getPosition());
        }

        if (index > 0
                && (menuItemWrapper.getPosition() - xMenuItems.get(index - 1).getPosition()) >= SEPARATOR_STEPS
                && !xMenuItems.get(index - 1).isSeparator()) {
            addSeparator(index, menuItemWrapper.getPosition());
        }
    }

    private int getInsertionPoint(MenuItemWrapper<? extends MenuItem> menuItemWrapper) {
        int index = Collections.binarySearch(xMenuItems, menuItemWrapper);
        if (index < 0) {
            index = -index - 1;
        } else {
            for (MenuItemWrapper<?> item : xMenuItems.subList(index, xMenuItems.size())) {
                if (item.getPosition() == menuItemWrapper.getPosition()) {
                    index++;
                } else {
                    break;
                }
            }
        }
        return index;
    }

    private <T extends MenuItem> void addMenuItem(final int index, final MenuItemWrapper<? extends T> menuItemWrapper, final ObservableList<? super T> menuItemList) {
        xMenuItems.add(index, menuItemWrapper);
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                menuItemList.add(index, menuItemWrapper.getMenuItem());
            }
        });
    }

    private void addSeparator(int index, int position) {
        if (isSupportingItems()) {
            MenuItemWrapper<SeparatorMenuItem> xMenuItemWrapper = MenuItemWrapper.wrapSeparator(
                    new SeparatorMenuItem(),
                    position / SEPARATOR_STEPS * SEPARATOR_STEPS);
            addMenuItem(index, xMenuItemWrapper, getItems());
        }
    }

    protected abstract ObservableList<? super Menu> getMenus();

    @Override
    public void addMenuItem(MenuItemWrapper<? extends MenuItem> menuItem) {
        addMenuItem(menuItem, getItems());
    }

    protected abstract ObservableList<MenuItem> getItems();

    @Override
    public boolean isSupportingItems() {
        return supportingItems;
    }
}
