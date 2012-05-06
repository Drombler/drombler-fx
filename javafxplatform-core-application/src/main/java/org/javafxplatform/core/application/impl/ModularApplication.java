/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.application.impl;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.osgi.framework.BundleContext;
import org.richclientplatform.core.action.spi.ApplicationToolBarContainerProvider;
import org.richclientplatform.core.action.spi.MenuBarMenuContainerProvider;

/**
 *
 * @author puce
 */
public class ModularApplication extends Application {

    private static BundleContext BUNDLE_CONTEXT;

    public static void launch(BundleContext bundleContext) {
        BUNDLE_CONTEXT = bundleContext;
        Application.launch(ModularApplication.class);
    }
    private ApplicationPane root;

    @Override
    public void start(Stage stage) throws Exception {
//        Pane root = FXMLLoader.load(getClass().getResource("ContactCenterPane.fxml"));
        root = new ApplicationPane();
        BUNDLE_CONTEXT.registerService(
                new String[]{MenuBarMenuContainerProvider.class.getName(), ContentPaneProvider.class.getName(),
                    ApplicationToolBarContainerProvider.class.getName()}, root, null);
//        Parent personEditorPane = FXMLLoader.load(getClass().getResource("PersonEditorPane.fxml"));
//        root.getChildren().add(personEditorPane);
        stage.setWidth(1400);
        stage.setHeight(1000);
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Override
    public void stop() throws Exception {
    }
}
