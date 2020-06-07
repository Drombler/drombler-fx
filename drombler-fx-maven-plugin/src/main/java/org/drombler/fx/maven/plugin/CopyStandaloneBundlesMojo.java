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

import org.apache.maven.artifact.repository.layout.ArtifactRepositoryLayout;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.*;
import org.apache.maven.plugins.dependency.AbstractDependencyMojo;
import org.apache.maven.plugins.dependency.fromDependencies.AbstractDependencyFilterMojo;
import org.apache.maven.plugins.dependency.fromDependencies.AbstractFromDependenciesMojo;
import org.apache.maven.plugins.dependency.fromDependencies.CopyDependenciesMojo;
import org.apache.maven.project.MavenProject;
import org.apache.maven.shared.transfer.artifact.install.ArtifactInstaller;
import org.apache.maven.shared.transfer.artifact.resolve.ArtifactResolver;
import org.apache.maven.shared.transfer.repository.RepositoryManager;
import org.drombler.fx.maven.plugin.util.PathUtils;
import org.ops4j.pax.construct.util.ReflectMojo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

/**
 * Copies the application dependencies to the bundle directory.
 */
@Mojo(name = "copy-standalone-bundles", defaultPhase = LifecyclePhase.PREPARE_PACKAGE,
        requiresDependencyResolution = ResolutionScope.COMPILE_PLUS_RUNTIME)
public class CopyStandaloneBundlesMojo extends AbstractMojo {

    /**
     * The target directory.
     */
    @Parameter(property = "dromblerfx.targetDirectory",
            defaultValue = "${project.build.directory}/deployment/standalone", required = true)
    private File targetDirectory;

    /**
     * The Maven project.
     */
    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    private MavenProject project;

    @Component(role = ArtifactRepositoryLayout.class)
    private Map<String, ArtifactRepositoryLayout> artifactRepositoryLayouts;

    @Component
    private ArtifactInstaller artifactInstaller;


    /**
     * The Maven session
     */
    @Parameter(defaultValue = "${session}", readonly = true, required = true)
    private MavenSession session;

    @Component
    private RepositoryManager repositoryManager;

    @Component
    private ArtifactResolver artifactResolver;

    /**
     * {@inheritDoc }
     */
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            Path targetDirPath = targetDirectory.toPath();
            copyBundles(targetDirPath);
        } catch (IOException ex) {
            throw new MojoExecutionException("Creating standalone zip failed!", ex);
        }
    }

    private void copyBundles(Path targetDirPath) throws MojoExecutionException, IOException, MojoFailureException {
        Path bundleDirPath = targetDirPath.resolve(PathUtils.BUNDLE_DIR_NAME);

        CopyDependenciesMojo copyDependenciesMojo = new CopyDependenciesMojo();

        ReflectMojo reflectCopyDependenciesMojo = new ReflectMojo(copyDependenciesMojo, CopyDependenciesMojo.class);
        reflectCopyDependenciesMojo.setField("repositoryLayouts", artifactRepositoryLayouts);
        reflectCopyDependenciesMojo.setField("installer", artifactInstaller);
        reflectCopyDependenciesMojo.setField("copyPom", false);

        ReflectMojo reflectAbstractFromDependenciesMojo = new ReflectMojo(copyDependenciesMojo,
                AbstractFromDependenciesMojo.class);
        reflectAbstractFromDependenciesMojo.setField("outputDirectory", bundleDirPath.toFile());
        reflectAbstractFromDependenciesMojo.setField("useRepositoryLayout", true);

        ReflectMojo reflectAbstractDependencyFilterMojoMojo = new ReflectMojo(copyDependenciesMojo,
                AbstractDependencyFilterMojo.class);
        reflectAbstractDependencyFilterMojoMojo.setField("excludeScope", "system");
        reflectAbstractDependencyFilterMojoMojo.setField("repositoryManager", repositoryManager);
        reflectAbstractDependencyFilterMojoMojo.setField("artifactResolver", artifactResolver);

        ReflectMojo reflectAbstractDependencyMojo = new ReflectMojo(copyDependenciesMojo,
                AbstractDependencyMojo.class);
        reflectAbstractDependencyMojo.setField("project", project);
        reflectAbstractDependencyMojo.setField("session", session);

        copyDependenciesMojo.execute();
    }

}
