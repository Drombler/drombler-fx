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

import org.drombler.acp.core.commons.util.concurrent.ApplicationThreadExecutorProvider;
import org.drombler.fx.core.application.ApplicationContentProvider;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * This handler is looking for a content pane for the application pane.
 *
 * @author puce
 */
@Component(immediate = true)
public class ContentHandler {

    @Reference
    private ContentPaneProvider contentPaneProvider;
    @Reference
    private ApplicationContentProvider applicationContentProvider;
    @Reference
    private ApplicationThreadExecutorProvider applicationThreadExecutorProvider;

    @Activate
    protected void activate(ComponentContext context) {
        applicationThreadExecutorProvider.getApplicationThreadExecutor().execute(()
                -> contentPaneProvider.getContentPane().setCenter(applicationContentProvider.getContentPane()));
    }

    @Deactivate
    protected void deactivate(ComponentContext context) {
        applicationThreadExecutorProvider.getApplicationThreadExecutor().execute(() -> {
            contentPaneProvider.getContentPane().setCenter(null);
            contentPaneProvider = null;
        });
    }

}
