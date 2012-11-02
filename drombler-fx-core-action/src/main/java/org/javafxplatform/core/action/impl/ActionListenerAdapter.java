/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.action.impl;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.InputStream;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanPropertyBase;
import javafx.event.ActionEvent;
import org.drombler.acp.core.action.ActionListener;
import org.javafxplatform.core.action.AbstractFXAction;

/**
 *
 * @author puce
 */
public class ActionListenerAdapter extends AbstractFXAction {

    private final ActionListener<? super ActionEvent> listener;
    private final DisabledProperty disabled = new DisabledProperty();

    public ActionListenerAdapter(ActionListener<? super ActionEvent> listener) {
        this.listener = listener;
        listener.addPropertyChangeListener("disabled", new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                disabled.set((Boolean) evt.getNewValue());
            }
        });
        disabled.set(listener.isDisabled());
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        listener.onAction(actionEvent);
    }

    @Override
    public ReadOnlyBooleanProperty disabledProperty() {
        return disabled;
    }

    @Override
    public final boolean isDisabled() {
        return disabledProperty().get();
    }

    @Override
    protected InputStream getImageInputStream(String icon) {
        return listener.getClass().getResourceAsStream(icon);
    }

    private class DisabledProperty extends ReadOnlyBooleanPropertyBase {

        private boolean disabled;

        @Override
        public final boolean get() {
            return disabled;
        }

        private void set(boolean newValue) {
            disabled = newValue;
            fireValueChangedEvent();
        }

        @Override
        public Object getBean() {
            return ActionListenerAdapter.this;
        }

        @Override
        public String getName() {
            return "disabled";
        }
    }
}
