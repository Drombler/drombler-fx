/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.action;

import javafx.collections.ObservableList;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

/**
 *
 * @author puce
 */
public class ContextMenuMenuItemContainer extends AbstractMenuItemContainer{
    private final ContextMenu contextMenu;

    public ContextMenuMenuItemContainer(ContextMenu contextMenu) {
        super(true);
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
