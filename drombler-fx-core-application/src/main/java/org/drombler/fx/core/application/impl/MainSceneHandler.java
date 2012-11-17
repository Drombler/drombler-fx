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
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Reference;
import org.drombler.acp.core.application.ApplicationExecutorProvider;
import org.drombler.fx.core.application.FocusOwnerChangeListenerProvider;
import org.osgi.service.component.ComponentContext;

/**
 *
 * @author puce
 */
@Component(immediate = true)
@Reference(name = "applicationExecutorProvider", referenceInterface = ApplicationExecutorProvider.class)
public class MainSceneHandler {

    @Reference
    private MainSceneProvider mainSceneProvider;
    @Reference
    private FocusOwnerChangeListenerProvider focusOwnerChangeListenerProvider;
    private Executor applicationExecutor;

    protected void bindMainSceneProvider(MainSceneProvider mainSceneProvider) {
        this.mainSceneProvider = mainSceneProvider;
    }

    protected void unbindMainSceneProvider(MainSceneProvider mainSceneProvider) {
        this.mainSceneProvider = null;
    }

    protected void bindFocusOwnerChangeListenerProvider(FocusOwnerChangeListenerProvider focusOwnerChangeListenerProvider) {
        this.focusOwnerChangeListenerProvider = focusOwnerChangeListenerProvider;
    }

    protected void unbindFocusOwnerChangeListenerProvider(FocusOwnerChangeListenerProvider focusOwnerChangeListenerProvider) {
        this.focusOwnerChangeListenerProvider = null;
    }

    protected void bindApplicationExecutorProvider(ApplicationExecutorProvider applicationExecutorProvider) {
        applicationExecutor = applicationExecutorProvider.getApplicationExecutor();
    }

    protected void unbindApplicationExecutorProvider(ApplicationExecutorProvider applicationExecutorProvider) {
        applicationExecutor = null;
    }

    @Activate
    protected void activate(ComponentContext context) {
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                mainSceneProvider.getMainScene().focusOwnerProperty().addListener(
                        focusOwnerChangeListenerProvider.getFocusOwnerChangeListener());
            }
        };
        applicationExecutor.execute(runnable);
    }

    @Deactivate
    protected void deactivate(ComponentContext context) {
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                mainSceneProvider.getMainScene().focusOwnerProperty().removeListener(
                        focusOwnerChangeListenerProvider.getFocusOwnerChangeListener());
            }
        };
        applicationExecutor.execute(runnable);
    }
}
