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
package org.drombler.fx.maven.plugin.util;

import com.sun.javafx.tools.ant.FXJar;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import org.softsmithy.lib.nio.file.CopyFileVisitor;
import org.softsmithy.lib.nio.file.JarFiles;

/**
 *
 * @author puce
 */
public class FXUtils {

    public static void copyMainClasses(Path targetDir) throws IOException {
        try {
            URI resourceURI = FXJar.class.getResource("/resources/classes").toURI();
            doCopyMainClassesWorkaround(resourceURI, targetDir);
        } catch (URISyntaxException ex) {
            // should not happen here
            throw new IllegalArgumentException(ex);
        }

    }

    private static void doCopyMainClassesWorkaround(URI resourceURI, Path targetDir) throws IOException {
        // The file system needs to be created explicitly. Bug?
        try (FileSystem jarFS = JarFiles.newJarFileSystem(JarFiles.getJarURI(resourceURI))) {
            CopyFileVisitor.copy(Paths.get(resourceURI), targetDir);
        }
    }

    public static Map<String, String> getManifestEntries(String mainClass) {
        Map<String, String> manifestEntries = new LinkedHashMap<>(3);
        manifestEntries.put("JavaFX-Version", getJavaFXVersion());
        manifestEntries.put("JavaFX-Application-Class", mainClass);
        manifestEntries.put("Main-Class", "com/javafx/main/Main");
        return manifestEntries;
    }

    public static String getJavaFXVersion() {
        return "2.2";
    }
}
