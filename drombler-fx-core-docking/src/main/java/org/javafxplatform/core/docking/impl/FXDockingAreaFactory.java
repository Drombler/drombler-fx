/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.docking.impl;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.drombler.acp.core.docking.spi.DockingAreaDescriptor;
import org.drombler.acp.core.docking.spi.DockingAreaFactory;

/**
 *
 * @author puce
 */
@Component
@Service
public class FXDockingAreaFactory implements DockingAreaFactory<DockingAreaPane> {

    @Override
    public DockingAreaPane createDockingArea(DockingAreaDescriptor dockingAreaDescriptor) {
        DockingAreaPane dockingAreaPane = new DockingAreaPane(dockingAreaDescriptor.getId(),
                dockingAreaDescriptor.getPosition(), dockingAreaDescriptor.isPermanent());
        dockingAreaPane.setLayoutConstraints(dockingAreaDescriptor.getLayoutConstraints());
        return dockingAreaPane;
    }
}
