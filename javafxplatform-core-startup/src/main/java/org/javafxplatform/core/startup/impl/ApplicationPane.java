/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.startup.impl;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 *
 * @author puce
 */
public class ApplicationPane extends BorderPane implements MenuBarProvider {

    private final ApplicationPane.Controller controller;
//    private final ActionTracker actionTracker;

    public ApplicationPane() throws IOException {
//        actionTracker = new ActionTracker(bundleContext, new ExtensionTrackerListener<List<ActionDescriptor>>() {
//
//            @Override
//            @SuppressWarnings("unchecked")
//            public void addingExtension(Bundle bundle, BundleEvent be, List<ActionDescriptor> actionDescriptors) {
//                ObservableList<MenuItem> menuItems = controller.menuBar.getMenus().get(0).getItems();
//                for (ActionDescriptor actionDescriptor : actionDescriptors) {
//                    try {
//                        MenuItem menuItem = new MenuItem(actionDescriptor.getDisplayName());
//                        menuItem.setOnAction((EventHandler<ActionEvent>) actionDescriptor.getListenerClass().newInstance());
//                        menuItems.add(menuItems.size() - 1, menuItem);
//                    } catch (InstantiationException | IllegalAccessException | ClassCastException ex) {
//                        Logger.getLogger(ApplicationPane.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//            }
//
//            @Override
//            public void removedExtension(Bundle bundle, BundleEvent be, List<ActionDescriptor> t) {
//
//            }
//        });
        FXMLLoader loader = new FXMLLoader();
        Class<?> type = ApplicationPane.class;
        loader.setClassLoader(type.getClassLoader());
        loader.setResources(ResourceBundle.getBundle(type.getPackage().getName() + ".Bundle"));
        Pane root = (Pane) loader.load(type.getResourceAsStream(type.getSimpleName() + ".fxml"));
        controller = (ApplicationPane.Controller) loader.getController();
        setCenter(root);

//        actionTracker.open();
    }


    @Override
    public MenuBar getMenuBar() {
        return controller.menuBar;
    }

    public static class Controller implements Initializable {

        @FXML
        private MenuBar menuBar;
        @FXML
        private ToolBar toolBar;
        @FXML
        private ContentPane contentPane;

        @Override
        public void initialize(URL url, ResourceBundle rb) {
//            Menu menuFile = new Menu("File");
//
//            MenuItem exitItem = new MenuItem("exit");
//            exitItem.setOnAction(new ExitAction());
//
//            menuFile.getItems().add(exitItem);
//
//            menuBar.getMenus().addAll(menuFile);
        }
    }
}