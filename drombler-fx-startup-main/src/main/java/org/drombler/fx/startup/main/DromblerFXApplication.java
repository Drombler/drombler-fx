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
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.drombler.acp.startup.main.DromblerACPStarter;
import org.drombler.acp.startup.main.MainWindowProvider;
import org.drombler.commons.client.startup.main.MissingPropertyException;
import org.drombler.fx.startup.main.impl.DefaultRootPane;
import org.osgi.framework.BundleContext;

/**
 * A Drombler FX application.<br>
 * <br>
 * It starts a {@link DromblerACPStarter} and registers the following OSGi services:
 * <ul>
 * <li>a {@link DromblerFXConfiguration}</li>
 * <li>a {@link MainWindowProvider} which provides the primary stage</li>
 * <li>the {@link HostServices}</li>
 * </ul>
 *
 * This class initializes a default scene and content which shows an infinite progress indicator.<br>
 * Make sure to either use a module which provides an implementation of {@code org.drombler.fx.core.application.MainSceneRootProvider} as an OSGi service or to provide and register your own
 * implementation.
 *
 * @author puce
 */
public class DromblerFXApplication extends Application {

    private MainWindowProvider<Stage> mainWindowProvider;
    private DromblerFXConfiguration configuration;
    private DromblerACPStarter<DromblerFXConfiguration> starter;

    // TODO: is this method still needed on Mac OS?
    /**
     * Runs the Drombler FX application.
     * @param args the command line args
     */
    public static final void main(String... args) {
        launch(args);
    }

    /**
     * Initializes a {@link DromblerACPStarter}.
     *
     * @throws URISyntaxException
     * @throws IOException
     * @throws MissingPropertyException
     * @throws Exception
     * @see DromblerACPStarter#init()
     */
    @Override
    public void init() throws URISyntaxException, IOException, MissingPropertyException, Exception {
        this.configuration = new DromblerFXConfiguration(getParameters());
        this.starter = new DromblerACPStarter<>(configuration);
        logInfo("Initializing JavaFX Application \"{0}\" ({1}x{2})...", getTitle(), getWidth(), getHeight());
        if (this.starter.init()) {
            logInfo("Initialized JavaFX Application \"{0}\"", getTitle());
        } else {
            Platform.exit();
        }
    }

    /**
     * Starts the application.<br>
     * <br>
     * Starts the previously initialized {@link DromblerACPStarter} and registers the following OSGi services:
     * <ul>
     * <li>a {@link DromblerFXConfiguration}</li>
     * <li>a {@link MainWindowProvider} which provides the primary stage</li>
     * <li>the {@link HostServices}</li>
     * </ul>
     *
     * @param primaryStage the primary stage
     * @see DromblerACPStarter#start()
     */
    @Override
    public void start(Stage primaryStage) {
        logInfo("Starting JavaFX Application \"{0}\"...", getTitle());

        primaryStage.setTitle(getTitle());
        final Scene scene = new Scene(new DefaultRootPane(), getWidth(), getHeight());

//        mainSceneProvider = () -> scene;
//        getBundleContext().registerService(MainSceneProvider.class, mainSceneProvider, null);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();

        mainWindowProvider = () -> primaryStage;
        getBundleContext().registerService(DromblerFXConfiguration.class, configuration, null);
        getBundleContext().registerService(MainWindowProvider.class, mainWindowProvider, null);
        getBundleContext().registerService(HostServices.class, getHostServices(), null);

        // Only start OSGi and register services such as ApplicationExecutorProvider once the JavaFX Platform has been started.
        starter.start();
        logInfo("Started JavaFX Application \"{0}\"", getTitle());
    }

    private BundleContext getBundleContext() {
        return starter.getFramework().getBundleContext();
    }

    /**
     * Stops the {@link DromblerACPStarter}.
     */
    @Override
    public void stop() {
        logInfo("Stopping JavaFX Application \"{0}\"...", getTitle());
        starter.stop();
        Platform.runLater(() -> {
            logInfo("Stopped JavaFX Application \"{0}\"", getTitle());
        });
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
