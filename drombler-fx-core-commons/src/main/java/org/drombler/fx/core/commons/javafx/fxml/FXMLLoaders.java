/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drombler.fx.core.commons.javafx.fxml;

import java.io.IOException;
import java.io.InputStream;
import javafx.fxml.FXMLLoader;
import org.drombler.acp.core.commons.util.Resources;

/**
 *
 * @author puce
 */
public class FXMLLoaders {

    private FXMLLoaders() {
    }

    public static FXMLLoader createFXMLLoader(Class<?> type) {
        FXMLLoader loader = new FXMLLoader();
        loader.setClassLoader(type.getClassLoader());
        loader.setResources(Resources.getResourceBundle(type));// ResourceBundle.getBundle(type.getPackage().getName() + ".Bundle"));
        return loader;
    }

    public static Object load(final Object controller) throws IOException {
        return load(controller.getClass(), controller);
    }

    public static Object load(Class<?> type, final Object rootController) throws IOException {
        FXMLLoader loader = createFXMLLoader(type);
        loader.setRoot(rootController);
        loader.setController(rootController);
        return load(loader, type);
    }

    public static Object load(FXMLLoader loader, Class<?> type) throws IOException {
        try (InputStream is = getFXMLInputStream(type)) {
            return loader.load(is);
        }
    }

    private static InputStream getFXMLInputStream(Class<?> type) {
        return type.getResourceAsStream(type.getSimpleName() + ".fxml");
    }
}
