/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.startup.impl.menu;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

/**
 *
 * @author puce
 */
public class MenuItemWrapper<T extends MenuItem> implements Comparable<MenuItemWrapper<T>> {

    public static MenuItemWrapper<SeparatorMenuItem> wrapSeparator(SeparatorMenuItem separatorMenuItem, int position) {
        return new MenuItemWrapper<>(separatorMenuItem, position, true);
    }

    public static <T extends MenuItem> MenuItemWrapper<T> wrapMenuItem(T menuItem, int position) {
        return new MenuItemWrapper<>(menuItem, position, false);
    }
    private final T menuItem;
    private final int position;
    private final boolean separator;

    private MenuItemWrapper(T menuItem, int position, boolean separator) {
        this.menuItem = menuItem;
        this.position = position;
        this.separator = separator;
    }

    public T getMenuItem() {
        return menuItem;
    }

    public int getPosition() {
        return position;
    }

    public boolean isSeparator() {
        return separator;
    }

    @Override
    public int compareTo(MenuItemWrapper<T> o) {
        if (getPosition() < o.getPosition()) {
            return -1;
        } else if (getPosition() > o.getPosition()) {
            return 1;
        } else {
            return 0;
        }
    }
}
