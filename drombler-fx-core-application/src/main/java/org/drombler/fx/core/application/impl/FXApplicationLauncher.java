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

import java.util.Map;
import java.util.concurrent.Executors;
import javafx.application.Platform;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Reference;
import org.drombler.acp.startup.main.ApplicationConfigProvider;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author puce
 */
@Component(immediate = true)
public class FXApplicationLauncher {

    private static final Logger LOG = LoggerFactory.getLogger(FXApplicationLauncher.class);

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
                        "Application width is not a double: {}"),
                getApplicationConfigDouble(applicationConfig, APPLICATION_HEIGHT_PROPERTY_NAME, -1,
                        "Application height is not a double: {}"));
    }

    private double getApplicationConfigDouble(Map<String, String> applicationConfig, String key, double defaultValue,
            String errorMessageFormat) {
        double value = defaultValue;
        if (applicationConfig.containsKey(key)) {
            try {
                value = Double.parseDouble(applicationConfig.get(key));
            } catch (NumberFormatException ex) {
                LOG.error(errorMessageFormat, applicationConfig.get(key));
            }
        }
        return value;
    }

    @Deactivate
    protected void deactivate(ComponentContext context) {
        stopJavaFXThread();
    }

    private void startJavaFXThread(final BundleContext context, final String applicationTitle,
            final double applicationWidth,
            final double applicationHeight) {
        Thread javaFXThread = Executors.defaultThreadFactory().newThread(new Runnable() {

            @Override
            public void run() {
                LOG.info("Launching JavaFX Application...");
                ModularApplication.launch(context, applicationTitle, applicationWidth, applicationHeight);
                shutdownFramework();
            }

            // TODO: better way?
            private void shutdownFramework() {
                try {
                    LOG.info("Stopping OSGi framework...");
                    context.getBundle(0).stop();
                } catch (BundleException ex) {
                    LOG.error("An Exception occured while stopping the OSGi framework...", ex);
                }
            }
        });
        javaFXThread.start();
    }

    private void stopJavaFXThread() {
        Platform.exit();
        LOG.info("Exited JavaFX Platform.");
    }
}
