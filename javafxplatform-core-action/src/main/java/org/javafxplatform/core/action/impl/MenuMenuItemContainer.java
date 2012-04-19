/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.action.impl;

import javafx.collections.ObservableList;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import org.javafxplatform.core.action.AbstractMenuItemContainer;
import org.javafxplatform.core.action.AbstractMenuItemRootContainer;
import org.richclientplatform.core.action.spi.MenuItemContainer;
import org.richclientplatform.core.action.spi.MenuItemRootContainer;

/**
 *
 * @author puce
 */
public class MenuMenuItemContainer extends AbstractMenuItemContainer {

    private final Menu menu;
    private final AbstractMenuItemRootContainer rootContainer;

    public MenuMenuItemContainer(String id, Menu menu, AbstractMenuItemContainer parentContainer, AbstractMenuItemRootContainer rootContainer) {
        super(id, true, parentContainer);
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
