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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.maven.model.Plugin;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import static org.drombler.fx.maven.plugin.AbstractDromblerMojo.JAVAFX_PLUGIN_COORDINATES;
import org.drombler.fx.maven.plugin.util.JavaFXMavenPluginUtils;
import org.drombler.fx.maven.plugin.util.PathUtils;

@Mojo(name = "build-standalone-manifest-classpath", defaultPhase = LifecyclePhase.PREPARE_PACKAGE)
public class BuildStandaloneManifestClasspathMojo extends AbstractDromblerMojo {
    private static final String MANIFEST_PATH_DELIMITER = " ";
    private static final String MANIFEST_FILE_DELIMITER = "/";
    /**
     * The target directory.
     */
    @Parameter(property = "dromblerfx.targetDirectory",
            defaultValue = "${project.build.directory}/deployment/standalone", required = true)
    private File targetDirectory;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            Path libDirPath = PathUtils.resolveLibDirPath(targetDirectory.toPath());
            try (Stream<Path> entryStream = Files.list(libDirPath)) {
                String manifestClasspath = getManifestClasspath(entryStream);
                Optional<Plugin> javafxPlugin = findPlugin(JAVAFX_PLUGIN_COORDINATES);
                ensureProperty(JAVAFX_PLUGIN_COORDINATES, JavaFXMavenPluginUtils.MANIFEST_CLASSPATH_PROPERTY_NAME, javafxPlugin, manifestClasspath);
            }

        } catch (IOException ex) {
            throw new MojoExecutionException("Building standalone Manifest Class-Path failed!", ex);
        }
    }

    private String getManifestClasspath(final Stream<Path> entryStream) {
        return entryStream
                .map(Path::getFileName)
                .map(Path::toString)
                .filter(fileName -> fileName.endsWith(".jar"))
                .map(fileName -> PathUtils.LIB_DIR_NAME + MANIFEST_FILE_DELIMITER + fileName)
                .collect(Collectors.joining(MANIFEST_PATH_DELIMITER));
    }
}
