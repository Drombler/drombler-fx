/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.application.impl;

import java.util.concurrent.Executor;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Reference;
import org.drombler.acp.core.application.ApplicationExecutorProvider;
import org.javafxplatform.core.application.ApplicationContentProvider;
import org.osgi.service.component.ComponentContext;

/**
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
        this.contentPaneProvider = null;
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
        initContentPane();
    }

    @Deactivate
    protected void deactivate(ComponentContext context) {
        uninitContentPane();
    }

    private void initContentPane() {
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                contentPaneProvider.getContentPane().setCenter(applicationContentProvider.getContentPane());
            }
        };
        applicationExecutor.execute(runnable);
    }

    private void uninitContentPane() {
        //TODO needed?
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                contentPaneProvider.getContentPane().setCenter(null);
            }
        };
        applicationExecutor.execute(runnable);
    }
}
