package org.drombler.fx.core.docking.impl;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.stage.WindowEvent;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.drombler.acp.core.docking.spi.DockingAreaContainerProvider;
import org.drombler.commons.docking.fx.FXDockableData;
import org.drombler.commons.docking.fx.FXDockableEntry;
import org.drombler.commons.docking.fx.context.WindowOnCloseRequestHandler;
import org.drombler.fx.core.application.WindowOnCloseRequestHandlerProvider;
import org.osgi.service.component.ComponentContext;

/**
 *
 * @author puce
 */
@Component
@Service
public class WindowOnCloseRequestHandlerProviderImpl implements WindowOnCloseRequestHandlerProvider {

    private WindowOnCloseRequestHandler windowOnCloseRequestHandler;

    @Reference
    private DockingAreaContainerProvider<Node, FXDockableData, FXDockableEntry> dockingAreaContainerProvider;

    protected void bindDockingAreaContainerProvider(DockingAreaContainerProvider<Node, FXDockableData, FXDockableEntry> dockingAreaContainerProvider) {
        this.dockingAreaContainerProvider = dockingAreaContainerProvider;
    }

    protected void unbindDockingAreaContainerProvider(DockingAreaContainerProvider<Node, FXDockableData, FXDockableEntry> dockingAreaContainerProvider) {
        this.dockingAreaContainerProvider = null;
    }

    @Activate
    protected void activate(ComponentContext context) {
        windowOnCloseRequestHandler = new WindowOnCloseRequestHandler(dockingAreaContainerProvider.getDockingAreaContainer());
    }

    @Deactivate
    protected void deactivate(ComponentContext context) {
        windowOnCloseRequestHandler = null;
    }

    @Override
    public EventHandler<WindowEvent> getWindowOnCloseRequestHandler() {
        return windowOnCloseRequestHandler;
    }

}
