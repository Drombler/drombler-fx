/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.docking.impl;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Control;
import org.richclientplatform.core.lib.util.Positionable;

/**
 *
 * @author puce
 */
public abstract class DockingSplitPaneChildBase extends Control implements Positionable {

    private final ObjectProperty<DockingSplitPane> parentSplitPane = new SimpleObjectProperty<>(this,
            "parentSplitPane", null);
    private final int position;
    private final boolean splitPane;

    public DockingSplitPaneChildBase(int position, boolean splitPane) {
        this.position = position;
        this.splitPane = splitPane;
    }

    @Override
    public int getPosition() {
        return position;
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
