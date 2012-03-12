/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.startup;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 *
 * @author puce
 */
public class ApplicationPane extends BorderPane {

    private final ApplicationPane.Controller controller;

    public ApplicationPane() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setClassLoader(ApplicationPane.class.getClassLoader());
        loader.setResources(ResourceBundle.getBundle("org.javafxplatform.core.startup.Bundle"));
        Pane root = (Pane) loader.load(ApplicationPane.class.getResourceAsStream("ApplicationPane.fxml"));
        controller = (ApplicationPane.Controller) loader.getController();
        setCenter(root);
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
            Menu menuFile = new Menu("File");

            MenuItem exitItem = new MenuItem("exit");
            exitItem.setOnAction(new ExitAction());

            menuFile.getItems().add(exitItem);

            menuBar.getMenus().addAll(menuFile);
        }
    }
}