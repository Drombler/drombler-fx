/*
 *         COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Notice
 *
 * The contents of this file are subject to the COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL)
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.opensource.org/licenses/cddl1.txt
 *
 * The Original Code is Drombler.org. The Initial Developer of the
 * Original Code is Florian Brunner (Sourceforge.net user: puce).
 * Copyright 2012 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.fx.core.standard.action.impl;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.drombler.acp.core.action.Action;
import org.drombler.acp.core.action.MenuEntry;
import org.drombler.acp.core.commons.util.SimpleServiceTrackerCustomizer;
import org.drombler.fx.core.application.OnExitRequestHandlerProvider;
import org.osgi.util.tracker.ServiceTracker;

/**
 *
 * @author puce
 */
@Action(id = "platform.exit", category = "core", displayName = "%exit.displayName", accelerator = "Shortcut+Q")
@MenuEntry(path = "File", position = 9900)
public class ExitAction implements EventHandler<ActionEvent>, AutoCloseable {

    private final ServiceTracker<OnExitRequestHandlerProvider, OnExitRequestHandlerProvider> onExitRequestHandlerProviderServiceTracker;
    private OnExitRequestHandlerProvider onExitRequestHandlerProvider;

    public ExitAction() {
        this.onExitRequestHandlerProviderServiceTracker = SimpleServiceTrackerCustomizer.createServiceTracker(OnExitRequestHandlerProvider.class, this::setOnExitRequestHandlerProvider);
        this.onExitRequestHandlerProviderServiceTracker.open(true);
    }

    @Override
    public void handle(ActionEvent t) {
        if (onExitRequestHandlerProvider != null) {
            if (onExitRequestHandlerProvider.getOnExitRequestHandler().handleExitRequest()) {
                Platform.exit();
            }
        } else {
            Platform.exit();
        }
    }

    private void setOnExitRequestHandlerProvider(OnExitRequestHandlerProvider onExitRequestHandlerProvider) {
        this.onExitRequestHandlerProvider = onExitRequestHandlerProvider;
    }

    @Override
    public void close() {
        onExitRequestHandlerProviderServiceTracker.close();
    }
}
