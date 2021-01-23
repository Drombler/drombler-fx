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

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.drombler.fx.maven.plugin.util.PathUtils;
import org.drombler.fx.startup.main.DromblerFXClasspathLauncher;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import static org.drombler.fx.maven.plugin.util.ModuleNameUtils.calculateAutomaticModuleName;

/**
 * Creates the executable main application JAR in the bin directory.
 */
@Mojo(name = "create-standalone-jar", defaultPhase = LifecyclePhase.PACKAGE)
public class CreateStandaloneJarMojo extends AbstractDromblerMojo {

    /**
     * The branding id.
     */
    @Parameter(property = "dromblerfx.brandingId", required = true)
    private String brandingId;

    @Parameter(required = true, defaultValue = "${project.build.directory}/${project.build.finalName}.jar")
    private File srcJarFile;

    /**
     * The Maven project.
     */
    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    private MavenProject project;

    /**
     * {@inheritDoc }
     */
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            if (!srcJarFile.exists()) {
                throw new MojoExecutionException("The srcJarFile does not exist: " + srcJarFile);
            }
            Path binDirPath = PathUtils.resolveBinDirPath(getTargetDirectoryPath());
            createMainJar(binDirPath);
        } catch (IOException ex) {
            throw new MojoExecutionException("Error creating the main JAR file!", ex);
        }
    }

    private void createMainJar(Path binDirPath) throws MojoExecutionException, IOException {
        Path mainJarPath = binDirPath.resolve(brandingId + ".jar");

        //            CopyMojo copyMojo = new CopyMojo();
        //
        //            ReflectMojo reflectAbstractFromDependenciesMojo = new ReflectMojo(copyMojo,
        //                    AbstractFromDependenciesMojo.class);
        //            reflectAbstractFromDependenciesMojo.setField("artifactItems",
        //                    Arrays.asList(new ArtifactItem(new Artifact() {
        //            })))
        //            copyMojo.execute();
        if (!Files.exists(mainJarPath)) {
            Files.copy(srcJarFile.toPath(), mainJarPath);

            try (FileSystem jarFS = FileSystems.newFileSystem(mainJarPath, null)) {
                Path manifestPath = jarFS.getPath("/META-INF/MANIFEST.MF");
                Manifest manifest = new Manifest();
                try (BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(manifestPath))) {
                    manifest.read(bis);
                }

                Attributes mainAttributes = manifest.getMainAttributes();
                mainAttributes.putValue(Attributes.Name.MAIN_CLASS.toString(), DromblerFXClasspathLauncher.class.getName());
                mainAttributes.putValue("Automatic-Module-Name", calculateAutomaticModuleName(project.getGroupId(), project.getArtifactId()));

                try (BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(manifestPath, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE))) {
                    manifest.write(bos);
                }
            }
        }
    }

}
