/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.action.impl;

import java.io.InputStream;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.javafxplatform.core.action.AbstractFXAction;

/**
 *
 * @author puce
 */
public class ActionEventHandlerAdapter extends AbstractFXAction {

    private final EventHandler<ActionEvent> actionEventHandler;
    private final ReadOnlyBooleanProperty disabled = new FixedBooleanProperty(this, "disabled", false);

    public ActionEventHandlerAdapter(EventHandler<ActionEvent> actionEventHandler) {
        this.actionEventHandler = actionEventHandler;
    }

    @Override
    public ReadOnlyBooleanProperty disabledProperty() {
        return disabled;
    }

    @Override
    public final boolean isDisabled() {
        return disabled.get();
    }

    @Override
    public void handle(ActionEvent e) {
        actionEventHandler.handle(e);
    }

    @Override
    protected InputStream getImageInputStream(String icon) {
        return actionEventHandler.getClass().getResourceAsStream(icon);
    }
}
