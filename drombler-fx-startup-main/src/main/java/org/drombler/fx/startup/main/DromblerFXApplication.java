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
package org.drombler.fx.startup.main;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.drombler.acp.startup.main.ApplicationExecutorProvider;
import org.drombler.acp.startup.main.DromblerACPStarter;
import org.drombler.acp.startup.main.MainWindowProvider;
import org.drombler.acp.startup.main.MissingPropertyException;
import org.drombler.fx.startup.main.impl.DefaultRootPane;
import org.drombler.fx.startup.main.impl.FXApplicationExecutorProvider;
import org.osgi.framework.BundleContext;

/**
 *
 * @author puce
 */
public class DromblerFXApplication extends Application {

    private final FXApplicationExecutorProvider fxApplicationExecutorProvider = new FXApplicationExecutorProvider();
//    private MainSceneProvider mainSceneProvider;
    private MainWindowProvider<Stage> mainWindowProvider;
    private DromblerFXConfiguration configuration;
    private DromblerACPStarter starter;


    // TODO: is this method still needed on Mac OS?
    public static final void main(String... args) {
        launch(args);
    }

    @Override
    public void init() throws URISyntaxException, IOException, MissingPropertyException, Exception {
        this.configuration = new DromblerFXConfiguration(getParameters());
        this.starter = new DromblerACPStarter(configuration);
        logInfo("Initializing JavaFX Application \"{0}\" ({1}x{2})...", getTitle(), getWidth(), getHeight());
        this.starter.init();
        logInfo("Initialized JavaFX Application \"{0}\"", getTitle());
    }

    @Override
    public void start(Stage stage) {
        logInfo("Starting JavaFX Application \"{0}\"...", getTitle());

        stage.setTitle(getTitle());
        final Scene scene = new Scene(new DefaultRootPane(), getWidth(), getHeight());

//        mainSceneProvider = () -> scene;
//        getBundleContext().registerService(MainSceneProvider.class, mainSceneProvider, null);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();

        mainWindowProvider = () -> stage;
        getBundleContext().registerService(MainWindowProvider.class, mainWindowProvider, null);
        // Only register the ApplicationExecutorProvider once the JavaFX Platform has been started.
        getBundleContext().registerService(ApplicationExecutorProvider.class,
                fxApplicationExecutorProvider, null);
        starter.start();
        logInfo("Started JavaFX Application \"{0}\"", getTitle());
    }

    private BundleContext getBundleContext() {
        return starter.getFramework().getBundleContext();
    }

    @Override
    public void stop() {
        logInfo("Stopping JavaFX Application \"{0}\"...", getTitle());
        starter.stop();
        logInfo("Stopped JavaFX Application \"{0}\"", getTitle());
    }

    private double getWidth() {
        return configuration.getApplicationWidth();
    }

    private double getHeight() {
        return configuration.getApplicationHeight();
    }

    private String getTitle() {
        return configuration.getApplicationTitle();
    }

    private void logInfo(String messageFormat, Object... arguments) {
        // TODO: replace with SLF4J Logger once available on classpath
        // Note: the message format is different!
        System.out.println(MessageFormat.format(messageFormat, arguments));
    }

    private void logError(Exception ex) {
        // TODO: replace with SLF4J Logger once available on classpath
        // Note: the message format is different!
        ex.printStackTrace();
    }
}
