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
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.drombler.fx.maven.plugin.util.PathUtils;
import org.drombler.fx.startup.main.DromblerFXApplication;

@Mojo(name = "create-standalone-jar", defaultPhase = LifecyclePhase.PACKAGE)
public class CreateStandaloneJar extends AbstractDromblerMojo {
    private static final String MANIFEST_CLASSPATH_FILE_SEPARATOR = "/";
    private static final String MANIFEST_CLASSPATH_PATH_SEPARATOR = " ";

    /**
     * The branding id.
     */
    @Parameter(property = "dromblerfx.brandingId", required = true)
    private String brandingId;

    @Parameter(required = true, defaultValue = "${project.build.directory}/${project.build.finalName}.jar")
    private File srcJarFile;

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
                mainAttributes.putValue(Attributes.Name.MAIN_CLASS.toString(), DromblerFXApplication.class.getName());
                mainAttributes.putValue(Attributes.Name.CLASS_PATH.toString(), calculateManifestClasspathFromLibDir(binDirPath));

                try (BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(manifestPath, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE))) {
                    manifest.write(bos);
                }
            }
        }
    }

    private String calculateManifestClasspathFromLibDir(Path binDirPath) throws MojoExecutionException {
        Path libDirPath = binDirPath.resolve(PathUtils.LIB_DIR_NAME);

        try (Stream<Path> entryStream = Files.list(libDirPath)) {
            return calculateManifestClasspath(entryStream);
        } catch (IOException ex) {
            throw new MojoExecutionException("Error calculating Manifest Class-Path for application!", ex);
        }
    }

    private String calculateManifestClasspath(final Stream<Path> entryStream) {
        return entryStream
                .map(Path::getFileName)
                .map(Path::toString)
                .filter(fileName -> fileName.endsWith(".jar"))
                .map(fileName -> PathUtils.LIB_DIR_NAME + MANIFEST_CLASSPATH_FILE_SEPARATOR + fileName)
                .collect(Collectors.joining(MANIFEST_CLASSPATH_PATH_SEPARATOR));
    }
}
