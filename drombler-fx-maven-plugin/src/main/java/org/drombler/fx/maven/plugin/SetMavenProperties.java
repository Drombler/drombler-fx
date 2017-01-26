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
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.drombler.fx.maven.plugin.util.JavaFXMavenPluginUtils;
import org.drombler.fx.maven.plugin.util.PathUtils;
import org.drombler.fx.startup.main.DromblerFXApplication;

@Mojo(name = "set-maven-properties", defaultPhase = LifecyclePhase.INITIALIZE)
public class SetMavenProperties extends AbstractDromblerMojo {

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

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        Path targetDirectoryPath = targetDirectory.toPath();
//        configureBundlePlugin();
        Optional<Plugin> javafxPlugin = findPlugin(JAVAFX_PLUGIN_COORDINATES);
//        Optional<Plugin> dromblerFxPlugin = findPlugin("org.drombler.fx", "drombler-fx-maven-plugin");
//        Optional<Plugin> dependencyPlugin = findPlugin("org.apache.maven.plugins", "maven-dependency-plugin");

        ensureProperty(JAVAFX_PLUGIN_COORDINATES, JavaFXMavenPluginUtils.MAIN_CLASS_PROPERTY_NAME, javafxPlugin, DromblerFXApplication.class.getName());
        ensureProperty(JAVAFX_PLUGIN_COORDINATES, JavaFXMavenPluginUtils.JFX_MAIN_APP_JAR_NAME_PROPERTY_NAME, javafxPlugin, PathUtils.getMainJarName(brandingId));
        ensureProperty(JAVAFX_PLUGIN_COORDINATES, JavaFXMavenPluginUtils.JFX_APP_OUTPUT_DIR_PROPERTY_NAME, javafxPlugin, targetDirectoryPath.resolve(PathUtils.BIN_DIR_NAME).toString());
        // TODO: this property doesn't seem to work (https://github.com/javafx-maven-plugin/javafx-maven-plugin/issues/256)
//        ensureProperty(JavaFXMavenPluginUtils.UPDATE_EXISTING_JAR_PROPERTY_NAME, javafxPlugin, Boolean.TRUE.toString());
//        ensureProperty(JavaFXMavenPluginUtils.MANIFEST_CLASSPATH_PROPERTY_NAME, javafxPlugin, Boolean.TRUE.toString());
        ensureProperty(JAVAFX_PLUGIN_COORDINATES, JavaFXMavenPluginUtils.COPY_DEPENDENCIES_TO_LIB_DIR_PROPERTY_NAME, javafxPlugin, Boolean.FALSE.toString());

//        ensureProperty(DependencyPluginUtils.FILE_SEPARATOR_PROPERTY_NAME, dependencyPlugin, MANIFEST_FILE_DELIMITER);
//        ensureProperty(DependencyPluginUtils.PATH_SEPARATOR_PROPERTY_NAME, dependencyPlugin, MANIFEST_PATH_DELIMITER);
//        ensureProperty(DependencyPluginUtils.PREFIX_PROPERTY_NAME, dependencyPlugin, "lib");
//        ensureProperty(DependencyPluginUtils.OUTPUT_PROPERTY_PROPERTY_NAME, dependencyPlugin, JavaFXMavenPluginUtils.MANIFEST_CLASSPATH_PROPERTY_NAME);
//        ensureProperty(DependencyPluginUtils.INCLUDE_SCOPE_PROPERTY_NAME, dependencyPlugin, "provided");
//        ensureProperty(DependencyPluginUtils.INCLUDE_ARTIFACT_IDS_PROPERTY_NAME, dependencyPlugin, "drombler-fx-startup-main");
//        ensureProperty(DependencyPluginUtils.ARTIFACT_PROPERTY_NAME, dependencyPlugin, "org.drombler.fx:drombler-fx-startup-main:" + "0.10-SNAPSHOT");

    }

//    private void configureBundlePlugin() {
//        // TODO: add drombler packaging to supportedProjectTypes of Maven Bundle Plugin
////        project.getProperties().setProperty("supportedProjectTypes", appCurrentVersion);
//        Optional<Plugin> findFirst = findPlugin("org.apache.felix", "maven-bundle-plugin");
//        if (findFirst.isPresent()) {
//            Plugin bundlePlugin = findFirst.get();
//            Object configuration = bundlePlugin.getConfiguration();
//            if (configuration == null) {
//                Xpp3Dom supportedProjectTypesDom = new Xpp3Dom("supportedProjectTypes");
//                supportedProjectTypesDom.addChild(createSupportedProjectTypesDom("jar"));
//                supportedProjectTypesDom.addChild(createSupportedProjectTypesDom("bundle"));
//                supportedProjectTypesDom.addChild(createSupportedProjectTypesDom("drombler-fx-application"));
//
//                Xpp3Dom configurationDom = new Xpp3Dom("configuration");
//                configurationDom.addChild(supportedProjectTypesDom);
//                bundlePlugin.setConfiguration(configurationDom);
//
//                configuration = configurationDom;
//            }
//            System.out.println("configuration: " + configuration);
//        } else {
//            getLog().info("plugin not found!");
//        }
//        Optional<Plugin> fxfindFirst = findPlugin("org.drombler.fx", "drombler-fx-maven-plugin");
//        if (findFirst.isPresent()) {
//            System.out.println("configuration type: " + fxfindFirst.get().getConfiguration().getClass());
//
//            System.out.println("configuration: " + fxfindFirst.get().getConfiguration());
//        } else {
//            getLog().info("plugin not found!");
//        }
//    }

//    private Xpp3Dom createSupportedProjectTypesDom(final String supportedProjectType) {
//        Xpp3Dom supportedProjectTypeDom = new Xpp3Dom("supportedProjectType");
//        supportedProjectTypeDom.setValue(supportedProjectType);
//        return supportedProjectTypeDom;
//    }

}
