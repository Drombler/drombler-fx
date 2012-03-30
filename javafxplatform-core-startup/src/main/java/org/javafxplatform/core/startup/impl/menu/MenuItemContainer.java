/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.startup.impl.menu;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

/**
 *
 * @author puce
 */
public interface MenuItemContainer {

    void addMenu(String id, MenuItemWrapper<? extends Menu> menu);

    void addMenuItem(MenuItemWrapper<? extends MenuItem> menuItem);

    /**
     * @return the menuContainers
     */
    MenuItemContainer getMenuContainer(String id);
    
    boolean isSupportingItems();
    
}
