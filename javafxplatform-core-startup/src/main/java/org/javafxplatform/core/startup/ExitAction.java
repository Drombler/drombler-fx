/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.startup;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 *
 * @author puce
 */
public class ExitAction implements EventHandler<ActionEvent> {


    @Override
    public void handle(ActionEvent t) {
        Platform.exit();
    }
    
}
