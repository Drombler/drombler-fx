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
 * Copyright 2017 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.fx.maven.plugin;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.drombler.commons.client.startup.main.ApplicationConfiguration;
import org.drombler.fx.startup.main.DromblerFXConfiguration;

/**
 * Generates the Drombler FX application resources (currently only: applicationConfig.properties) in the ${project.build.outputDirectory} to be packaged in the application JAR.
 */
@Mojo(name = "generate-application-resources", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class GenerateApplicationResourcesMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project.build.outputDirectory}", readonly = true, required = true)
    private File outputDirectory;

    /**
     * The branding id.
     */
    @Parameter(property = "dromblerfx.brandingId", required = true)
    private String brandingId;

    /**
     * The application title.
     */
    @Parameter(property = "dromblerfx.title", required = true)
    private String title;

    /**
     * The default application width.
     */
    @Parameter(property = "dromblerfx.width", required = true)
    private double width;

    /**
     * The default application height.
     */
    @Parameter(property = "dromblerfx.height", required = true)
    private double height;

    /**
     * The default port used for a single instance application or null if this is not a single instance application (default). The port must be between 0 and 65535, inclusive. A port number of 0 means
     * that the port number is automatically allocated, typically from an ephemeral port range.
     */
    @Parameter(property = "dromblerfx.defaultSingleInstancePort")
    private Integer defaultSingleInstancePort;

    /**
     * {@inheritDoc }
     */
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            final Path outputDirPath = outputDirectory.toPath();
            if (!Files.exists(outputDirPath)) {
                Files.createDirectories(outputDirPath);
            }
            createApplicationConfigProperties(outputDirPath.resolve(ApplicationConfiguration.APPLICATION_PROPERTIES_FILE_PATH_RELATIVE));
        } catch (IOException ex) {
            throw new MojoExecutionException("Generating application resources failed!", ex);
        }
    }

    private void createApplicationConfigProperties(Path applicationConfigPropertiesPath) throws IOException {
        getLog().info("Generating application resource: " + applicationConfigPropertiesPath);

        Properties applicationConfigProperties = new Properties();
        applicationConfigProperties.setProperty(DromblerFXConfiguration.APPLICATION_BRANDING_ID_PROPERTY_NAME, brandingId);
        applicationConfigProperties.setProperty(DromblerFXConfiguration.APPLICATION_TITLE_PROPERTY_NAME, title);
        applicationConfigProperties.setProperty(DromblerFXConfiguration.APPLICATION_WIDTH_PROPERTY_NAME, Double.toString(width));
        applicationConfigProperties.setProperty(DromblerFXConfiguration.APPLICATION_HEIGHT_PROPERTY_NAME, Double.toString(height));
        if (defaultSingleInstancePort != null && defaultSingleInstancePort > 0 && defaultSingleInstancePort <= 65535) {
            applicationConfigProperties.setProperty(
                    ApplicationConfiguration.APPLICATION_DEFAULT_SINGLE_INSTANCE_PORT_PROPERTY_NAME,
                    Integer.toString(defaultSingleInstancePort));
        }

        try (OutputStream os = Files.newOutputStream(applicationConfigPropertiesPath)) {
            applicationConfigProperties.store(os, null);
        }
    }

}
