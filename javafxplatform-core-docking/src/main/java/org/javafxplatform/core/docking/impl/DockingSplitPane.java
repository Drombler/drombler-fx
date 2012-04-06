/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.docking.impl;

import java.util.Collection;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author puce
 */
class DockingSplitPane extends SplitPane implements DockingSplitPaneChild {

    private final ObjectProperty<DockingSplitPane> parentSplitPane = new SimpleObjectProperty<>(this,
            "parentSplitPane", null);

    public DockingSplitPane() {
        // throws exception
//        getItems().addListener(new ListChangeListener<Node>() {
//
//            @Override
//            public void onChanged(Change<? extends Node> change) {
//                if (change.wasAdded()) {
//                    for (Node areaPane : change.getAddedSubList()) {
//                        ((DockingAreaPane) areaPane).setParentSplitPane(DockingSplitPane.this);
//                    }
//                }
//            }
//        });
    }

    public DockingSplitPane getParentSplitPane() {
        return parentSplitPane.get();
    }

    @Override
    public void setParentSplitPane(DockingSplitPane parentSplitPane) {
        this.parentSplitPane.set(parentSplitPane);
    }

    public ObjectProperty<DockingSplitPane> parentSplitPaneProperty() {
        return parentSplitPane;
    }

    public <T extends Node & DockingSplitPaneChild> void add(T child) {
        child.setParentSplitPane(this);
        getItems().add(child);
    }

    public <T extends Node & DockingSplitPaneChild> void add(int currentIndex, T child) {
        child.setParentSplitPane(this);
        BorderPane pane = new BorderPane();
        pane.setCenter(child);
        getItems().add(pane);
    }

    public <T extends Node & DockingSplitPaneChild> void addAll(Collection<? extends T> children) {
        for (T child : children) {
            child.setParentSplitPane(this);
        }
        getItems().addAll(children);
    }

    public <T extends Node & DockingSplitPaneChild> void set(int currentIndex, T child) {
        child.setParentSplitPane(this);
        getItems().set(currentIndex, child);
    }
}
