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
package org.drombler.fx.core.commons.fx.fxml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.util.BuilderFactory;
import org.apache.commons.lang3.StringUtils;
import org.drombler.acp.core.commons.util.Resources;
import org.softsmithy.lib.util.ResourceFileNotFoundException;

/**
 * Utility methods for {@link FXMLLoader}.
 *
 * @author puce
 */
public class FXMLLoaders {

    private static final String FXML_EXTENSION = ".fxml";
    public static final String RESOURCE_PATH_SEPARATOR = "/";



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
        return createFXMLLoader(type, Resources.getResourceBundle(type));
    }

    private static FXMLLoader createFXMLLoader(Class<?> type, ResourceBundle resourceBundle) {
        FXMLLoader loader = new FXMLLoader(){

            @Override
            public BuilderFactory getBuilderFactory() {
                return new FXMLRootBuilderFactory(super.getBuilderFactory());
            }
            
        };
        loader.setClassLoader(type.getClassLoader());
        loader.setResources(resourceBundle);
        return loader;
    }

    private static FXMLLoader createFXMLLoader(Class<?> type, final Object rootController) {
        FXMLLoader loader = createFXMLLoader(type);
        loader.setRoot(rootController);
        loader.setController(rootController);
        return loader;
    }

    private static FXMLLoader createFXMLLoader(final Object rootController, String propertiesFile) {
        return createFXMLLoader(rootController.getClass(), Resources.getResourceBundle(rootController.getClass(),
                propertiesFile));
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

    public static void loadRoot(final Object rootController, FXMLRoot fxmlRoot) throws IOException {
        FXMLLoader loader = createFXMLLoader(rootController, fxmlRoot.properties());
        load(loader, rootController.getClass(), fxmlRoot.fxml());
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
        FXMLLoader loader = createFXMLLoader(type, rootController);
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
        return load(loader, type, "");
    }

    public static Object load(FXMLLoader loader, Class<?> type, String fxmlFile) throws IOException {
        String fxmlFileName = StringUtils.isBlank(fxmlFile) ? getFxmlFileName(type) : fxmlFile;
        try (InputStream is = type.getResourceAsStream(fxmlFileName)) {
            if (is == null) {
                // avoid NullPointerException
                throw new ResourceFileNotFoundException(getAbsoluteFxmlResourcePath(type.getPackage(), fxmlFileName));
            }
            return loader.load(is);
        }
    }

    private static String getAbsoluteFxmlResourcePath(Package aPackage, String fxmlFileName) {
        if (fxmlFileName.startsWith(RESOURCE_PATH_SEPARATOR)) {
            return fxmlFileName;
        } else {
            return getAbsoluteParentDirPath(aPackage) + RESOURCE_PATH_SEPARATOR + fxmlFileName;
        }
    }

    private static String getAbsoluteParentDirPath(Package aPackage) {
        return "/" + aPackage.getName().replace(".", RESOURCE_PATH_SEPARATOR);
    }

    private static String getFxmlFileName(Class<?> type) {
        return type.getSimpleName() + FXML_EXTENSION;
    }
}
