/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drombler.fx.core.action.impl;

import javafx.scene.control.Menu;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.drombler.acp.core.action.spi.MenuDescriptor;
import org.drombler.acp.core.action.spi.MenuFactory;


/**
 *
 * @author puce
 */
@Component
@Service
public class FXMenuFactory implements MenuFactory<Menu> {

    @Override
    public Menu createMenu(MenuDescriptor menuDescriptor) {
        Menu menu = new Menu(menuDescriptor.getDisplayName());
        menu.setMnemonicParsing(true);
        return menu;
    }
}
