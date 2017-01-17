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
import java.nio.file.Path;
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
import org.drombler.fx.maven.plugin.util.JavaFXMavenPluginUtils;
import org.drombler.fx.maven.plugin.util.PathUtils;
import org.drombler.fx.startup.main.DromblerFXApplication;

@Mojo(name = "set-maven-properties", defaultPhase = LifecyclePhase.INITIALIZE)
public class SetMavenProperties extends AbstractMojo {

    /**
     * The branding id.
     */
    @Parameter(property = "dromblerfx.brandingId", required = true)
    private String brandingId;

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

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        Path targetDirectoryPath = targetDirectory.toPath();
//        configureBundlePlugin();
        Optional<Plugin> javafxPlugin = findPlugin("com.zenjava", "javafx-maven-plugin");
        Optional<Plugin> dromblerFxPlugin = findPlugin("org.drombler.fx", "drombler-fx-maven-plugin");

        ensureProperty(JavaFXMavenPluginUtils.MAIN_CLASS_PROPERTY_NAME, javafxPlugin, DromblerFXApplication.class.getName());
        ensureProperty(JavaFXMavenPluginUtils.JFX_MAIN_APP_JAR_NAME_PROPERTY_NAME, javafxPlugin, PathUtils.getMainJarName(brandingId));
        ensureProperty(JavaFXMavenPluginUtils.JFX_APP_OUTPUT_DIR_PROPERTY_NAME, javafxPlugin, targetDirectoryPath.toString());

    }

    private void ensureProperty(String propertyName, Optional<Plugin> javafxPlugin, String defaultValue) {
        Optional<String> propertyValue = getPropertyValue(propertyName, javafxPlugin);
        if (propertyValue.isPresent()) {
            getLog().info("Found property '" + propertyName + "' = '" + propertyValue.get() + "'");
        } else {
            getLog().info("Setting property '" + propertyName + "' = '" + defaultValue + "'");
            project.getProperties().setProperty(propertyName, defaultValue);
        }
    }

    private Optional<String> getPropertyValue(final String propertyName, Optional<Plugin> plugin) {
        Optional<String> propertyValue = Optional.empty();
        if (plugin.isPresent()) {
            propertyValue = getPluginConfigurationPropertyValue(plugin.get(), propertyName);
        }
        if (!propertyValue.isPresent() && project.getProperties().containsKey(propertyName)) {
            propertyValue = Optional.of(project.getProperties().getProperty(propertyName));
        }
        return propertyValue;
    }

    private void configureBundlePlugin() {
        // TODO: add drombler packaging to supportedProjectTypes of Maven Bundle Plugin
//        project.getProperties().setProperty("supportedProjectTypes", appCurrentVersion);
        Optional<Plugin> findFirst = findPlugin("org.apache.felix", "maven-bundle-plugin");
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
        Optional<Plugin> fxfindFirst = findPlugin("org.drombler.fx", "drombler-fx-maven-plugin");
        if (findFirst.isPresent()) {
            System.out.println("configuration type: " + fxfindFirst.get().getConfiguration().getClass());

            System.out.println("configuration: " + fxfindFirst.get().getConfiguration());
        } else {
            getLog().info("plugin not found!");
        }
    }

    private Optional<Plugin> findPlugin(String pluginGroupId, String pluginArtifactId) {
        return project.getBuild().getPlugins().stream()
                .filter(plugin -> plugin.getGroupId().equals(pluginGroupId) && plugin.getArtifactId().equals(pluginArtifactId))
                .findFirst();
    }

    private Xpp3Dom createSupportedProjectTypesDom(final String supportedProjectType) {
        Xpp3Dom supportedProjectTypeDom = new Xpp3Dom("supportedProjectType");
        supportedProjectTypeDom.setValue(supportedProjectType);
        return supportedProjectTypeDom;
    }

    private Optional<String> getPluginConfigurationPropertyValue(Plugin plugin, String propertyName) {
        if (plugin.getConfiguration() instanceof Xpp3Dom) {
            Xpp3Dom configurationDom = (Xpp3Dom) plugin.getConfiguration();
            final Xpp3Dom child = configurationDom.getChild(propertyName);
            if (child != null) {
                return Optional.ofNullable(child.getValue());
            } else {
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    }
}
