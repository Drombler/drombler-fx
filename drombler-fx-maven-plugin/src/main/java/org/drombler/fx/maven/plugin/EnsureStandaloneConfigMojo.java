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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.drombler.commons.client.startup.main.DromblerClientConfiguration;

/**
 * Ensures the application config files exist in the conf directory and contain the necessary info.
 */
@Mojo(name = "ensure-standalone-config", defaultPhase = LifecyclePhase.PACKAGE)
public class EnsureStandaloneConfigMojo extends AbstractDromblerMojo {

    private static final Path RELATIVE_CONFIG_PROPERTIES_FILE_PATH
            = Paths.get(DromblerClientConfiguration.CONFIG_DIRECTORY_NAME, DromblerClientConfiguration.CONFIG_PROPERTIES_FILE_NAME);
    private static final Path RELATIVE_SYSTEM_PROPERTIES_FILE_PATH
            = Paths.get(DromblerClientConfiguration.CONFIG_DIRECTORY_NAME, DromblerClientConfiguration.SYSTEM_PROPERTIES_FILE_NAME);
    /**
     * The branding id.
     */
    @Parameter(property = "dromblerfx.brandingId", required = true)
    private String brandingId;

    /**
     * The default user directory. The user can change the value.
     */
    // TODO: good solution using "${dollar}"?
    @Parameter(property = "dromblerfx.userdir", defaultValue = "${dollar}{user.home}/.${brandingId}/${project.version}",
            required = true)
    private String userdir;

    /**
     * {@inheritDoc }
     */
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            Path targetDirPath = getTargetDirectoryPath();

            ensureFileExists(targetDirPath, RELATIVE_SYSTEM_PROPERTIES_FILE_PATH);
            ensureConfigPropertiesFileExists(targetDirPath);

        } catch (IOException ex) {
            throw new MojoExecutionException("Ensuring standalone zip failed!", ex);
        }
    }

    private void ensureFileExists(Path targetDirPath, final Path relativeFilePath) throws IOException {
        Path targetFilePath = targetDirPath.resolve(relativeFilePath);
        if (!Files.exists(targetFilePath)) {
            Files.createFile(targetFilePath);
        }
    }

    private void ensureConfigPropertiesFileExists(Path targetDirPath) throws IOException {
        Path targetFilePath = targetDirPath.resolve(RELATIVE_CONFIG_PROPERTIES_FILE_PATH);
        if (Files.exists(targetFilePath)) {
            Properties configProperties = loadProperties(targetFilePath);
            completeConfigProperties(configProperties, targetFilePath);
        } else {
            writeConfigPropertiesFile(targetFilePath);
        }
    }

    private void completeConfigProperties(Properties configProperties, Path targetFilePath) throws IOException {
        if (!configProperties.containsKey(DromblerClientConfiguration.USER_DIR_PROPERTY)) {
            addUserDirProperty(configProperties);
            writeProperties(configProperties, targetFilePath);
        }
    }

    private void addUserDirProperty(Properties configProperties) {
        userdir = userdir.replace("${brandingId}", brandingId);
        configProperties.setProperty(DromblerClientConfiguration.USER_DIR_PROPERTY, userdir);
    }

    private void writeConfigPropertiesFile(Path targetConfigPropertiesFile) throws IOException {
        Properties configProperties = new Properties();
        addUserDirProperty(configProperties);
        writeProperties(configProperties, targetConfigPropertiesFile);
    }

    private void writeProperties(Properties properties, Path propertiesFilePath) throws IOException {
        try (BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(propertiesFilePath))) {
            properties.store(bos, "");
        }
    }

    private Properties loadProperties(Path targetFilePath) throws IOException {
        Properties properties = new Properties();
        try (BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(targetFilePath))) {
            properties.load(bis);
        }
        return properties;
    }


}
