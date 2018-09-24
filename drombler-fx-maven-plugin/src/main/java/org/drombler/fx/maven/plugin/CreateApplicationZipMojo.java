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
import java.nio.file.Files;
import java.nio.file.Path;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.archiver.Archiver;
import org.codehaus.plexus.archiver.util.DefaultFileSet;
import org.codehaus.plexus.archiver.zip.ZipArchiver;

/**
 * Generates the JAP file.
 */
@Mojo(name = "create-application-zip", defaultPhase = LifecyclePhase.PACKAGE)
public class CreateApplicationZipMojo extends AbstractDromblerMojo {

    /**
     * The application ZIP file.
     */
    @Parameter(property = "dromblerfx.applicationZipFile",
            defaultValue = "${project.build.directory}/application.zip", required = true)
    private File applicationZipFile;

    /**
     * The target directory.
     */
    @Parameter(defaultValue = "${project.build.directory}", readonly = true, required = true)
    private File outputDir;


    @Component(role = Archiver.class, hint = "zip")
    private ZipArchiver zipArchiver;


    /**
     * {@inheritDoc }
     */
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            final Path targetDirectoryPath = getTargetDirectoryPath();
            if (!Files.exists(targetDirectoryPath)) {
                Files.createDirectories(targetDirectoryPath);
            }
            createApplicationZipFile();
        } catch (IOException ex) {
            throw new MojoExecutionException("Generating application resources failed!", ex);
        }
    }

    private void createApplicationZipFile() throws IOException, MojoExecutionException {
        Path outputDirPath = getOutputDir();
        Path targetDirectoryPath = getTargetDirectoryPath();
        if (Files.exists(targetDirectoryPath)) {
            getLog().info("Adding " + outputDirPath.relativize(targetDirectoryPath).toString() + " to application.zip ...");
            DefaultFileSet fileSet = new DefaultFileSet();
            fileSet.setDirectory(targetDirectoryPath.toFile());
            fileSet.setUsingDefaultExcludes(false);
            zipArchiver.addFileSet(fileSet);
        } else {
            throw new MojoExecutionException("targetDirectoryPath does not exist! Current value: " + targetDirectoryPath.toString());
        }
        zipArchiver.setDestFile(getApplicationZipFilePath().toFile());
        zipArchiver.createArchive();
    }

    /**
     * @return the applicationZipFile
     */
    public Path getApplicationZipFilePath() {
        return applicationZipFile.toPath();
    }

    /**
     * @return the outputDir
     */
    public Path getOutputDir() {
        return outputDir.toPath();
    }

}
