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
package org.drombler.fx.core.standard.desktop.classic.impl;

import java.util.concurrent.Executor;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Reference;
import org.drombler.acp.startup.main.ApplicationExecutorProvider;
import org.drombler.fx.startup.main.ApplicationContentProvider;
import org.osgi.service.component.ComponentContext;

/**
 * This handler is looking for a content pane for the application pane.
 *
 * @author puce
 */
@Component(immediate = true)
@Reference(name = "applicationExecutorProvider", referenceInterface = ApplicationExecutorProvider.class)
public class ContentHandler {

    @Reference
    private ContentPaneProvider contentPaneProvider;
    @Reference
    private ApplicationContentProvider applicationContentProvider;
    private Executor applicationExecutor;

    protected void bindContentPaneProvider(ContentPaneProvider contentPaneProvider) {
        this.contentPaneProvider = contentPaneProvider;
    }

    protected void unbindContentPaneProvider(ContentPaneProvider contentPaneProvider) {
    }

    protected void bindApplicationContentProvider(ApplicationContentProvider applicationContentProvider) {
        this.applicationContentProvider = applicationContentProvider;
    }

    protected void unbindApplicationContentProvider(ApplicationContentProvider applicationContentProvider) {
        this.applicationContentProvider = null;
    }

    protected void bindApplicationExecutorProvider(ApplicationExecutorProvider applicationExecutorProvider) {
        applicationExecutor = applicationExecutorProvider.getApplicationExecutor();
    }

    protected void unbindApplicationExecutorProvider(ApplicationExecutorProvider applicationExecutorProvider) {
        applicationExecutor = null;
    }

    @Activate
    protected void activate(ComponentContext context) {
        applicationExecutor.execute(()
                -> contentPaneProvider.getContentPane().setCenter(applicationContentProvider.getContentPane()));
    }

    @Deactivate
    protected void deactivate(ComponentContext context) {
        applicationExecutor.execute(() -> {
            contentPaneProvider.getContentPane().setCenter(null);
            contentPaneProvider = null;
        });
    }

}
