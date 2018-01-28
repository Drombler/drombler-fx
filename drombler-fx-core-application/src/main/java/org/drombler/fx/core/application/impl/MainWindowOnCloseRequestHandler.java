package org.drombler.fx.core.application.impl;

import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.drombler.acp.startup.main.MainWindowProvider;
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
@Component(immediate = true)
public class MainWindowOnCloseRequestHandler {

    @Reference
    private MainWindowProvider<Stage> mainWindowProvider;

    @Reference
    private OnExitRequestHandlerProvider onExitRequestHandlerProvider;

    private EventHandler<WindowEvent> onCloseRequestEventHandler;

    @Activate
    protected void activate(ComponentContext context) {
        this.onCloseRequestEventHandler = OnExitRequestHandler.createOnWindowCloseRequestEventHandler(onExitRequestHandlerProvider.getOnExitRequestHandler());
        mainWindowProvider.getMainWindow().setOnCloseRequest(onCloseRequestEventHandler);
    }

    @Deactivate
    protected void deactivate(ComponentContext context) {
        mainWindowProvider.getMainWindow().setOnCloseRequest(null);
        this.onCloseRequestEventHandler = null;
    }

}
