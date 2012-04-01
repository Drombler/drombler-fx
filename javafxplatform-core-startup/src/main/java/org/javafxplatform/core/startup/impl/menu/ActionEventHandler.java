/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.startup.impl.menu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.richclientplatform.core.action.ActionListener;

/**
 *
 * @author puce
 */
public class ActionEventHandler implements EventHandler<ActionEvent> {

    private final ActionListener<? super ActionEvent> listener;

    public ActionEventHandler(ActionListener<? super ActionEvent> listener) {
        this.listener = listener;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        listener.actionPerformed(actionEvent);
    }
}
