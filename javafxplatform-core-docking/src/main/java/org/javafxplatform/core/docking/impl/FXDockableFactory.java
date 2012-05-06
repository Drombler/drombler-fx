/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.docking.impl;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.javafxplatform.core.docking.DockablePane;
import org.richclientplatform.core.docking.spi.DockableFactory;
import org.richclientplatform.core.docking.spi.DockingDescriptor;

/**
 *
 * @author puce
 */
@Component
@Service
public class FXDockableFactory implements DockableFactory<DockablePane> {

    private static final String MNEMONIC_CHAR = "_";

    @Override
    public DockablePane createDockable(DockingDescriptor dockingDescriptor) {
        DockablePane dockablePane = (DockablePane) dockingDescriptor.getDockable();
        dockablePane.setTitle(dockingDescriptor.getDisplayName().replace(MNEMONIC_CHAR, ""));
        return dockablePane;
    }

}
