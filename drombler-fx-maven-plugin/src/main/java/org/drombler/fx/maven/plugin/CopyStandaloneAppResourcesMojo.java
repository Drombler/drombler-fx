package org.drombler.fx.maven.plugin;

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
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.softsmithy.lib.nio.file.CopyFileVisitor;

@Mojo(name = "copy-standalone-app-resources", defaultPhase = LifecyclePhase.PREPARE_PACKAGE,
        requiresDependencyResolution = ResolutionScope.COMPILE_PLUS_RUNTIME)
public class CopyStandaloneAppResourcesMojo extends AbstractMojo {

    /**
     * The target directory.
     */
    @Parameter(property = "dromblerfx.targetDirectory",
            defaultValue = "${project.build.directory}/deployment/standalone", required = true)
    private File targetDirectory;

    @Parameter(property = "dromblerfx.appSourceDir", defaultValue = "${basedir}/src/main/app", required = true)
    private File appSourceDir;


    @Override
    public void execute() throws MojoExecutionException {
        try {
            Path targetDirPath = targetDirectory.toPath();
            copyAppDir(targetDirPath);
        } catch (IOException ex) {
            throw new MojoExecutionException("Copying standalone app resources failed!", ex);
        }
    }

    private void copyAppDir(Path targetDirPath) throws IOException {
        Path appSourceDirPath = appSourceDir.toPath();
        if (Files.exists(appSourceDirPath) && Files.isDirectory(appSourceDirPath)) {
            CopyFileVisitor.copy(appSourceDirPath, targetDirPath, StandardCopyOption.COPY_ATTRIBUTES);
        }
    }

}
