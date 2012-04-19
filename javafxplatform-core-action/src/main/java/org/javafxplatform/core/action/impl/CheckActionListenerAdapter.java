/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.action.impl;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanPropertyBase;
import javafx.event.ActionEvent;
import org.javafxplatform.core.action.FXCheckAction;
import org.richclientplatform.core.action.CheckActionListener;

/**
 *
 * @author puce
 */
public class CheckActionListenerAdapter extends ActionListenerAdapter implements FXCheckAction {

    private final PrivateWriteableBooleanProperty selected = new PrivateWriteableBooleanProperty("selected");

    public CheckActionListenerAdapter(CheckActionListener<? super ActionEvent> listener) {
        super(listener);

        listener.addPropertyChangeListener("selected", new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                selected.set((Boolean) evt.getNewValue());
            }
        });
        selected.set(listener.isSelected());
    }

    @Override
    public ReadOnlyBooleanProperty selectedProperty() {
        return selected;
    }

    @Override
    public final boolean isSelected() {
        return selectedProperty().get();
    }

    private class PrivateWriteableBooleanProperty extends ReadOnlyBooleanPropertyBase {

        private boolean value;
        private final String name;

        public PrivateWriteableBooleanProperty(String name) {
            this.name = name;
        }

        @Override
        public final boolean get() {
            return value;
        }

        private void set(boolean newValue) {
            value = newValue;
            fireValueChangedEvent();
        }

        @Override
        public Object getBean() {
            return CheckActionListenerAdapter.this;
        }

        @Override
        public String getName() {
            return name;
        }
    }
}
