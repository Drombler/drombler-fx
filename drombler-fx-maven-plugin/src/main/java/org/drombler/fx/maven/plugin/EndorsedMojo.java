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

import java.nio.file.Paths;
import org.apache.maven.artifact.DefaultArtifact;
import org.apache.maven.artifact.handler.DefaultArtifactHandler;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;

/**
 *
 * @goal endorsed
 *
 * @phase validate
 */
// TODO: NOT USED YET!
public class EndorsedMojo extends AbstractMojo {

    /**
     * The Maven project.
     *
     * @parameter default-value="${project}" @required @readonly
     */
    private MavenProject project;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
//        getLog().debug(System.getenv("JAVAFX_HOME"));
//        DefaultArtifact artifact = new DefaultArtifact("javafx", "jfxrt", "2.0", "system", "jar", null, new DefaultArtifactHandler("jar"));
//        artifact.setFile(Paths.get(System.getenv("JAVAFX_HOME"), "rt", "lib", "jfxrt.jar").toFile());
//        project.getDependencyArtifacts().add(artifact);
        System.getProperties().setProperty("java.endorsed.dirs", Paths.get(System.getenv("JAVAFX_HOME"), "rt", "lib").toString());

    }
}
