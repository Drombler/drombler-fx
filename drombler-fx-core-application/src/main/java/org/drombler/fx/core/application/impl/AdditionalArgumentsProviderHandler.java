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
import org.drombler.acp.startup.main.AdditionalArgumentsProvider;
import org.drombler.acp.core.commons.util.concurrent.ApplicationExecutorProvider;
import org.drombler.acp.startup.main.MainWindowProvider;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author puce
 */
@Component(immediate = true)
public class AdditionalArgumentsProviderHandler {

    private static final Logger LOG = LoggerFactory.getLogger(AdditionalArgumentsProviderHandler.class);

    @Reference
    private MainWindowProvider<Stage> mainWindowProvider;
    @Reference
    private ApplicationExecutorProvider applicationExecutorProvider;

    @Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
    public void bindAdditionalArgumentsProvider(AdditionalArgumentsProvider additionalArgumentsProvider) {
        mainWindowToFront();
    }

    public void unbindAdditionalArgumentsProvider(AdditionalArgumentsProvider additionalArgumentsProvider) {
        // TODO
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
