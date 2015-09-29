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
import org.drombler.acp.core.docking.spi.DockingAreaContainer;
import org.drombler.acp.core.docking.spi.DockingAreaContainerProvider;
import org.drombler.commons.context.ActiveContextProvider;
import org.drombler.commons.context.ApplicationContextProvider;
import org.drombler.commons.context.Context;
import org.drombler.commons.context.ContextManager;
import org.drombler.commons.docking.fx.context.DockableDataModifiedManager;
import org.drombler.commons.docking.fx.context.DockingManager;
import org.drombler.commons.docking.fx.DockingPane;
import org.drombler.commons.docking.fx.FXDockableEntry;
import org.drombler.fx.core.application.ApplicationContentProvider;

/**
 *
 * @author puce
 */
@Component
@Service
public class DockingPaneProvider implements ApplicationContentProvider,
        DockingAreaContainerProvider<Node, FXDockableEntry>,
        ActiveContextProvider, ApplicationContextProvider {

    private final ContextManager contextManager = new ContextManager();
    private DockingPane dockingPane;
    private DockingAreaContainer<Node, FXDockableEntry> dockingAreaContainer;
    private DockingManager dockingManager;
    private DockableDataModifiedManager dockableDataModifiedManager;

    @Override
    public Node getContentPane() {
        return getDockingPane();
    }

    @Override
    public DockingAreaContainer<Node, FXDockableEntry> getDockingAreaContainer() {
        if (dockingAreaContainer == null) {
            dockingAreaContainer = new DockingPaneDockingAreaContainerAdapter(getDockingPane());
        }
        return dockingAreaContainer;
    }

    @Override
    public Context getApplicationContext() {
        return contextManager.getApplicationContext();
    }

    @Override
    public Context getActiveContext() {
        return contextManager.getActiveContext();
    }

    private DockingPane getDockingPane() {
        if (dockingPane == null) {
            dockingPane = new DockingPane();
            // TODO: remove "stop" DockingManager
            dockingManager = new DockingManager(dockingPane, contextManager);
            // TODO: remove "stop" DockableDataModifiedManager
            dockableDataModifiedManager = new DockableDataModifiedManager(dockingPane);
        }
        return dockingPane;
    }
}
