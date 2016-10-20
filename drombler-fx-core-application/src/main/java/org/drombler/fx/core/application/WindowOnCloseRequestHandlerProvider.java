/*
 *         COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Notice
 *
 * The contents of this file are subject to the COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL)
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.opensource.org/licenses/cddl1.txt
 *
 * The Original Code is Drombler.org. The Initial Developer of the
 * Original Code is Florian Brunner (GitHub user: puce77).
 * Copyright 2016 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.fx.core.application;

import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

/**
 * A service interface for a window on close request handler provider. It provides an {@link EventHandler} for window on close request events.
 *
 * @author puce
 */
public interface WindowOnCloseRequestHandlerProvider {

    /**
     * Gets a window on close request event handler.
     *
     * @return a window on close request event handler. The request can be vetoed by consuming the {@link WindowEvent}.
     * @see WindowEvent#consume()
     */
    EventHandler<WindowEvent> getWindowOnCloseRequestHandler();
}
