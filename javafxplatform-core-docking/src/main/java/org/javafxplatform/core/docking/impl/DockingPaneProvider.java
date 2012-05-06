/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.docking.impl;

import javafx.scene.Node;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.javafxplatform.core.application.ApplicationContentProvider;
import org.javafxplatform.core.docking.DockablePane;
import org.richclientplatform.core.docking.spi.DockingAreaContainer;
import org.richclientplatform.core.docking.spi.DockingAreaContainerProvider;

/**
 *
 * @author puce
 */
@Component
@Service
public class DockingPaneProvider implements ApplicationContentProvider, DockingAreaContainerProvider<DockingAreaPane, DockablePane> {

    private final DockingPane dockingPane = new DockingPane();

    @Override
    public Node getContentPane() {
        return dockingPane;
    }

    @Override
    public DockingAreaContainer<DockingAreaPane, DockablePane> getDockingAreaContainer() {
        return dockingPane;
    }
}
