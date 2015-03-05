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
import org.drombler.acp.core.action.spi.PositionableMenuItemAdapter;

/**
 *
 * @author puce
 */
public abstract class AbstractMenuItemRootContainer extends AbstractMenuItemContainer implements
        MenuItemRootContainer<MenuItem, Menu> {

    private final List<MenuItemContainerListener<MenuItem, Menu>> containerListeners
            = Collections.synchronizedList(new ArrayList<>()); // TODO: synchronized needed?

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
        containerListeners.forEach(containerListener -> containerListener.menuAdded(event));
    }

    void fireMenuItemAddedEvent(PositionableMenuItemAdapter<? extends MenuItem> menuItem, List<String> path) {
        MenuItemContainerMenuItemEvent<MenuItem, Menu> event = new MenuItemContainerMenuItemEvent<>(
                getMenuItemRootContainer(), menuItem, path);
        containerListeners.stream().forEach((containerListener) -> containerListener.menuItemAdded(event));
    }

    @Override
    protected AbstractMenuItemRootContainer getMenuItemRootContainer() {
        return this;
    }
}
