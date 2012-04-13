/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.docking.impl;

import javafx.scene.Node;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.apache.felix.scr.annotations.ReferencePolicy;
import org.apache.felix.scr.annotations.References;
import org.apache.felix.scr.annotations.Service;
import org.javafxplatform.core.docking.DockablePane;
import org.javafxplatform.core.startup.ApplicationContentProvider;
import org.richclientplatform.core.docking.processing.DockingAreaDescriptor;
import org.richclientplatform.core.docking.processing.DockingDescriptor;

/**
 *
 * @author puce
 */
@Component
@Service
@References({
    @Reference(name = "dockingAreaDescriptor", referenceInterface = DockingAreaDescriptor.class,
    cardinality = ReferenceCardinality.OPTIONAL_MULTIPLE, policy = ReferencePolicy.DYNAMIC),
    @Reference(name = "dockingDescriptor", referenceInterface = DockingDescriptor.class,
    cardinality = ReferenceCardinality.OPTIONAL_MULTIPLE, policy = ReferencePolicy.DYNAMIC)
})
public class DockingManager implements ApplicationContentProvider {

    private static final String MNEMONIC_CHAR = "_";
    private final DockingPane dockingPane = new DockingPane();

    protected void bindDockingAreaDescriptor(DockingAreaDescriptor dockingAreaDescriptor) {
        DockingAreaPane dockingAreaPane = new DockingAreaPane();
        dockingPane.addDockingArea(dockingAreaPane);
    }

    protected void unbindDockingAreaDescriptor(DockingAreaDescriptor dockingAreaDescriptor) {
    }

    protected void bindDockingDescriptor(DockingDescriptor dockingAreaDescriptor) {
        DockablePane dockablePane = (DockablePane) dockingAreaDescriptor.getDockable();
        dockablePane.setTitle(dockingAreaDescriptor.getDisplayName().replace(MNEMONIC_CHAR, ""));
        dockingPane.addDockable(dockablePane);
    }

    protected void unbindDockingDescriptor(DockingDescriptor dockingDescriptor) {
    }

    @Override
    public Node getContentPane() {
        return dockingPane;
    }
}
