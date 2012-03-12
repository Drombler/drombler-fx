/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.startup;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author puce
 */
public class ModularApplication extends Application{
 public static void main(String... args) {
        Application.launch(ModularApplication.class, args);
    }

    @Override
    public void start(Stage stage) throws Exception {
//        Pane root = FXMLLoader.load(getClass().getResource("ContactCenterPane.fxml"));
        ApplicationPane root = new ApplicationPane();
//        Parent personEditorPane = FXMLLoader.load(getClass().getResource("PersonEditorPane.fxml"));
//        root.getChildren().add(personEditorPane);
        stage.setWidth(1400);
        stage.setHeight(300);
        stage.setScene(new Scene(root));
        stage.show();
    }
    
}
