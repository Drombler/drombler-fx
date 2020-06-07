package org.drombler.fx.core.docking.impl;

import javafx.scene.Node;
import org.drombler.acp.core.docking.spi.DockingAreaContainerProvider;
import org.drombler.commons.docking.fx.FXDockableData;
import org.drombler.commons.docking.fx.FXDockableEntry;
import org.drombler.commons.docking.fx.context.DockingOnExitRequestHandler;
import org.drombler.commons.fx.stage.OnExitRequestHandler;
import org.drombler.fx.core.application.OnExitRequestHandlerProvider;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 *
 * @author puce
 */
@Component
public class OnExitRequestHandlerProviderImpl implements OnExitRequestHandlerProvider {

    private DockingOnExitRequestHandler windowOnCloseRequestHandler;

    @Reference
    private DockingAreaContainerProvider<Node, FXDockableData, FXDockableEntry> dockingAreaContainerProvider;


    @Activate
    protected void activate(ComponentContext context) {
        windowOnCloseRequestHandler = new DockingOnExitRequestHandler(dockingAreaContainerProvider.getDockingAreaContainer());
    }

    @Deactivate
    protected void deactivate(ComponentContext context) {
        windowOnCloseRequestHandler = null;
    }

    @Override
    public OnExitRequestHandler getOnExitRequestHandler() {
        return windowOnCloseRequestHandler;
    }

}
