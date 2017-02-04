package org.drombler.fx.maven.plugin.util;

/**
 *
 * @author puce
 */
public class PluginProperty {

    private final String configurationPropertyName;
    private final String mavenPropertyName;

    public PluginProperty(String configurationPropertyName, String mavenPropertyName) {
        this.configurationPropertyName = configurationPropertyName;
        this.mavenPropertyName = mavenPropertyName;
    }

    /**
     * @return the configurationPropertyName
     */
    public String getConfigurationPropertyName() {
        return configurationPropertyName;
    }

    /**
     * @return the mavenPropertyName
     */
    public String getMavenPropertyName() {
        return mavenPropertyName;
    }

    @Override
    public String toString() {
        return "PluginProperty[" + "configurationPropertyName=" + configurationPropertyName + ", mavenPropertyName=" + mavenPropertyName + ']';
    }

}
