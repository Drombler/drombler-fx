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

import java.util.Optional;
import org.apache.maven.model.Plugin;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.xml.Xpp3Dom;

@Mojo(name = "set-maven-properties", defaultPhase = LifecyclePhase.INITIALIZE)
public class SetMavenProperties extends AbstractMojo {

    /**
     * The Maven project.
     */
    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    private MavenProject project;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        // TODO: add drombler packaging to supportedProjectTypes of Maven Bundle Plugin
//        project.getProperties().setProperty("supportedProjectTypes", appCurrentVersion);
        Optional<Plugin> findFirst = project.getBuild().getPlugins().stream().filter(plugin -> plugin.getArtifactId().equals("maven-bundle-plugin")).findFirst();
        if (findFirst.isPresent()) {
            Plugin bundlePlugin = findFirst.get();
            Object configuration = bundlePlugin.getConfiguration();
            if (configuration == null) {
                Xpp3Dom supportedProjectTypesDom = new Xpp3Dom("supportedProjectTypes");
                supportedProjectTypesDom.addChild(createSupportedProjectTypesDom("jar"));
                supportedProjectTypesDom.addChild(createSupportedProjectTypesDom("bundle"));
                supportedProjectTypesDom.addChild(createSupportedProjectTypesDom("drombler-fx-application"));

                Xpp3Dom configurationDom = new Xpp3Dom("configuration");
                configurationDom.addChild(supportedProjectTypesDom);
                bundlePlugin.setConfiguration(configurationDom);

                configuration = configurationDom;
            }
            System.out.println("configuration: " + configuration);
        } else {
            getLog().info("plugin not found!");
        }
        Optional<Plugin> fxfindFirst = project.getBuild().getPlugins().stream().filter(plugin -> plugin.getArtifactId().equals("drombler-fx-maven-plugin")).findFirst();
        if (findFirst.isPresent()) {
            System.out.println("configuration type: " + fxfindFirst.get().getConfiguration().getClass());

            System.out.println("configuration: " + fxfindFirst.get().getConfiguration());
        } else {
            getLog().info("plugin not found!");
        }

    }

    private Xpp3Dom createSupportedProjectTypesDom(final String supportedProjectType) {
        Xpp3Dom supportedProjectTypeDom = new Xpp3Dom("supportedProjectType");
        supportedProjectTypeDom.setValue(supportedProjectType);
        return supportedProjectTypeDom;
    }

}
