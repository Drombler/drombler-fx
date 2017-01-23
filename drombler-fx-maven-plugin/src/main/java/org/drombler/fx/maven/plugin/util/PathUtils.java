package org.drombler.fx.maven.plugin.util;

/**
 *
 * @author puce
 */
public class PathUtils {

    public static final String BIN_DIR_NAME = "bin";
    public static final String BUNDLE_DIR_NAME = "bundle";
    public static final String LIB_DIR_NAME = "lib";

    public static String getMainJarName(String brandingId) {
        return brandingId + ".jar";
    }

}
