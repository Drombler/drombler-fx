/*
 *         COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Notice
 *
 * The contents of this file are subject to the COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL)
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.opensource.org/licenses/cddl1.txt
 *
 * The Original Code is Drombler.org. The Initial Developer of the
 * Original Code is Florian Brunner (GitHub user: puce77).
 * Copyright 2015 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.fx.startup.main;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.List;
import java.util.Properties;
import javafx.application.Application;
import org.drombler.acp.startup.main.DromblerACPConfiguration;
import org.drombler.commons.client.startup.main.ApplicationConfiguration;
import org.drombler.commons.client.startup.main.MissingPropertyException;
import org.drombler.commons.client.startup.main.cli.CommandLineArgs;

/**
 * The Drombler FX configuration. Subclasses may add additional properties or change the configuration such as the application layout. Provides access to additional properties which are usually set by
 * the Drombler FX Maven Plugin.
 *
 * @see ApplicationConfiguration
 */
public class DromblerFXConfiguration extends DromblerACPConfiguration {

    /**
     * The property name of the branding id property in the applicationConfig.properties file.
     */
    public static final String APPLICATION_BRANDING_ID_PROPERTY_NAME = "drombler.application.brandingId";

    /**
     * The property name of the title property in the applicationConfig.properties file.
     */
    public static final String APPLICATION_TITLE_PROPERTY_NAME = "drombler.application.title";

    /**
     * The property name of the preferred width property in the applicationConfig.properties file.
     */
    public static final String APPLICATION_WIDTH_PROPERTY_NAME = "drombler.application.width";

    /**
     * The property name of the preferred height property in the applicationConfig.properties file.
     */
    public static final String APPLICATION_HEIGHT_PROPERTY_NAME = "drombler.application.height";

    /**
     * The system property name of the JavaFX version system property.
     */
    public static final String JAVAFX_VERSION_PROPERTY = "jfx.specification.version";

    /**
     * The JavaFX 8 version.
     */
    public static final String JAVAFX_VERSION_8 = "8";

    private static final String DEFAULT_APPLICATION_TITLE = "Drombler FX based Application";
    private static final double DEFAULT_APPLICATION_WIDTH = 1024;
    private static final double DEFAULT_APPLICATION_HEIGHT = 768;

    private final String applicationBrandingId;
    private final String applicationTitle;
    private final double applicationWidth;
    private final double applicationHeight;

    /**
     * Creates a new instance of this class.
     *
     * @param parameters the application parameters
     * @throws URISyntaxException
     * @throws IOException
     * @throws MissingPropertyException
     */
    public DromblerFXConfiguration(Application.Parameters parameters) throws URISyntaxException, IOException,
            MissingPropertyException {
        super(toCommandLineArgs(parameters));
        this.applicationBrandingId = determineBrandingId();
        this.applicationTitle = determineApplicationTitle();
        this.applicationWidth = determineApplicationWidth();
        this.applicationHeight = determineApplicationHeight();
    }

    private String determineBrandingId() {
        return getApplicationConfig().getStringProperty(APPLICATION_BRANDING_ID_PROPERTY_NAME);
    }

    private String determineApplicationTitle() {
        String title = getApplicationConfig().getStringProperty(APPLICATION_TITLE_PROPERTY_NAME);

        if (title == null) {
            title = DEFAULT_APPLICATION_TITLE;
        }
        return title;
    }

    private double determineApplicationWidth() {
        return getApplicationConfig().getPositiveDoubleProperty(APPLICATION_WIDTH_PROPERTY_NAME,
                DEFAULT_APPLICATION_WIDTH, "Application width is not a double: {}");
    }

    private double determineApplicationHeight() {
        return getApplicationConfig().getPositiveDoubleProperty(APPLICATION_HEIGHT_PROPERTY_NAME,
                DEFAULT_APPLICATION_HEIGHT, "Application height is not a double: {}");
    }

    private static CommandLineArgs toCommandLineArgs(Application.Parameters parameters) {
        final List<String> unnamedParameters = parameters.getUnnamed();
        return CommandLineArgs.parseCommandLineArgs(unnamedParameters.toArray(new String[unnamedParameters.size()]));
    }

    /**
     * {@inheritDoc }
     */
    @Override
    protected Properties loadDefaultConfigProps() throws IOException {
        Properties properties = new Properties(super.loadDefaultConfigProps());
        try (InputStream is = DromblerFXConfiguration.class.getResourceAsStream("config.properties")) {
            properties.load(is);
        }
        return properties;
    }

    /**
     * {@inheritDoc }
     * Sets the JavaFX version as an additional system property.
     *
     * @see #JAVAFX_VERSION_PROPERTY
     */
    @Override
    protected void loadSystemProperties(Path rootDirPath) throws MalformedURLException, IOException {
        super.loadSystemProperties(rootDirPath);

        // used by: org.osgi.framework.system.packages.extra
        if (System.getProperty(JAVAFX_VERSION_PROPERTY) == null) {
            System.setProperty(JAVAFX_VERSION_PROPERTY, JAVAFX_VERSION_8);
        }
    }

    /**
     * Gets the application branding id.
     *
     * @return the application branding id
     */
    public String getApplicationBrandingId() {
        return applicationBrandingId;
    }

    /**
     * Gets the application title.
     *
     * @return the application title
     */
    public String getApplicationTitle() {
        return applicationTitle;
    }

    /**
     * Gets the preferred application width.
     *
     * @return the preferred application width
     */
    public double getApplicationWidth() {
        return applicationWidth;
    }

    /**
     * Gets the preferred application height.
     *
     * @return the preferred application height
     */
    public double getApplicationHeight() {
        return applicationHeight;
    }

}
