package org.drombler.fx.maven.plugin;

import org.apache.maven.model.Plugin;

/**
 *
 * @author puce
 */


public class PluginCoordinates {

    private final String groupId;
    private final String artifactId;

    /**
     *
     * @param groupId
     * @param artifactId
     */
    public PluginCoordinates(String groupId, String artifactId) {
        this.groupId = groupId;
        this.artifactId = artifactId;
    }

    /**
     * @return the groupId
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * @return the artifactId
     */
    public String getArtifactId() {
        return artifactId;
    }

    public boolean matchesPlugin(Plugin plugin) {
        return plugin.getGroupId().equals(getGroupId()) && plugin.getArtifactId().equals(getArtifactId());
    }

    @Override
    public String toString() {
        return getGroupId() + ":" + getArtifactId();
    }

}
