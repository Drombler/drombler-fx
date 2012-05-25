/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.application.impl;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import org.osgi.framework.BundleContext;
import org.richclientplatform.core.action.spi.ApplicationToolBarContainerProvider;
import org.richclientplatform.core.action.spi.MenuBarMenuContainerProvider;

/**
 *
 * @author puce
 */
public class ModularApplication extends Application {

    // TODO: better way than static fields?
    private static BundleContext BUNDLE_CONTEXT;
    private static String APPLICATION_TITLE = "JavaFX Platform based Application";
    private static double APPLICATION_WIDTH = 1024;
    private static double APPLICATION_HEIGHT = 768;

    public static void launch(BundleContext bundleContext, String applicationTitle, double applicationWidth, double applicationHeight) {
        BUNDLE_CONTEXT = bundleContext;
        if (applicationTitle != null) {
            APPLICATION_TITLE = applicationTitle;
        }
        if (applicationWidth > 0) {
            APPLICATION_WIDTH = applicationWidth;
        }
        if (applicationHeight > 0) {
            APPLICATION_HEIGHT = applicationHeight;
        }
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
        stage.setTitle(getTitle());
        stage.setScene(new Scene(root, getWidth(), getHeight()));
        stage.sizeToScene();
        stage.show();
    }

    @Override
    public void stop() throws Exception {
    }

    private double getWidth() {
        return APPLICATION_WIDTH;
    }

    private double getHeight() {
        return APPLICATION_HEIGHT;
    }

    private String getTitle() {
        return APPLICATION_TITLE;
    }
}
