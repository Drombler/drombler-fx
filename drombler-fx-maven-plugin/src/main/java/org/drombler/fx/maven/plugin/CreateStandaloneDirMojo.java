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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.drombler.commons.client.startup.main.DromblerClientConfiguration;
import org.drombler.fx.maven.plugin.util.PathUtils;

/**
 * Creates the directory structure of a Drombler FX application.
 */
@Mojo(name = "create-standalone-dir", defaultPhase = LifecyclePhase.INITIALIZE)
public class CreateStandaloneDirMojo extends AbstractDromblerMojo {

    /**
     * {@inheritDoc }
     */
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            Path targetDirPath = getTargetDirectoryPath();

            ensureDirExists(targetDirPath);
            Path binDirPath = targetDirPath.resolve(PathUtils.BIN_DIR_NAME);
            ensureDirExists(binDirPath);
            ensureDirExists(binDirPath.resolve(PathUtils.LIB_DIR_NAME));
            ensureDirExists(targetDirPath.resolve(DromblerClientConfiguration.CONFIG_DIRECTORY_NAME));
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
