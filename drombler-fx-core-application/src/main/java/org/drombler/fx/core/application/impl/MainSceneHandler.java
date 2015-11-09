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
package org.drombler.fx.core.application.impl;

import java.util.concurrent.Executor;
import org.drombler.acp.startup.main.ApplicationExecutorProvider;
import org.osgi.service.component.ComponentContext;

/**
 *
 * @author puce
 */
//@Component(immediate = true)
//@Reference(name = "applicationExecutorProvider", referenceInterface = ApplicationExecutorProvider.class)
class MainSceneHandler {

//    @Reference
    private MainSceneProvider mainSceneProvider;
    private Executor applicationExecutor;

    protected void bindMainSceneProvider(MainSceneProvider mainSceneProvider) {
        this.mainSceneProvider = mainSceneProvider;
    }

    protected void unbindMainSceneProvider(MainSceneProvider mainSceneProvider) {
        this.mainSceneProvider = null;
    }

    protected void bindApplicationExecutorProvider(ApplicationExecutorProvider applicationExecutorProvider) {
        applicationExecutor = applicationExecutorProvider.getApplicationExecutor();
    }

    protected void unbindApplicationExecutorProvider(ApplicationExecutorProvider applicationExecutorProvider) {
        applicationExecutor = null;
    }

//    @Activate
    protected void activate(ComponentContext context) {
        applicationExecutor.execute(() -> {});
    }

//    @Deactivate
    protected void deactivate(ComponentContext context) {
        applicationExecutor.execute(() -> {});
    }
}
