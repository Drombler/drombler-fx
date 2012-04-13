/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.docking.impl;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import org.javafxplatform.core.docking.DockablePane;

/**
 *
 * @author puce
 */
class DockingAreaPane extends BorderPane implements DockingSplitPaneChild {

    private final TabPane tabPane;

    public DockingAreaPane() {
        tabPane = new TabPane();
        Tab tab = new Tab();
        tab.setText("Test");
        tab.setContent(new Label("Hello world!"));
        tabPane.getTabs().add(tab);
        setCenter(tabPane);
    }
    private final ObjectProperty<DockingSplitPane> parentSplitPane = new SimpleObjectProperty<>(this,
            "parentSplitPane", null);

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

    public void addDockable(DockablePane dockable) {
        Tab tab = new Tab();
        tab.textProperty().bind(dockable.titleProperty());
        tab.contextMenuProperty().bind(dockable.contextMenuProperty());
        tab.setContent(dockable);
        tabPane.getTabs().add(tab);
    }
}
