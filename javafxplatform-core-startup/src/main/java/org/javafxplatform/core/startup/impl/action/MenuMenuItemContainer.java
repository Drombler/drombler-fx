/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.startup.impl.action;

import javafx.collections.ObservableList;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

/**
 *
 * @author puce
 */
public class MenuMenuItemContainer extends AbstractMenuItemContainer {

    private final Menu menu;

    public MenuMenuItemContainer(Menu menu) {
        super(true);
        this.menu = menu;
    }

    @Override
    protected ObservableList<? super Menu> getMenus() {
        return menu.getItems();
    }

    @Override
    protected ObservableList<MenuItem> getItems() {
        return menu.getItems();
    }

}
