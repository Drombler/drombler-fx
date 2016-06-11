package org.drombler.fx.core.application;

import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

/**
 *
 * @author puce
 */
public interface WindowOnCloseRequestHandlerProvider {

    EventHandler<WindowEvent> getWindowOnCloseRequestHandler();
}
