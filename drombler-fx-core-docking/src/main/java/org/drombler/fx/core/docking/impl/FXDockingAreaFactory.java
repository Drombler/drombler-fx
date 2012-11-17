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
package org.drombler.fx.core.docking.impl;

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
