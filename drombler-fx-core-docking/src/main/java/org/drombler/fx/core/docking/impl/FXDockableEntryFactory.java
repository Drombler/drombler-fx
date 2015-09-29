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
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.drombler.acp.core.docking.spi.DockableDataManagerProvider;
import org.drombler.acp.core.docking.spi.DockableEntryFactory;
import org.drombler.commons.docking.DockablePreferences;
import org.drombler.commons.docking.fx.FXDockableData;
import org.drombler.commons.docking.fx.FXDockableEntry;

/**
 *
 * @author puce
 */
@Component
@Service
public class FXDockableEntryFactory implements DockableEntryFactory<Node, FXDockableEntry> {

    @Reference
    private DockableDataManagerProvider<Node, FXDockableData> dockableDataManagerProvider;

    protected void bindDockableDataManagerProvider(
            DockableDataManagerProvider<Node, FXDockableData> dockableDataManagerProvider) {
        this.dockableDataManagerProvider = dockableDataManagerProvider;
    }

    protected void unbindDockableDataManagerProvider(
            DockableDataManagerProvider<Node, FXDockableData> dockableDataManagerProvider) {
        this.dockableDataManagerProvider = null;
    }

    @Override
    public FXDockableEntry createDockableEntry(Node dockable, DockablePreferences dockablePreferences) {
        final FXDockableData dockableData = dockableDataManagerProvider.getDockableDataManager().getDockableData(
                dockable);
        return new FXDockableEntry(dockable, dockableData, dockablePreferences);
    }
}
