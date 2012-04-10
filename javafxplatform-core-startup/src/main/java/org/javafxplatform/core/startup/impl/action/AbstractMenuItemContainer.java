/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.startup.impl.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import org.richclientplatform.core.action.processing.MenuItemContainer;
import org.richclientplatform.core.action.processing.PositionableMenuItemAdapter;
import org.richclientplatform.core.util.Positionables;

/**
 *
 * @author puce
 */
public abstract class AbstractMenuItemContainer implements MenuItemContainer<Menu, MenuItem> {

    private static final int SEPARATOR_STEPS = 1000;
    private final Map<String, MenuItemContainer<Menu, MenuItem>> menuContainers = new HashMap<>();
    private final boolean supportingItems;
    private final List<PositionableMenuItemAdapter<?>> xMenuItems = new ArrayList<>();
    private final Map<String, List<PositionableMenuItemAdapter<?>>> unresolvedMenus = new HashMap<>();

    public AbstractMenuItemContainer(boolean supportingItems) {
        this.supportingItems = supportingItems;
    }

    /**
     * @return the menuContainers
     */
    @Override
    public MenuItemContainer<Menu, MenuItem> getMenuContainer(String id) {
        return menuContainers.get(id);
    }

    @Override
    public void addMenu(String id, PositionableMenuItemAdapter<? extends Menu> menu) {
        MenuMenuItemContainer menuMenuContainer = new MenuMenuItemContainer(menu.getMenuItem());
        menuContainers.put(id, menuMenuContainer);

        addMenuItem(menu, getMenus());
    }

    private <T extends MenuItem> void addMenuItem(PositionableMenuItemAdapter<? extends T> menuItemAdapter, ObservableList<? super T> menuItemList) {
        int index = Positionables.getInsertionPoint(xMenuItems, menuItemAdapter);

        addMenuItem(index, menuItemAdapter, menuItemList);

        if (index < xMenuItems.size() - 1
                && (xMenuItems.get(index + 1).getPosition() - menuItemAdapter.getPosition()) >= SEPARATOR_STEPS
                && !xMenuItems.get(index + 1).isSeparator()) {
            addSeparator(index + 1, xMenuItems.get(index + 1).getPosition());
        }

        if (index > 0
                && (menuItemAdapter.getPosition() - xMenuItems.get(index - 1).getPosition()) >= SEPARATOR_STEPS
                && !xMenuItems.get(index - 1).isSeparator()) {
            addSeparator(index, menuItemAdapter.getPosition());
        }
    }

    
    

    private <T extends MenuItem> void addMenuItem(final int index, final PositionableMenuItemAdapter<? extends T> menuItemWrapper, final ObservableList<? super T> menuItemList) {
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
            PositionableMenuItemAdapter<SeparatorMenuItem> xMenuItemWrapper = PositionableMenuItemAdapter.wrapSeparator(
                    new SeparatorMenuItem(),
                    position / SEPARATOR_STEPS * SEPARATOR_STEPS);
            addMenuItem(index, xMenuItemWrapper, getItems());
        }
    }

    protected abstract ObservableList<? super Menu> getMenus();

    @Override
    public void addMenuItem(PositionableMenuItemAdapter<? extends MenuItem> menuItem) {
        addMenuItem(menuItem, getItems());
    }

    protected abstract ObservableList<MenuItem> getItems();

    @Override
    public boolean isSupportingItems() {
        return supportingItems;
    }
}
