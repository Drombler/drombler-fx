package org.drombler.fx.core.action.impl;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import org.drombler.acp.core.action.MenuItemSortingStrategy;
import org.drombler.acp.core.action.MenuItemSupplierFactory;
import org.drombler.acp.core.action.spi.AbstractMenuItemRootContainer;
import org.drombler.acp.core.action.spi.MenuItemContainer;
import org.drombler.acp.core.action.spi.MenuMenuItemContainerFactory;
import org.drombler.acp.core.action.spi.SeparatorMenuItemFactory;
import org.osgi.service.component.annotations.Component;

/**
 *
 * @author puce
 */
@Component
public class FXMenuMenuItemContainerFactory implements MenuMenuItemContainerFactory<MenuItem, Menu> {

    @Override
    public <F extends MenuItemSupplierFactory<MenuItem, F>> MenuItemContainer<MenuItem, Menu, F> createMenuMenuItemContainer(String id, Menu menu,
            MenuItemContainer<MenuItem, Menu, ?> parentContainer,
            AbstractMenuItemRootContainer<MenuItem, Menu, ?> rootContainer,
            MenuItemSortingStrategy<MenuItem, F> menuItemSortingStrategy,
            MenuMenuItemContainerFactory<MenuItem, Menu> menuMenuItemContainerFactory,
            SeparatorMenuItemFactory<? extends MenuItem> separatorMenuItemFactory) {
        return new FXMenuMenuItemContainer<>(id, menu, parentContainer, rootContainer, menuItemSortingStrategy, menuMenuItemContainerFactory, separatorMenuItemFactory);
    }

}
