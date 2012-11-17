/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drombler.fx.core.commons.fx.fxml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import org.drombler.acp.core.commons.util.Resources;
import org.softsmithy.lib.util.ResourceFileNotFoundException;

/**
 * Utility methods for {@link FXMLLoader}.
 * @author puce
 */
public class FXMLLoaders {

    private static final String FXML_EXTENSION = ".fxml";

    private FXMLLoaders() {
    }

    /**
     * Creates a new {@link FXMLLoader}. <br/> <br/>
     *  Sets:
     * <ul>
     *   <li>the {@link ClassLoader} to the ClassLoader of the specified type</li>
     *   <li>the {@link ResourceBundle} by looking for a {@code  Bundle.properties} file in the package of the specified type (or a locale specific derivation) using the default {@link Locale}</li>
     * </ul>
     * @param type the type specifing the {@link ClassLoader} and the package of the {@code  Bundle.properties} file
     * @return a {@link FXMLLoader}
     */
    public static FXMLLoader createFXMLLoader(Class<?> type) {
        FXMLLoader loader = new FXMLLoader();
        loader.setClassLoader(type.getClassLoader());
        loader.setResources(Resources.getResourceBundle(type));
        return loader;
    }
    /**
     * Loads the &lt;class name&gt;.fxml file, where &lt;class name&gt; is the type of the specified rootController and
     * the FXML-file is expected to be in the same package.
     *  <br/> <br/>
     *  Sets:
     * <ul>
     *   <li>the {@link ClassLoader} to the ClassLoader of the type of the rootController</li>
     *   <li>the {@link ResourceBundle} by looking for a {@code  Bundle.properties} file in the package of the type of 
     * the specified rootController (or a locale specific derivation) using the default {@link Locale}</li>
     * </ul>
     * 
     * The root element of the FXML document is expected to be:
     * <br/> <br/>
     * {@code  <fx:root type="{super-type}" xmlns:fx="http://javafx.com/fxml">}
     * <br/> <br/>
     * where "super-type" is the super type of the type of the specified rootController.
     * @param rootController the Object acting as the root and as the controller.
     * @throws IOException 
     */
    public static void loadRoot(final Object rootController) throws IOException {
        loadRoot(rootController.getClass(), rootController);
    }

    /**
     * Loads the &lt;class name&gt;.fxml file, which is expected to be in the same package as the specified type.
     *  <br/> <br/>
     *  Sets:
     * <ul>
     *   <li>the {@link ClassLoader} to the ClassLoader of the specified type</li>
     *   <li>the {@link ResourceBundle} by looking for a {@code  Bundle.properties} file in the package of the specified
     *   type (or a locale specific derivation) using the default {@link Locale}</li>
     * </ul>
     * 
     * The root element of the FXML document is expected to be:
     *  <br/> <br/>
     * {@code  <fx:root type="{super-type}" xmlns:fx="http://javafx.com/fxml">}
     *  <br/> <br/>
     * where "super-type" is the super type of the specified type.
     * @param type the type
     * @param rootController the Object acting as the root and as the controller.
     * @throws IOException 
     */
    public static void loadRoot(Class<?> type, final Object rootController) throws IOException {
        FXMLLoader loader = createFXMLLoader(type);
        loader.setRoot(rootController);
        loader.setController(rootController);
        load(loader, type);
    }

    /**
     * Loads the &lt;class name&gt;.fxml file, which is expected to be in the same package as the specified type.
     * 
     * @param loader the {@link FXMLLoader}
     * @param type the type
     * @return the loaded object
     * @throws IOException 
     */
    public static Object load(FXMLLoader loader, Class<?> type) throws IOException {
        try (InputStream is = getFXMLInputStream(type)) {
            if (is == null) {
                // avoid NullPointerException
                throw new ResourceFileNotFoundException(getAbsoluteFxmlResourcePath(type));
            }
            return loader.load(is);
        }
    }

    private static InputStream getFXMLInputStream(Class<?> type) {
        return type.getResourceAsStream(getFxmlFileName(type));
    }

    private static String getAbsoluteFxmlResourcePath(Class<?> type) {
        return "/" + type.getName().replace(".", "/") + FXML_EXTENSION;
    }

    private static String getFxmlFileName(Class<?> type) {
        return type.getSimpleName() + FXML_EXTENSION;
    }
}
