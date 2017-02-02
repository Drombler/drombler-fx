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
     * The application resource source directory.
     */
    @Parameter(property = "dromblerfx.appSourceDir", defaultValue = "${basedir}/src/main/app", required = true)
    private File appSourceDir;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        Path targetDirectoryPath = getTargetDirectoryPath();

        Optional<Plugin> javafxPlugin = findPlugin(JAVAFX_PLUGIN_COORDINATES);

        ensureMavenProperty(JAVAFX_PLUGIN_COORDINATES, JavaFXMavenPluginUtils.MAIN_CLASS_PROPERTY_NAME, javafxPlugin, DromblerFXApplication.class.getName());
        ensureMavenProperty(JAVAFX_PLUGIN_COORDINATES, JavaFXMavenPluginUtils.JFX_MAIN_APP_JAR_NAME_PROPERTY_NAME, javafxPlugin, PathUtils.getMainJarName(brandingId));
        ensureMavenProperty(JAVAFX_PLUGIN_COORDINATES, JavaFXMavenPluginUtils.JFX_APP_OUTPUT_DIR_PROPERTY_NAME, javafxPlugin, targetDirectoryPath.toString());
        ensureMavenProperty(JAVAFX_PLUGIN_COORDINATES, JavaFXMavenPluginUtils.JFX_BIN_DIR_PROPERTY_NAME, javafxPlugin, PathUtils.BIN_DIR_NAME);
        ensureMavenProperty(JAVAFX_PLUGIN_COORDINATES, JavaFXMavenPluginUtils.JFX_LIB_DIR_PROPERTY_NAME, javafxPlugin, PathUtils.LIB_DIR_NAME);

        // TODO: this property doesn't seem to work (https://github.com/javafx-maven-plugin/javafx-maven-plugin/issues/256)
        ensureMavenProperty(JAVAFX_PLUGIN_COORDINATES, JavaFXMavenPluginUtils.UPDATE_EXISTING_JAR_PROPERTY_NAME, javafxPlugin, Boolean.TRUE.toString());
        ensureMavenProperty(JAVAFX_PLUGIN_COORDINATES, JavaFXMavenPluginUtils.SKIP_COPY_DEPENDENCIES_TO_LIB_DIR_PROPERTY_NAME, javafxPlugin, Boolean.TRUE.toString());
        ensureMavenProperty(JAVAFX_PLUGIN_COORDINATES, JavaFXMavenPluginUtils.ADDITIONAL_APP_RESOURCES_PROPERTY_NAME, javafxPlugin, appSourceDir.toString());
        ensureMavenProperty(JAVAFX_PLUGIN_COORDINATES, JavaFXMavenPluginUtils.COPY_ADDITIONAL_APP_RESOURCES_TO_JAR_PROPERTY_NAME, javafxPlugin, String.valueOf(appSourceDir.exists()));


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
