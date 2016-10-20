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


import javafx.scene.Node;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.drombler.acp.core.docking.spi.DockableFactory;
import org.drombler.acp.core.docking.spi.ViewDockingDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author puce
 */
@Component
@Service
public class FXDockableFactory implements DockableFactory<Node> {

    private static final Logger LOG = LoggerFactory.getLogger(FXDockableFactory.class);

    @Override
    public <D extends Node> D createDockable(ViewDockingDescriptor<D> dockingDescriptor) {
        try {
            return dockingDescriptor.getDockableClass().newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            LOG.error(ex.getMessage(), ex);
        }
        return null;
    }
}
