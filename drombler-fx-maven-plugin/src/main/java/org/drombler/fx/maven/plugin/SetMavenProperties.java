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
import java.nio.file.Path;
import java.util.Optional;
import org.apache.maven.model.Plugin;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.drombler.fx.maven.plugin.util.JAPMavenPluginUtils;
import org.drombler.fx.maven.plugin.util.JavaFXMavenPluginUtils;
import org.drombler.fx.maven.plugin.util.PathUtils;
import org.drombler.fx.startup.main.DromblerFXApplication;

/**
 * Sets default values for some properties of other Maven Plugin (currently only the JavaFX Maven Plugin), if they aren't set yet.
 */
@Mojo(name = "set-maven-properties", defaultPhase = LifecyclePhase.INITIALIZE)
public class SetMavenProperties extends AbstractDromblerMojo {

    /**
     * The branding id.
     */
    @Parameter(property = "dromblerfx.brandingId", required = true)
    private String brandingId;

    /**
     * The application title.
     */
    @Parameter(property = "dromblerfx.title", required = true)
    private String title;

    /**
     * The application resource source directory.
     */
    @Parameter(property = "dromblerfx.appSourceDir", defaultValue = "${basedir}/src/main/app", required = true)
    private File appSourceDir;

    /**
     * The application ZIP file.
     */
    @Parameter(property = "dromblerfx.applicationZipFile",
            defaultValue = "${project.build.directory}/application.zip", required = true)
    private File applicationZipFile;

    /**
     * {@inheritDoc }
     */
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        Path targetDirectoryPath = getTargetDirectoryPath();

        setJAPMavenPluginProperties(targetDirectoryPath);
        setJavafxMavenPluginProperties(targetDirectoryPath);

    }

    private void setJAPMavenPluginProperties(Path targetDirectoryPath) {
        Optional<Plugin> japMavenPlugin = findPlugin(JAP_MAVEN_PLUGIN_COORDINATES);

        ensureMavenProperty(JAP_MAVEN_PLUGIN_COORDINATES, JAPMavenPluginUtils.APPLICATION_ZIP_FILE_PROPERTY, japMavenPlugin, applicationZipFile.getAbsolutePath());
    }

    private void setJavafxMavenPluginProperties(Path targetDirectoryPath) {
        Optional<Plugin> javafxMavenPlugin = findPlugin(JAVAFX_MAVEN_PLUGIN_COORDINATES);

        ensureMavenProperty(JAVAFX_MAVEN_PLUGIN_COORDINATES, JavaFXMavenPluginUtils.MAIN_CLASS_PROPERTY, javafxMavenPlugin, DromblerFXApplication.class.getName());
        ensureMavenProperty(JAVAFX_MAVEN_PLUGIN_COORDINATES, JavaFXMavenPluginUtils.JFX_MAIN_APP_JAR_NAME_PROPERTY, javafxMavenPlugin, PathUtils.getMainJarName(brandingId));
        ensureMavenProperty(JAVAFX_MAVEN_PLUGIN_COORDINATES, JavaFXMavenPluginUtils.JFX_APP_OUTPUT_DIR_PROPERTY, javafxMavenPlugin, targetDirectoryPath.toString());
        ensureMavenProperty(JAVAFX_MAVEN_PLUGIN_COORDINATES, JavaFXMavenPluginUtils.NATIVE_OUTPUT_DIR_PROPERTY, javafxMavenPlugin, targetDirectoryPath.resolveSibling("native").toString());
        ensureMavenProperty(JAVAFX_MAVEN_PLUGIN_COORDINATES, JavaFXMavenPluginUtils.JFX_BIN_DIR_PROPERTY, javafxMavenPlugin, PathUtils.BIN_DIR_NAME);
        ensureMavenProperty(JAVAFX_MAVEN_PLUGIN_COORDINATES, JavaFXMavenPluginUtils.JFX_LIB_DIR_PROPERTY, javafxMavenPlugin, PathUtils.LIB_DIR_NAME);

        // TODO: this property doesn't seem to work (https://github.com/javafx-maven-plugin/javafx-maven-plugin/issues/256)
        ensureMavenProperty(JAVAFX_MAVEN_PLUGIN_COORDINATES, JavaFXMavenPluginUtils.UPDATE_EXISTING_JAR_PROPERTY, javafxMavenPlugin, Boolean.TRUE.toString());
        ensureMavenProperty(JAVAFX_MAVEN_PLUGIN_COORDINATES, JavaFXMavenPluginUtils.SKIP_COPY_DEPENDENCIES_TO_LIB_DIR_PROPERTY, javafxMavenPlugin, Boolean.TRUE.toString());
        ensureMavenProperty(JAVAFX_MAVEN_PLUGIN_COORDINATES, JavaFXMavenPluginUtils.ADDITIONAL_APP_RESOURCES_PROPERTY, javafxMavenPlugin, appSourceDir.toString());
        ensureMavenProperty(JAVAFX_MAVEN_PLUGIN_COORDINATES, JavaFXMavenPluginUtils.COPY_ADDITIONAL_APP_RESOURCES_TO_JAR_PROPERTY, javafxMavenPlugin, String.valueOf(appSourceDir.exists()));

        ensureMavenProperty(JAVAFX_MAVEN_PLUGIN_COORDINATES, JavaFXMavenPluginUtils.IDENTIFIER_PROPERTY, javafxMavenPlugin, project.getGroupId() + "." + brandingId);
        ensureMavenProperty(JAVAFX_MAVEN_PLUGIN_COORDINATES, JavaFXMavenPluginUtils.NATIVE_RELEASE_VERSION_PROPERTY, javafxMavenPlugin, project.getVersion());
        ensureMavenProperty(JAVAFX_MAVEN_PLUGIN_COORDINATES, JavaFXMavenPluginUtils.APP_NAME_PROPERTY, javafxMavenPlugin, title);
        ensureMavenProperty(JAVAFX_MAVEN_PLUGIN_COORDINATES, JavaFXMavenPluginUtils.APP_FS_NAME_PROPERTY, javafxMavenPlugin, brandingId);
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
