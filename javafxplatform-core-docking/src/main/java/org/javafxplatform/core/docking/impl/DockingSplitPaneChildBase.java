/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.docking.impl;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Control;

/**
 *
 * @author puce
 */
public abstract class DockingSplitPaneChildBase extends Control{

    private final ObjectProperty<DockingSplitPane> parentSplitPane = new SimpleObjectProperty<>(this,
            "parentSplitPane", null);
    private final boolean splitPane;

    public DockingSplitPaneChildBase(boolean splitPane) {
        this.splitPane = splitPane;
    }


    public DockingSplitPane getParentSplitPane() {
        return parentSplitPane.get();
    }

    public ObjectProperty<DockingSplitPane> parentSplitPaneProperty() {
        return parentSplitPane;
    }

    public void setParentSplitPane(DockingSplitPane parentSplitPane) {
        this.parentSplitPane.set(parentSplitPane);
    }

    public boolean isSplitPane() {
        return splitPane;
    }
}
