package org.drombler.fx.core.application.impl;

import javafx.stage.Stage;
import org.drombler.acp.startup.main.MainWindowProvider;
import org.drombler.fx.core.application.WindowOnCloseRequestHandlerProvider;
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
    private WindowOnCloseRequestHandlerProvider windowOnCloseRequestHandlerProvider;

    @Activate
    protected void activate(ComponentContext context) {
        mainWindowProvider.getMainWindow().setOnCloseRequest(windowOnCloseRequestHandlerProvider.getWindowOnCloseRequestHandler());
    }

    @Deactivate
    protected void deactivate(ComponentContext context) {
        mainWindowProvider.getMainWindow().setOnCloseRequest(null);
    }

}
