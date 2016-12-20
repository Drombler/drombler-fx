package org.drombler.fx.core.action.impl;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.drombler.acp.core.action.spi.AbstractMenuItemRootContainer;
import org.drombler.acp.core.action.spi.MenuItemContainer;
import org.drombler.acp.core.action.spi.MenuItemSortingStrategy;
import org.drombler.acp.core.action.spi.MenuItemSupplierFactory;
import org.drombler.acp.core.action.spi.MenuMenuItemContainerFactory;
import org.drombler.acp.core.action.spi.SeparatorMenuItemFactory;

/**
 *
 * @author puce
 */
@Component
@Service
public class FXMenuMenuItemContainerFactory implements MenuMenuItemContainerFactory<MenuItem, Menu> {

    @Override
    public <F extends MenuItemSupplierFactory<MenuItem>> MenuItemContainer<MenuItem, Menu, F> createMenuMenuItemContainer(String id, Menu menu,
            MenuItemContainer<MenuItem, Menu, ?> parentContainer,
            AbstractMenuItemRootContainer<MenuItem, Menu, ?> rootContainer,
            MenuItemSortingStrategy<MenuItem, F> menuItemSortingStrategy,
            MenuMenuItemContainerFactory<MenuItem, Menu> menuMenuItemContainerFactory,
            SeparatorMenuItemFactory<? extends MenuItem> separatorMenuItemFactory) {
        return new FXMenuMenuItemContainer<>(id, menu, parentContainer, rootContainer, menuItemSortingStrategy, menuMenuItemContainerFactory, separatorMenuItemFactory);
    }

}
