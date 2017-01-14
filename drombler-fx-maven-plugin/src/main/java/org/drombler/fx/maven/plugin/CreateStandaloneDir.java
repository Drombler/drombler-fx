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
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.drombler.acp.startup.main.DromblerACPConfiguration;
import org.drombler.fx.maven.plugin.util.PathUtils;

@Mojo(name = "create-standalone-dir", defaultPhase = LifecyclePhase.INITIALIZE)
public class CreateStandaloneDir extends AbstractMojo {

    /**
     * The target directory.
     */
    @Parameter(property = "dromblerfx.targetDirectory",
            defaultValue = "${project.build.directory}/deployment/standalone", required = true)
    private File targetDirectory;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            Path targetDirPath = targetDirectory.toPath();

            ensureDirExists(targetDirPath);
            ensureDirExists(targetDirPath.resolve(PathUtils.BIN_DIR_NAME));
            ensureDirExists(targetDirPath.resolve(DromblerACPConfiguration.CONFIG_DIRECTORY));
            ensureDirExists(targetDirPath.resolve(PathUtils.BUNDLE_DIR_NAME));

        } catch (IOException ex) {
            throw new MojoExecutionException("Creating standalone dir failed!", ex);
        }
    }

    private void ensureDirExists(Path dirPath) throws IOException {
        if (!Files.exists(dirPath)) {
            getLog().info("Creating directories: " + dirPath);
            Files.createDirectories(dirPath);
        }
    }

}
