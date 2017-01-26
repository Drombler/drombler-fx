package org.drombler.fx.maven.plugin.util;

import java.nio.file.Path;

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

    public static Path resolveBinDirPath(Path targetDir) {
        return targetDir.resolve(BIN_DIR_NAME);
    }

    public static Path resolveLibDirPath(Path targetDir) {
        return resolveBinDirPath(targetDir).resolve(LIB_DIR_NAME);
    }

}
