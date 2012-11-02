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
    public static final String APPLICATION_WIDTH_PROPERTY_NAME = "platform.application.width";
    public static final String APPLICATION_HEIGHT_PROPERTY_NAME = "platform.application.height";
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

        startJavaFXThread(context.getBundleContext(),
                applicationConfig.get(APPLICATION_TITLE_PROPERTY_NAME),
                getApplicationConfigDouble(applicationConfig, APPLICATION_WIDTH_PROPERTY_NAME, -1,
                "Application width is not a double: {0}"),
                getApplicationConfigDouble(applicationConfig, APPLICATION_HEIGHT_PROPERTY_NAME, -1,
                "Application height is not a double: {0}"));
    }

    private double getApplicationConfigDouble(Map<String, String> applicationConfig, String key, double defaultValue, String errorMessageFormat) {
        double value = defaultValue;
        if (applicationConfig.containsKey(key)) {
            try {
                value = Double.parseDouble(applicationConfig.get(key));
            } catch (Exception ex) {
                Logger.getLogger(FXApplicationLauncher.class.getName()).log(Level.SEVERE,
                        errorMessageFormat, applicationConfig.get(key));
            }
        }
        return value;
    }

    @Deactivate
    protected void deactivate(ComponentContext context) {
        stopJavaFXThread();
    }

    private void startJavaFXThread(final BundleContext context, final String applicationTitle, final double applicationWidth,
            final double applicationHeight) {
        Thread javaFXThread = Executors.defaultThreadFactory().newThread(new Runnable() {

            @Override
            public void run() {
                ModularApplication.launch(context, applicationTitle, applicationWidth, applicationHeight);
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
