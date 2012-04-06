/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.util.javafx.fxml;

import java.io.IOException;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.util.Builder;
import javafx.util.BuilderFactory;
import javafx.util.Callback;
import org.richclientplatform.core.util.Resources;

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

    public static Object load(Class<?> type, final Object controller) throws IOException {
        FXMLLoader loader = createFXMLLoader(type);
        loader.setControllerFactory(new Callback<Class<?>, Object>() {

            @Override
            public Object call(Class<?> p) {
                if (p.equals(controller.getClass())) {
                    return controller;
                } else {
                    return null;
                }
            }
        });
        return load(loader, type);
    }

    public static Object load(FXMLLoader loader, Class<?> type) throws IOException {
        return loader.load(type.getResourceAsStream(type.getSimpleName() + ".fxml"));
    }
}
