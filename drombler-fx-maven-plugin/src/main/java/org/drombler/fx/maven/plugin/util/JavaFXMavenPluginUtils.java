package org.drombler.fx.maven.plugin.util;

/**
 *
 * @author puce
 */
public final class JavaFXMavenPluginUtils {

    public static final PluginProperty MAIN_CLASS_PROPERTY = new PluginProperty("mainClass", "jfx.mainClass");
    public static final PluginProperty JFX_MAIN_APP_JAR_NAME_PROPERTY = new PluginProperty("jfxMainAppJarName", "jfx.jfxMainAppJarName");
    public static final PluginProperty JFX_APP_OUTPUT_DIR_PROPERTY = new PluginProperty("jfxAppOutputDir", "jfx.jfxAppOutputDir");
    public static final PluginProperty JFX_BIN_DIR_PROPERTY = new PluginProperty("jfxBinDir", "jfx.jfxBinDir");
    public static final PluginProperty JFX_LIB_DIR_PROPERTY = new PluginProperty("jfxLibDir", "jfx.jfxLibDir");
    public static final PluginProperty UPDATE_EXISTING_JAR_PROPERTY = new PluginProperty("updateExistingJar", "jfx.updateExistingJar");
    public static final PluginProperty SKIP_COPY_DEPENDENCIES_TO_LIB_DIR_PROPERTY = new PluginProperty("skipCopyDependenciesToLibDir", "jfx.skipCopyDependenciesToLibDir");
    public static final PluginProperty ADDITIONAL_APP_RESOURCES_PROPERTY = new PluginProperty("additionalAppResources", "jfx.additionalAppResources");
    public static final PluginProperty COPY_ADDITIONAL_APP_RESOURCES_TO_JAR_PROPERTY = new PluginProperty("copyAdditionalAppResourcesToJar", "jfx.copyAdditionalAppResourcesToJar");
    public static final PluginProperty IDENTIFIER_PROPERTY = new PluginProperty("identifier", "jfx.identifier");
    public static final PluginProperty NATIVE_OUTPUT_DIR_PROPERTY = new PluginProperty("nativeOutputDir", "jfx.nativeOutputDir");
    public static final PluginProperty NATIVE_RELEASE_VERSION_PROPERTY = new PluginProperty("nativeReleaseVersion", "jfx.nativeReleaseVersion");
    public static final PluginProperty APP_NAME_PROPERTY = new PluginProperty("appName", "jfx.appName");
    public static final PluginProperty APP_FS_NAME_PROPERTY = new PluginProperty("appFsName", "jfx.appFsName");

}
