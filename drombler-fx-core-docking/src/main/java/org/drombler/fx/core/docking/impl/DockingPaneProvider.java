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

import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.drombler.acp.core.commons.util.context.ActiveContextProvider;
import org.drombler.acp.core.commons.util.context.ApplicationContextProvider;
import org.drombler.acp.core.commons.util.context.Context;
import org.drombler.acp.core.docking.spi.DockablePreferencesManager;
import org.drombler.acp.core.docking.spi.DockingAreaContainer;
import org.drombler.acp.core.docking.spi.DockingAreaContainerProvider;
import org.drombler.fx.core.application.ApplicationContentProvider;
import org.drombler.fx.core.application.FocusOwnerChangeListenerProvider;
import org.drombler.fx.core.docking.DockablePane;

/**
 *
 * @author puce
 */
@Component
@Service
public class DockingPaneProvider implements ApplicationContentProvider,
        DockingAreaContainerProvider<DockingAreaPane, DockablePane>, FocusOwnerChangeListenerProvider,
        ActiveContextProvider, ApplicationContextProvider {

    @Reference
    private DockablePreferencesManager<DockablePane> dockablePreferencesManager;
    private DockingPane dockingPane;

    protected void bindDockablePreferencesManager(DockablePreferencesManager<DockablePane> dockablePreferencesManager) {
        this.dockablePreferencesManager = dockablePreferencesManager;
    }

    protected void unbindDockablePreferencesManager(DockablePreferencesManager<DockablePane> dockablePreferencesManager) {
        this.dockablePreferencesManager = null;
    }

    @Override
    public Node getContentPane() {
        return getDockingPane();
    }

    @Override
    public DockingAreaContainer<DockingAreaPane, DockablePane> getDockingAreaContainer() {
        return getDockingPane();
    }

    @Override
    public Context getApplicationContext() {
        return getDockingPane().getApplicationContext();
    }

    @Override
    public Context getActiveContext() {
        return getDockingPane().getActiveContext();
    }

    @Override
    public ChangeListener<Node> getFocusOwnerChangeListener() {
        return getDockingPane().getFocusOwnerChangeListener();
    }

    private DockingPane getDockingPane() {
        if (dockingPane == null) {
            dockingPane = new DockingPane();
            dockingPane.setDockablePreferencesManager(dockablePreferencesManager);
        }
        return dockingPane;
    }
}
