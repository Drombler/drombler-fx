/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.application.impl;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.richclientplatform.core.action.Action;
import org.richclientplatform.core.action.MenuEntry;

/**
 *
 * @author puce
 */
@Action(id = "platform.exit", category = "core", displayName = "#exit.displayName")
@MenuEntry(path = "File", position = 9900)
public class ExitAction implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent t) {
        Platform.exit();
    }
}
