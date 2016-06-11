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
import java.util.Set;
import javafx.collections.SetChangeListener;
import javafx.scene.Node;
import org.drombler.acp.core.docking.spi.DockingAreaContainer;
import org.drombler.acp.core.docking.spi.DockingAreaContainerDockableEvent;
import org.drombler.acp.core.docking.spi.DockingAreaContainerDockingAreaEvent;
import org.drombler.acp.core.docking.spi.DockingAreaContainerListener;
import org.drombler.commons.docking.DockingAreaDescriptor;
import org.drombler.commons.docking.fx.DockingPane;
import org.drombler.commons.docking.fx.FXDockableEntry;

/**
 *
 * @author puce
 */
public class DockingPaneDockingAreaContainerAdapter implements DockingAreaContainer<Node, FXDockableEntry> {

    private final List<DockingAreaContainerListener<Node, FXDockableEntry>> listeners = new ArrayList<>();
    private final DockingPane dockingPane;

    public DockingPaneDockingAreaContainerAdapter(DockingPane dockingPane) {
        this.dockingPane = dockingPane;
        dockingPane.getDockingAreaDescriptors().addListener(
                (SetChangeListener.Change<? extends DockingAreaDescriptor> change) -> {
                    if (change.wasAdded()) {
                        fireDockingAreaAdded(change.getElementAdded().getId());
                    } else if (change.wasRemoved()) {
                        fireDockingAreaRemoved(change.getElementRemoved().getId());
                    }
                });
        dockingPane.getDockables().addListener(
                (SetChangeListener.Change<? extends FXDockableEntry> change) -> {
                    if (change.wasAdded()) {
                        fireDockableAdded(change.getElementAdded());
                    } else if (change.wasRemoved()) {
                        fireDockableRemoved(change.getElementRemoved());
                    }
                });
    }

    @Override
    public void addDockingAreaContainerListener(DockingAreaContainerListener<Node, FXDockableEntry> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeDockingAreaContainerListener(DockingAreaContainerListener<Node, FXDockableEntry> listener) {
        listeners.remove(listener);
    }

    private void fireDockingAreaAdded(String dockingAreaId) {
        DockingAreaContainerDockingAreaEvent<Node, FXDockableEntry> event = new DockingAreaContainerDockingAreaEvent<>(this, dockingAreaId);
        listeners.forEach(listener -> listener.dockingAreaAdded(event));
    }

    private void fireDockingAreaRemoved(String dockingAreaId) {
        DockingAreaContainerDockingAreaEvent<Node, FXDockableEntry> event = new DockingAreaContainerDockingAreaEvent<>(this, dockingAreaId);
        listeners.forEach(listener -> listener.dockingAreaRemoved(event));
    }

    private void fireDockableAdded(FXDockableEntry dockableEntry) {
        DockingAreaContainerDockableEvent<Node, FXDockableEntry> event = new DockingAreaContainerDockableEvent<>(this, dockableEntry);
        listeners.forEach(listener -> listener.dockableAdded(event));
    }

    private void fireDockableRemoved(FXDockableEntry dockableEntry) {
        DockingAreaContainerDockableEvent<Node, FXDockableEntry> event = new DockingAreaContainerDockableEvent<>(this, dockableEntry);
        listeners.forEach(listener -> listener.dockableRemoved(event));
    }

    @Override
    public boolean addDockingArea(DockingAreaDescriptor dockingAreaDescriptor) {
        return dockingPane.getDockingAreaDescriptors().add(dockingAreaDescriptor);
    }

    @Override
    public boolean addDockable(FXDockableEntry dockableEntry) {
        boolean added = dockingPane.getDockables().add(dockableEntry);
        dockableEntry.getDockable().requestFocus();
        return added;
    }

    @Override
    public String getDefaultEditorAreaId() {
        return dockingPane.getDefaultEditorAreaId();
    }

    @Override
    public Set<FXDockableEntry> getDockables() {
        return dockingPane.getDockables();
    }

}
