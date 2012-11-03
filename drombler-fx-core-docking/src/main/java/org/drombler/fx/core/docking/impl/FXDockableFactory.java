/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drombler.fx.core.docking.impl;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.drombler.acp.core.docking.spi.DockableFactory;
import org.drombler.acp.core.docking.spi.ViewDockingDescriptor;
import org.drombler.fx.core.docking.DockablePane;

/**
 *
 * @author puce
 */
@Component
@Service
public class FXDockableFactory implements DockableFactory<DockablePane> {

    private static final String MNEMONIC_CHAR = "_";

    @Override
    public DockablePane createDockable(ViewDockingDescriptor dockingDescriptor) {
        try {
            DockablePane dockablePane = (DockablePane) dockingDescriptor.getDockableClass().newInstance();
            dockablePane.setTitle(dockingDescriptor.getDisplayName().replace(MNEMONIC_CHAR, ""));
            return dockablePane;
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(FXDockableFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
