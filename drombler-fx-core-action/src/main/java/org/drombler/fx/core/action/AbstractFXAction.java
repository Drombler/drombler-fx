/*
 *         COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Notice
 *
 * The contents of this file are subject to the COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL)
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.opensource.org/licenses/cddl1.txt
 *
 * The Original Code is Drombler.org. The Initial Developer of the
 * Original Code is Florian Brunner (Sourceforge.net user: puce).
 * Copyright 2012 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.fx.core.action;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanPropertyBase;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.input.KeyCombination;
import org.drombler.commons.fx.scene.GraphicFactory;

/**
 *
 * @author puce
 */
public abstract class AbstractFXAction implements FXAction {

   
    private final StringProperty displayName = new SimpleStringProperty(this, "displayName", null);
    private final ObjectProperty<KeyCombination> accelerator = new SimpleObjectProperty<>(this, "accelerator", null);
    private final DisabledProperty disabled = new DisabledProperty();
    private final ObjectProperty<GraphicFactory> graphicFactory = new SimpleObjectProperty<>(this, "graphicFactory", null);


    @Override
    public final String getDisplayName() {
        return displayNameProperty().get();
    }

    @Override
    public final void setDisplayName(String displayName) {
        displayNameProperty().set(displayName);
    }

    @Override
    public StringProperty displayNameProperty() {
        return displayName;
    }

    @Override
    public final KeyCombination getAccelerator() {
        return acceleratorProperty().get();
    }

    @Override
    public final void setAccelerator(KeyCombination keyCombination) {
        acceleratorProperty().set(keyCombination);
    }

    @Override
    public ObjectProperty<KeyCombination> acceleratorProperty() {
        return accelerator;
    }

    @Override
    public final boolean isDisabled() {
        return disabledProperty().get();
    }

    protected void setDisabled(boolean disabled) {
        this.disabled.set(disabled);
    }

    @Override
    public ReadOnlyBooleanProperty disabledProperty() {
        return disabled;
    }

    @Override
    public final GraphicFactory getGraphicFactory(){
        return graphicFactoryProperty().get();
    }
    
    @Override
    public final void setGraphicFactory(GraphicFactory graphicFactory){
        graphicFactoryProperty().set(graphicFactory);
    }
    
    @Override
    public ObjectProperty<GraphicFactory> graphicFactoryProperty(){
        return graphicFactory;
    }

    private class DisabledProperty extends ReadOnlyBooleanPropertyBase {

        private boolean disabled = false;

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
            return AbstractFXAction.this;
        }

        @Override
        public String getName() {
            return "disabled";
        }
    }
}
