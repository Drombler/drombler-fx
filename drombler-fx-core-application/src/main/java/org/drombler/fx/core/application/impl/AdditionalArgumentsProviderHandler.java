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
package org.drombler.fx.core.application.impl;

import javafx.stage.Stage;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.apache.felix.scr.annotations.ReferencePolicy;
import org.drombler.acp.startup.main.AdditionalArgumentsProvider;
import org.drombler.acp.startup.main.ApplicationExecutorProvider;
import org.drombler.acp.startup.main.MainWindowProvider;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author puce
 */
@Component(immediate = true)
@Reference(name = "additionalArgumentsProvider", referenceInterface = AdditionalArgumentsProvider.class,
        cardinality = ReferenceCardinality.OPTIONAL_MULTIPLE, policy = ReferencePolicy.DYNAMIC)
public class AdditionalArgumentsProviderHandler {

    private static final Logger LOG = LoggerFactory.getLogger(AdditionalArgumentsProviderHandler.class);

    @Reference
    private MainWindowProvider<Stage> mainWindowProvider;
    @Reference
    private ApplicationExecutorProvider applicationExecutorProvider;

    protected void bindAdditionalArgumentsProvider(AdditionalArgumentsProvider additionalArgumentsProvider) {
        mainWindowToFront();
    }

    protected void unbindAdditionalArgumentsProvider(AdditionalArgumentsProvider additionalArgumentsProvider) {
        // TODO
    }

    protected void bindMainWindowProvider(MainWindowProvider<Stage> mainWindowProvider) {
        this.mainWindowProvider = mainWindowProvider;
    }

    protected void unbindMainWindowProvider(MainWindowProvider<Stage> mainWindowProvider) {
        this.mainWindowProvider = null;
    }

    protected void bindApplicationExecutorProvider(ApplicationExecutorProvider applicationExecutorProvider) {
        this.applicationExecutorProvider = applicationExecutorProvider;
    }

    protected void unbindApplicationExecutorProvider(ApplicationExecutorProvider applicationExecutorProvider) {
        this.applicationExecutorProvider = null;
    }

    @Activate
    protected void activate(ComponentContext context) {

    }

    @Deactivate
    protected void deactivate(ComponentContext context) {

    }

    private boolean isInitialized() {
        return mainWindowProvider != null && applicationExecutorProvider != null;
    }

    private void mainWindowToFront() {
        if (isInitialized()) {
            applicationExecutorProvider.getApplicationExecutor().execute(() -> {
                mainWindowProvider.getMainWindow().toFront();
                LOG.debug("Called mainWindow.toFront()!");
            });
        }
    }

}
