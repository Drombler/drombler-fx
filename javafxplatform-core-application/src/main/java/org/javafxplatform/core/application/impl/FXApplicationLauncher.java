/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.application.impl;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.service.component.ComponentContext;
import org.richclientplatform.startup.main.ApplicationConfigProvider;

/**
 *
 * @author puce
 */
@Component(immediate = true)
public class FXApplicationLauncher {

//    @Property(value = "JavaFX Platform based Application")
    public static final String APPLICATION_TITLE_PROPERTY_NAME = "platform.application.title";
    @Reference
    private ApplicationConfigProvider applicationConfigProvider;

    protected void bindApplicationConfigProvider(ApplicationConfigProvider applicationConfigProvider) {
        this.applicationConfigProvider = applicationConfigProvider;
    }

    protected void unbindApplicationConfigProvider(ApplicationConfigProvider applicationConfigProvider) {
        this.applicationConfigProvider = null;
    }

    @Activate
    protected void activate(ComponentContext context) {
        Map<String, String> applicationConfig = applicationConfigProvider.getApplicationConfig();
        startJavaFXThread(context.getBundleContext(), applicationConfig.get(APPLICATION_TITLE_PROPERTY_NAME));
    }

    @Deactivate
    protected void deactivate(ComponentContext context) {
        stopJavaFXThread();
    }

    private void startJavaFXThread(final BundleContext context, final String applicationTitle) {
        Thread javaFXThread = Executors.defaultThreadFactory().newThread(new Runnable() {

            @Override
            public void run() {
                ModularApplication.launch(context, applicationTitle);
                shutdownFramework();
            }

            // TODO: better way?
            private void shutdownFramework() {
                try {
                    context.getBundle(0).stop();
                } catch (BundleException ex) {
                    Logger.getLogger(FXApplicationLauncher.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        javaFXThread.start();
    }

    private void stopJavaFXThread() {
        Platform.exit();
    }
}
