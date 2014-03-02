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

import java.util.ArrayList;
import java.util.List;
import javafx.collections.SetChangeListener;
import org.drombler.acp.core.docking.spi.DockingAreaContainer;
import org.drombler.acp.core.docking.spi.DockingAreaContainerDockingAreaEvent;
import org.drombler.acp.core.docking.spi.DockingAreaContainerListener;
import org.drombler.commons.client.docking.DockableEntry;
import org.drombler.commons.client.docking.DockingAreaDescriptor;
import org.drombler.commons.fx.docking.DockablePane;
import org.drombler.commons.fx.docking.DockingPane;

/**
 *
 * @author puce
 */
public class DockingPaneDockingAreaContainerAdapter implements DockingAreaContainer<DockablePane> {

    private final List<DockingAreaContainerListener<DockablePane>> listeners = new ArrayList<>();
    private final DockingPane dockingPane;

    public DockingPaneDockingAreaContainerAdapter(DockingPane dockingPane) {
        this.dockingPane = dockingPane;
        dockingPane.getDockingAreaDescriptors().addListener(new SetChangeListener<DockingAreaDescriptor>() {

            @Override
            public void onChanged(SetChangeListener.Change<? extends DockingAreaDescriptor> change) {
                if (change.wasAdded()) {
                    fireDockingAreaAdded(change.getElementAdded().getId());
                } else if (change.wasRemoved()) {
                    fireDockingAreaRemoved(change.getElementRemoved().getId());
                }
            }
        });
    }

    @Override
    public void addDockingAreaContainerListener(DockingAreaContainerListener<DockablePane> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeDockingAreaContainerListener(DockingAreaContainerListener<DockablePane> listener) {
        listeners.remove(listener);
    }

    private void fireDockingAreaAdded(String dockingAreaId) {
        DockingAreaContainerDockingAreaEvent<DockablePane> event = new DockingAreaContainerDockingAreaEvent<>(this,
                dockingAreaId);
        for (DockingAreaContainerListener<DockablePane> listener : listeners) {
            listener.dockingAreaAdded(event);
        }
    }

    private void fireDockingAreaRemoved(String dockingAreaId) {
        DockingAreaContainerDockingAreaEvent<DockablePane> event = new DockingAreaContainerDockingAreaEvent<>(this,
                dockingAreaId);
        for (DockingAreaContainerListener<DockablePane> listener : listeners) {
            listener.dockingAreaRemoved(event);
        }
    }

    @Override
    public boolean addDockingArea(DockingAreaDescriptor dockingAreaDescriptor) {
        return dockingPane.getDockingAreaDescriptors().add(dockingAreaDescriptor);
    }

    @Override
    public boolean addDockable(DockableEntry<? extends DockablePane> dockableEntry) {
        return dockingPane.getDockables().add(dockableEntry);
    }
}
