package org.drombler.fx.maven.plugin;

import java.io.File;
import java.nio.file.Path;
import org.drombler.fx.maven.plugin.util.PluginCoordinates;
import java.util.Optional;
import org.apache.maven.model.Plugin;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.xml.Xpp3Dom;

/**
 *
 * @author puce
 */


public abstract class AbstractDromblerMojo extends AbstractMojo {

    protected static final PluginCoordinates JAVAFX_PLUGIN_COORDINATES = new PluginCoordinates("com.zenjava", "javafx-maven-plugin");

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
    protected MavenProject project;

    protected void ensureMavenProperty(PluginCoordinates pluginCoordinates, String propertyName, Optional<Plugin> plugin, String defaultValue) {
        Optional<String> propertyValue = getPropertyValue(propertyName, plugin);
        if (propertyValue.isPresent()) {
            getLog().info("Plugin (" + pluginCoordinates + "): Found property '" + propertyName + "' = '" + propertyValue.get() + "'");
        } else {
            getLog().info("Plugin (" + pluginCoordinates + "): Setting property '" + propertyName + "' = '" + defaultValue + "'");
            project.getProperties().setProperty(propertyName, defaultValue);
        }
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

    protected Optional<Plugin> findPlugin(PluginCoordinates pluginCoordinates) {
        return project.getBuild().getPlugins().stream()
                .filter(pluginCoordinates::matchesPlugin)
                .findFirst();
    }

    /**
     * @return the targetDirectory
     */
    public Path getTargetDirectoryPath() {
        return targetDirectory.toPath();
    }

}
