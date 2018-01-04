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
 * Copyright 2015 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.fx.core.application.impl;

import java.util.concurrent.Executor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.drombler.acp.core.commons.util.concurrent.ApplicationThreadExecutorProvider;
import org.drombler.acp.startup.main.MainWindowProvider;
import org.drombler.fx.core.application.MainSceneRootProvider;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This handler is looking for a root node for the main scene.
 *
 * @author puce
 */
@Component(immediate = true)
public class MainSceneRootHandler {

    private static final Logger LOG = LoggerFactory.getLogger(MainSceneRootHandler.class);

    @Reference
    private MainWindowProvider<Stage> mainWindowProvider;
    @Reference
    private MainSceneRootProvider mainSceneRootProvider;

    @Reference
    private ApplicationThreadExecutorProvider applicationThreadExecutorProvider;
    private Parent oldRoot = null;


    @Activate
    protected void activate(ComponentContext context) {
        getApplicationExecutor().execute(() -> {
            try {
                oldRoot = getScene().getRoot();
                getScene().setRoot(mainSceneRootProvider.getRoot());
            } catch (Exception ex) {
                LOG.error(ex.getMessage(), ex);
            }
        });
    }

    @Deactivate
    protected void deactivate(ComponentContext context) {
        getApplicationExecutor().execute(() -> {
            getScene().setRoot(oldRoot);
            oldRoot = null;
            mainWindowProvider = null;
        });
    }

    private Scene getScene() {
        return mainWindowProvider.getMainWindow().getScene();
    }

    private Executor getApplicationExecutor() {
        return applicationThreadExecutorProvider.getApplicationThreadExecutor();
    }

}
