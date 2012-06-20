/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.docking.impl;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.javafxplatform.core.application.ApplicationContentProvider;
import org.javafxplatform.core.application.FocusOwnerChangeListenerProvider;
import org.javafxplatform.core.docking.DockablePane;
import org.richclientplatform.core.docking.spi.DockablePreferencesManager;
import org.richclientplatform.core.docking.spi.DockingAreaContainer;
import org.richclientplatform.core.docking.spi.DockingAreaContainerProvider;
import org.richclientplatform.core.lib.util.ActiveContextProvider;
import org.richclientplatform.core.lib.util.ApplicationContextProvider;
import org.richclientplatform.core.lib.util.Context;
import org.richclientplatform.core.lib.util.Contexts;

/**
 *
 * @author puce
 */
@Component
@Service
@Reference(name = "dockablePreferencesManager", referenceInterface = DockablePreferencesManager.class)
public class DockingPaneProvider implements ApplicationContentProvider,
        DockingAreaContainerProvider<DockingAreaPane, DockablePane>, FocusOwnerChangeListenerProvider,
        ActiveContextProvider, ApplicationContextProvider {

    private final DockingPane dockingPane = new DockingPane();

    protected void bindDockablePreferencesManager(DockablePreferencesManager<DockablePane> dockablePreferencesManager) {
        dockingPane.setDockablePreferencesManager(dockablePreferencesManager);
    }

    protected void unbindDockablePreferencesManager(DockablePreferencesManager<DockablePane> dockablePreferencesManager) {
        dockingPane.setDockablePreferencesManager(null);
    }

    @Override
    public Node getContentPane() {
        return dockingPane;
    }

    @Override
    public DockingAreaContainer<DockingAreaPane, DockablePane> getDockingAreaContainer() {
        return dockingPane;
    }
    private ObjectProperty<DockablePane> activeDockable = new SimpleObjectProperty<>(this, "activeDockable");

   

    @Override
    public Context getApplicationContext() {
        return dockingPane.getApplicationContext();
    }

    @Override
    public Context getActiveContext() {
        return dockingPane.getActiveContext();
    }

    @Override
    public ChangeListener<Node> getFocusOwnerChangeListener() {
        return dockingPane.getFocusOwnerChangeListener();
    }
}
