/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.action.impl;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import org.javafxplatform.core.action.FXToggleAction;
import org.richclientplatform.core.action.ToggleActionListener;

/**
 *
 * @author puce
 */
public class ToggleActionListenerAdapter extends ActionListenerAdapter implements FXToggleAction {

    private final BooleanProperty selected = new SimpleBooleanProperty(this, "selected");

    public ToggleActionListenerAdapter(final ToggleActionListener<? super ActionEvent> listener) {
        super(listener);

        listener.addPropertyChangeListener("selected", new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                selected.set((Boolean) evt.getNewValue());
            }
        });
        selected.addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean oldValue, Boolean newValue) {
                listener.setSelected(newValue);
            }
        });
        selected.set(listener.isSelected());
    }

    @Override
    public BooleanProperty selectedProperty() {
        return selected;
    }

    @Override
    public final boolean isSelected() {
        return selectedProperty().get();
    }

    @Override
    public final void setSelected(boolean selected) {
        selectedProperty().set(selected);
    }
}
