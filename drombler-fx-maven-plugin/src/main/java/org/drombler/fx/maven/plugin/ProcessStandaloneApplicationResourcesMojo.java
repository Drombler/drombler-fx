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
package org.drombler.fx.maven.plugin;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Map;
import java.util.Properties;
import org.apache.maven.artifact.factory.ArtifactFactory;
import org.apache.maven.artifact.installer.ArtifactInstaller;
import org.apache.maven.artifact.repository.ArtifactRepositoryFactory;
import org.apache.maven.artifact.repository.layout.ArtifactRepositoryLayout;
import org.apache.maven.artifact.resolver.ArtifactResolver;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.archiver.Archiver;
import org.codehaus.plexus.archiver.zip.ZipArchiver;
import org.drombler.acp.startup.main.DromblerACPConfiguration;
import org.softsmithy.lib.nio.file.CopyFileVisitor;

@Mojo(name = "process-standalone-application-resources", defaultPhase = LifecyclePhase.PROCESS_RESOURCES)
public class ProcessStandaloneApplicationResourcesMojo extends AbstractMojo {

    private static final Path RELATIVE_CONFIG_PROPERTIES_FILE_PATH
            = Paths.get(DromblerACPConfiguration.CONFIG_DIRECTORY, DromblerACPConfiguration.CONFIG_PROPERTIES_FILE_NAME);

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
     * The user directory.
     */
    // TODO: good solution using "${dollar}"?
    @Parameter(property = "dromblerfx.userdir", defaultValue = "${dollar}{user.home}/.${brandingId}/${project.version}",
            required = true)
    private String userdir;
    /**
     * The target directory.
     */
    @Parameter(property = "dromblerfx.targetDirectory",
            defaultValue = "${project.build.directory}/deployment/standalone", required = true)
    private File targetDirectory;
    /**
     * The application resource source directory.
     */
    @Parameter(property = "dromblerfx.appSourceDir", defaultValue = "${basedir}/src/main/app", required = true)
    private File appSourceDir;

    /**
     * The default port used for a single instance application or null if this is not a single instance application (default). The port must be between 0 and 65535, inclusive. A port number of 0 means
     * that the port number is automatically allocated, typically from an ephemeral port range.
     */
    @Parameter(property = "dromblerfx.defaultSingleInstancePort")
    private Integer defaultSingleInstancePort;

    /**
     * The Maven project.
     */
    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    private MavenProject project;

    @Component
    private ArtifactRepositoryFactory artifactRepositoryFactory;

    @Component(role = ArtifactRepositoryLayout.class)
    private Map<String, ArtifactRepositoryLayout> artifactRepositoryLayouts;

    @Component
    private ArtifactInstaller artifactInstaller;

    @Component
    private ArtifactFactory artifactFactory;

    @Component
    private ArtifactResolver artifactResolver;

    @Component(role = Archiver.class, hint = "zip")
    private ZipArchiver zipArchiver;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            Path targetDirPath = targetDirectory.toPath();

            copyAppDir(targetDirPath);

            ensureFileExists(targetDirPath, Paths.get(DromblerACPConfiguration.CONFIG_DIRECTORY,
                    DromblerACPConfiguration.SYSTEM_PROPERTIES_FILE_NAME));
            ensureConfigPropertiesFileExists(targetDirPath);


        } catch (IOException ex) {
            throw new MojoExecutionException("Creating standalone zip failed!", ex);
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
        if (!Files.exists(targetFilePath)) {
            writeConfigPropertiesFile(targetFilePath);
        }
    }

    private void writeConfigPropertiesFile(Path targetConfigPropertiesFile) throws IOException {
        userdir = userdir.replace("${brandingId}", brandingId);
        Properties configProperties = new Properties();
        configProperties.setProperty(DromblerACPConfiguration.USER_DIR_PROPERTY, userdir);
        Path configPropertiesFilePath = getAppSourceDirPath().resolve(RELATIVE_CONFIG_PROPERTIES_FILE_PATH);
        if (Files.exists(configPropertiesFilePath)) {
            try (BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(configPropertiesFilePath))) {
                configProperties.load(bis);
            }
        }
        writeProperties(configProperties, targetConfigPropertiesFile);
    }

    private void writeProperties(Properties properties, Path propertiesFilePath) throws IOException {
        try (BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(propertiesFilePath))) {
            properties.store(bos, "");
        }
    }

    private void copyAppDir(Path targetDirPath) throws IOException {
        Path appSourceDirPath = getAppSourceDirPath();
        if (Files.exists(appSourceDirPath) && Files.isDirectory(appSourceDirPath)) {
            FileVisitor<Path> copyFileVisitor = new CopyFileVisitor(appSourceDirPath, targetDirPath) {

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (appSourceDirPath.relativize(file).equals(RELATIVE_CONFIG_PROPERTIES_FILE_PATH)) {
                        writeConfigPropertiesFile(targetDirPath.resolve(RELATIVE_CONFIG_PROPERTIES_FILE_PATH));
                        return FileVisitResult.CONTINUE;
                    } else {
                        return super.visitFile(file, attrs);
                    }
                }

            };
            Files.walkFileTree(appSourceDirPath, copyFileVisitor);
        }
    }

    private Path getAppSourceDirPath() {
        return appSourceDir.toPath();
    }

}
