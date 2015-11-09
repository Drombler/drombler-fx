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
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Reference;
import org.drombler.acp.startup.main.ApplicationExecutorProvider;
import org.drombler.acp.startup.main.MainWindowProvider;
import org.drombler.fx.startup.main.MainSceneRootProvider;
import org.osgi.service.component.ComponentContext;
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
    private ApplicationExecutorProvider applicationExecutorProvider;
    private Parent oldRoot = null;

    protected void bindMainWindowProvider(MainWindowProvider<Stage> mainWindowProvider) {
        this.mainWindowProvider = mainWindowProvider;
    }

    protected void unbindMainWindowProvider(MainWindowProvider<Stage> mainWindowProvider) {

    }

    protected void bindMainSceneRootProvider(MainSceneRootProvider mainSceneRootProvider) {
        this.mainSceneRootProvider = mainSceneRootProvider;
    }

    protected void unbindMainSceneRootProvider(MainSceneRootProvider mainSceneRootHandler) {
        this.mainSceneRootProvider = null;
    }

    protected void bindApplicationExecutorProvider(ApplicationExecutorProvider applicationExecutorProvider) {
        this.applicationExecutorProvider = applicationExecutorProvider;
    }

    protected void unbindApplicationExecutorProvider(ApplicationExecutorProvider applicationExecutorProvider) {
        this.applicationExecutorProvider = null;
    }

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
        return applicationExecutorProvider.getApplicationExecutor();
    }

}
