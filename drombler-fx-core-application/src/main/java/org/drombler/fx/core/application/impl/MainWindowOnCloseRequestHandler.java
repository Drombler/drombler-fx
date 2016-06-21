package org.drombler.fx.core.application.impl;

import org.drombler.fx.core.application.WindowOnCloseRequestHandlerProvider;
import javafx.stage.Stage;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Reference;
import org.drombler.acp.startup.main.MainWindowProvider;
import org.osgi.service.component.ComponentContext;

/**
 *
 * @author puce
 */
@Component(immediate = true)
public class MainWindowOnCloseRequestHandler {

    @Reference
    private MainWindowProvider<Stage> mainWindowProvider;

    @Reference
    private WindowOnCloseRequestHandlerProvider windowOnCloseRequestHandlerProvider;

    protected void bindMainWindowProvider(MainWindowProvider<Stage> mainWindowProvider) {
        this.mainWindowProvider = mainWindowProvider;
    }

    protected void unbindMainWindowProvider(MainWindowProvider<Stage> mainWindowProvider) {
        this.mainWindowProvider = null;
    }

    protected void bindWindowOnCloseRequestHandlerProvider(WindowOnCloseRequestHandlerProvider windowOnCloseRequestHandlerProvider) {
        this.windowOnCloseRequestHandlerProvider = windowOnCloseRequestHandlerProvider;
    }

    protected void unbindWindowOnCloseRequestHandlerProvider(WindowOnCloseRequestHandlerProvider windowOnCloseRequestHandlerProvider) {
        this.windowOnCloseRequestHandlerProvider = null;
    }

    @Activate
    protected void activate(ComponentContext context) {
        mainWindowProvider.getMainWindow().setOnCloseRequest(windowOnCloseRequestHandlerProvider.getWindowOnCloseRequestHandler());
    }

    @Deactivate
    protected void deactivate(ComponentContext context) {
        mainWindowProvider.getMainWindow().setOnCloseRequest(null);
    }

}
