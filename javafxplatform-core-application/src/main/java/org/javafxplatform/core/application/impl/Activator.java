package org.javafxplatform.core.application.impl;

import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;

public class Activator implements BundleActivator {

    @Override
    public void start(BundleContext context) {
        startJavaFXThread(context);
    }

    @Override
    public void stop(BundleContext context) {
        stopJavaFXThread();
    }

    private void startJavaFXThread(final BundleContext context) {
        Thread javaFXThread = Executors.defaultThreadFactory().newThread(new Runnable() {

            @Override
            public void run() {
                ModularApplication.launch(context);
                shutdownFramework();
            }

            // TODO: better way?
            private void shutdownFramework() {
                try {
                    context.getBundle(0).stop();
                } catch (BundleException ex) {
                    Logger.getLogger(Activator.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        javaFXThread.start();
    }

    private void stopJavaFXThread() {
        Platform.exit();
    }
}
