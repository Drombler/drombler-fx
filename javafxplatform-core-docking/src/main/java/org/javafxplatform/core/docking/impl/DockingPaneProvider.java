/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.docking.impl;

import javafx.application.Platform;
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
import org.richclientplatform.core.lib.util.context.ActiveContextProvider;
import org.richclientplatform.core.lib.util.context.ApplicationContextProvider;
import org.richclientplatform.core.lib.util.context.Context;

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
//            System.out.println("Create DockingPane: isFxApplicationThread: " + Platform.isFxApplicationThread());
            dockingPane = new DockingPane();
            dockingPane.setDockablePreferencesManager(dockablePreferencesManager);
        }
        return dockingPane;
    }
}
