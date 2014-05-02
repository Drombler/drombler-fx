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
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.Properties;
import org.drombler.acp.startup.main.impl.CommandLineArgs;

/**
 *
 * @author puce
 */
public class Main extends org.drombler.acp.startup.main.impl.Main {
    public static final String JAVAFX_VERSION_PROPERTY = "jfx.specification.version";
    public static final String JAVAFX_VERSION_8 = "8";
    
    /**
     * <p>
     * This method performs the main task of constructing an framework instance
     * and starting its execution. The following functions are performed
     * when invoked:
     * </p>
     * <ol>
     * <li><i><b>Examine and verify command-line arguments.</b></i> The launcher
     * accepts a "<tt>-b</tt>" command line switch to set the bundle auto-deploy
     * directory and a single argument to set the bundle cache directory.
     * </li>
     * <li><i><b>Read the system properties file.</b></i> This is a file
     * containing properties to be pushed into <tt>System.setProperty()</tt>
     * before starting the framework. This mechanism is mainly shorthand
     * for people starting the framework from the command line to avoid having
     * to specify a bunch of <tt>-D</tt> system property definitions.
     * The only properties defined in this file that will impact the framework's
     * behavior are the those concerning setting HTTP proxies, such as
     * <tt>http.proxyHost</tt>, <tt>http.proxyPort</tt>, and
     * <tt>http.proxyAuth</tt>. Generally speaking, the framework does
     * not use system properties at all.
     * </li>
     * <li><i><b>Read the framework's configuration property file.</b></i> This is
     * a file containing properties used to configure the framework
     * instance and to pass configuration information into
     * bundles installed into the framework instance. The configuration
     * property file is called <tt>config.properties</tt> by default
     * and is located in the <tt>conf/</tt> directory of the Felix
     * installation directory, which is the parent directory of the
     * directory containing the <tt>felix.jar</tt> file. It is possible
     * to use a different location for the property file by specifying
     * the desired URL using the <tt>felix.config.properties</tt>
     * system property; this should be set using the <tt>-D</tt> syntax
     * when executing the JVM. If the <tt>config.properties</tt> file
     * cannot be found, then default values are used for all configuration
     * properties. Refer to the
     * <a href="Felix.html#Felix(java.util.Map)"><tt>Felix</tt></a>
     * constructor documentation for more information on framework
     * configuration properties.
     * </li>
     * <li><i><b>Copy configuration properties specified as system properties
     * into the set of configuration properties.</b></i> Even though the
     * Felix framework does not consult system properties for configuration
     * information, sometimes it is convenient to specify them on the command
     * line when launching Felix. To make this possible, the Felix launcher
     * copies any configuration properties specified as system properties
     * into the set of configuration properties passed into Felix.
     * </li>
     * <li><i><b>Add shutdown hook.</b></i> To make sure the framework shutdowns
     * cleanly, the launcher installs a shutdown hook; this can be disabled
     * with the <tt>felix.shutdown.hook</tt> configuration property.
     * </li>
     * <li><i><b>Create and initialize a framework instance.</b></i> The OSGi standard
     * <tt>FrameworkFactory</tt> is retrieved from <tt>META-INF/services</tt>
     * and used to create a framework instance with the configuration properties.
     * </li>
     * <li><i><b>Auto-deploy bundles.</b></i> All bundles in the auto-deploy
     * directory are deployed into the framework instance.
     * </li>
     * <li><i><b>Start the framework.</b></i> The framework is started and
     * the launcher thread waits for the framework to shutdown.
     * </li>
     * </ol>
     * <p>
     * It should be noted that simply starting an instance of the framework is not
     * enough to create an interactive session with it. It is necessary to install
     * and start bundles that provide a some means to interact with the framework;
     * this is generally done by bundles in the auto-deploy directory or specifying
     * an "auto-start" property in the configuration property file. If no bundles
     * providing a means to interact with the framework are installed or if the
     * configuration property file cannot be found, the framework will appear to
     * be hung or deadlocked. This is not the case, it is executing correctly,
     * there is just no way to interact with it.
     * </p>
     * <p>
     * The launcher provides two ways to deploy bundles into a framework at
     * startup, which have associated configuration properties:
     * </p>
     * <ul>
     * <li>Bundle auto-deploy - Automatically deploys all bundles from a
     * specified directory, controlled by the following configuration
     * properties:
     * <ul>
     * <li><tt>felix.auto.deploy.dir</tt> - Specifies the auto-deploy directory
     * from which bundles are automatically deploy at framework startup.
     * The default is the <tt>bundle/</tt> directory of the current directory.
     * </li>
     * <li><tt>felix.auto.deploy.action</tt> - Specifies the auto-deploy actions
     * to be found on bundle JAR files found in the auto-deploy directory.
     * The possible actions are <tt>install</tt>, <tt>update</tt>,
     * <tt>start</tt>, and <tt>uninstall</tt>. If no actions are specified,
     * then the auto-deploy directory is not processed. There is no default
     * value for this property.
     * </li>
     * </ul>
     * </li>
     * <li>Bundle auto-properties - Configuration properties which specify URLs
     * to bundles to install/start:
     * <ul>
     * <li><tt>felix.auto.install.N</tt> - Space-delimited list of bundle
     * URLs to automatically install when the framework is started,
     * where <tt>N</tt> is the start level into which the bundle will be
     * installed (e.g., felix.auto.install.2).
     * </li>
     * <li><tt>felix.auto.start.N</tt> - Space-delimited list of bundle URLs
     * to automatically install and start when the framework is started,
     * where <tt>N</tt> is the start level into which the bundle will be
     * installed (e.g., felix.auto.start.2).
     * </li>
     * </ul>
     * </li>
     * </ul>
     * <p>
     * These properties should be specified in the <tt>config.properties</tt>
     * so that they can be processed by the launcher during the framework
     * startup process.
     * </p>
     *
     * @param args Accepts arguments to set the auto-deploy directory and/or
     * the bundle cache directory.
     * @throws Exception If an error occurs.
     *
     */
    public static void main(String[] args) throws Exception {
        CommandLineArgs commandLineArgs = CommandLineArgs.parseCommandLineArgs(args);
        Main main = new Main();
        main.start(commandLineArgs);
    }

    @Override
    protected Properties getDefaultConfigProps() throws IOException {
        Properties properties = new Properties(super.getDefaultConfigProps());
        try (InputStream is = Main.class.getResourceAsStream("config.properties")) {
            properties.load(is);
        }
        return properties;
    }

    @Override
    protected void loadSystemProperties(Path rootDirPath) throws MalformedURLException, IOException {
        super.loadSystemProperties(rootDirPath);
            
        // used by: org.osgi.framework.system.packages.extra
        if (System.getProperty(JAVAFX_VERSION_PROPERTY) == null) {
            System.setProperty(JAVAFX_VERSION_PROPERTY, JAVAFX_VERSION_8);
        }
    }
    
    
}
