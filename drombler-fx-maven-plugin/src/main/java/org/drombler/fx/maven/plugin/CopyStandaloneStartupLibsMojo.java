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
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.apache.maven.artifact.handler.manager.ArtifactHandlerManager;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.dependency.AbstractDependencyMojo;
import org.apache.maven.plugins.dependency.fromConfiguration.AbstractFromConfigurationMojo;
import org.apache.maven.plugins.dependency.fromConfiguration.ArtifactItem;
import org.apache.maven.plugins.dependency.fromConfiguration.CopyMojo;
import org.apache.maven.project.MavenProject;
import org.apache.maven.shared.artifact.resolve.ArtifactResolver;
import org.drombler.fx.maven.plugin.util.PathUtils;
import org.ops4j.pax.construct.util.ReflectMojo;

/**
 * Copies the startup libraries to the bin/lib directory. These libraries are on the classpath of the System Bundle and are not loaded as OSGi bundles.
 */
@Mojo(name = "copy-standalone-startup-libs", defaultPhase = LifecyclePhase.PREPARE_PACKAGE)
public class CopyStandaloneStartupLibsMojo extends AbstractMojo {

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


    @Parameter(defaultValue = "${session}", readonly = true, required = true)
    private MavenSession session;

    @Component
    private ArtifactHandlerManager artifactHandlerManager;

    @Component
    private ArtifactResolver artifactResolver;

    /**
     * {@inheritDoc }
     */
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            Path targetDirPath = targetDirectory.toPath();

            Path binDirPath = targetDirPath.resolve(PathUtils.BIN_DIR_NAME);

            copyStartupLibs(binDirPath);
        } catch (IOException ex) {
            throw new MojoExecutionException("Creating standalone zip failed!", ex);
        }
    }

    private void copyArtifacts(Path outputDirPath, boolean useRepositoryLayout, String excludeScope,
            List<ArtifactItem> artifactItems) throws MojoExecutionException, MojoFailureException {
        CopyMojo copyMojo = new CopyMojo();

        ReflectMojo reflectAbstractFromConfigurationMojo = new ReflectMojo(copyMojo, AbstractFromConfigurationMojo.class);
        reflectAbstractFromConfigurationMojo.setField("outputDirectory", outputDirPath.toFile());
        reflectAbstractFromConfigurationMojo.setField("artifactItems", artifactItems);
        reflectAbstractFromConfigurationMojo.setField("artifactHandlerManager", artifactHandlerManager);
        reflectAbstractFromConfigurationMojo.setField("artifactResolver", artifactResolver);

        ReflectMojo reflectAbstractDependencyMojo = new ReflectMojo(copyMojo, AbstractDependencyMojo.class);
        reflectAbstractDependencyMojo.setField("project", project);
        reflectAbstractDependencyMojo.setField("session", session);

        copyMojo.execute();
    }

    private void copyStartupLibs(Path binDirPath) throws IOException, MojoExecutionException, MojoFailureException {
        Path libDirPath = binDirPath.resolve(PathUtils.LIB_DIR_NAME);

//        List<Dependency> dependencies = project.getDependencies();
//        Dependency dromblerFXStartupMainDependency = dependencies.stream().filter(dependency
//                -> dependency.getGroupId().equals("org.drombler.fx")
//                && dependency.getArtifactId().equals("drombler-fx-startup-main")).
//                findFirst().
//                orElseThrow(() -> new MojoExecutionException("???"));
//
//        Set<Artifact> artifacts = project.getArtifacts();
//        Artifact dromblerFXStartupMainArtifact = artifacts.stream().
//                filter(artifact
//                        -> artifact.getGroupId().equals("org.drombler.fx")
//                && artifact.getArtifactId().equals("drombler-fx-startup-main")).
//                findFirst().
//                orElseThrow(() -> new MojoExecutionException("???"));
//        List<Artifact> libs = new ArrayList<>();
//        libs.add(dromblerFXStartupMainArtifact);
//        dromblerFXStartupMainArtifact.getDependencyTrail();
//        copyArtifacts(libDirPath, true, "system", createEndorsedArtifactsList());
        copyArtifacts(libDirPath, true, "system", createStartupArtifactItemList());
    }

//    private List<ArtifactItem> createEndorsedArtifactsList() {
//        ArtifactItem javaxAnnotationArtifactItem = new ArtifactItem();
//        javaxAnnotationArtifactItem.setGroupId("javax.annotation");
//        javaxAnnotationArtifactItem.setArtifactId("javax.annotation-api");
//        javaxAnnotationArtifactItem.setVersion("1.2");
//
//        return Arrays.asList(javaxAnnotationArtifactItem);
//    }
    private List<ArtifactItem> createStartupArtifactItemList() {
        List<ArtifactItem> startupArtifactItemList = new ArrayList<>();
        startupArtifactItemList.add(createArtifactItem("org.drombler.fx", "drombler-fx-startup-main"));

        // TODO: how to find the transitive dependencies automatically
        startupArtifactItemList.add(createArtifactItem("org.drombler.acp", "drombler-acp-startup-main"));
        startupArtifactItemList.add(createArtifactItem("org.drombler.commons", "drombler-commons-client-startup-main"));
        startupArtifactItemList.add(createArtifactItem("org.apache.felix", "org.apache.felix.framework"));

        return startupArtifactItemList;
    }

    private ArtifactItem createArtifactItem(String groupId, String artifactId) {
        ArtifactItem artifactItem = new ArtifactItem();
        artifactItem.setGroupId(groupId);
        artifactItem.setArtifactId(artifactId);
        return artifactItem;
    }
}
