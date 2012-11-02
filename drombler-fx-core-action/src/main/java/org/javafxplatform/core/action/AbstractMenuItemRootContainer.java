/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import org.drombler.acp.core.action.spi.MenuItemContainerListener;
import org.drombler.acp.core.action.spi.MenuItemContainerMenuEvent;
import org.drombler.acp.core.action.spi.MenuItemContainerMenuItemEvent;
import org.drombler.acp.core.action.spi.MenuItemRootContainer;
import org.drombler.acp.core.action.spi.PositionableMenuItemAdapter;

/**
 *
 * @author puce
 */
public abstract class AbstractMenuItemRootContainer extends AbstractMenuItemContainer implements MenuItemRootContainer<MenuItem, Menu> {

    private final List<MenuItemContainerListener<MenuItem, Menu>> containerListeners = Collections.synchronizedList(
            new ArrayList<MenuItemContainerListener<MenuItem, Menu>>()); // TODO: synchronized needed?

    public AbstractMenuItemRootContainer(boolean supportingItems) {
        super(null, supportingItems, null);
    }

    @Override
    public void addMenuContainerListener(MenuItemContainerListener<MenuItem, Menu> containerListener) {
        containerListeners.add(containerListener);
    }

    @Override
    public void removeMenuContainerListener(MenuItemContainerListener<MenuItem, Menu> containerListener) {
        containerListeners.remove(containerListener);
    }

    void fireMenuAddedEvent(PositionableMenuItemAdapter<? extends Menu> menu, String id, List<String> path) {
        MenuItemContainerMenuEvent<MenuItem, Menu> event = new MenuItemContainerMenuEvent<>(getMenuItemRootContainer(),
                menu, id, path);
        for (MenuItemContainerListener<MenuItem, Menu> containerListener : containerListeners) {
            containerListener.menuAdded(event);
        }
    }

    void fireMenuItemAddedEvent(PositionableMenuItemAdapter<? extends MenuItem> menuItem, List<String> path) {
        MenuItemContainerMenuItemEvent<MenuItem, Menu> event = new MenuItemContainerMenuItemEvent<>(
                getMenuItemRootContainer(), menuItem, path);
        for (MenuItemContainerListener<MenuItem, Menu> containerListener : containerListeners) {
            containerListener.menuItemAdded(event);
        }
    }

    @Override
    protected AbstractMenuItemRootContainer getMenuItemRootContainer() {
        return this;
    }
}
