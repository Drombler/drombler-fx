/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projectx.maven.plugins.pxb.util;

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
            doCopyMainClasses(resourceURI, targetDir);
        }
    }

    private static void doCopyMainClasses(URI resourceURI, Path targetDir) throws IOException {
        CopyFileVisitor.copy(Paths.get(resourceURI), targetDir);
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
