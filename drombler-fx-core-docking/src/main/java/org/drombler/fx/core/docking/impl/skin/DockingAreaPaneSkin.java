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
package org.drombler.fx.core.docking.impl.skin;

import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.control.Skin;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.drombler.fx.core.docking.DockablePane;
import org.drombler.fx.core.docking.impl.DockingAreaPane;
import org.softsmithy.lib.util.PositionableAdapter;

/**
 *
 * @author puce
 */
public class DockingAreaPaneSkin implements Skin<DockingAreaPane> {

    private DockingAreaPane control;
    private TabPane tabPane = new TabPane();

    private final ListChangeListener<PositionableAdapter<DockablePane>> dockablesChangeListener = change -> {
        while (change.next()) {
            if (change.wasPermutated()) {
                for (int i = change.getFrom(); i < change.getTo(); ++i) {
                    // TODO: ???
                }
            } else if (change.wasUpdated()) {
                // TODO: ???
            } else if (change.wasRemoved()) {
                for (PositionableAdapter<DockablePane> remitem : change.getRemoved()) {
                    // TODO: ???
                }
            } else if (change.wasAdded()) {
                for (int index = change.getFrom(); index < change.getTo(); index++) {
                    addTab(change.getList().get(index));
                }
            } else if (change.wasReplaced()) {
                // TODO: ???
            }
        }
    };

    private final ListChangeListener<Tab> tabsChangeListener = change -> {
        while (change.next()) {
            if (change.wasPermutated()) {
                for (int i = change.getFrom(); i < change.getTo(); ++i) {
                    // TODO: ???
                }
            } else if (change.wasUpdated()) {
                // TODO: ???
            } else if (change.wasRemoved()) {
                DockingAreaPaneSkin.this.control.removeDockable(change.getFrom());
            } else if (change.wasAdded()) {
                // TODO: ???
            } else if (change.wasReplaced()) {
                // TODO: ???
            }
        }
    };
    private final ChangeListener<Number> dockableSelectedIndexChangeListener = (ov, oldValue, newValue)
            -> tabPane.getSelectionModel().select(newValue.intValue());

    private final ChangeListener<Number> tabSelectedIndexChangeListener = (ov, oldValue, newValue)
            -> DockingAreaPaneSkin.this.control.getSelectionModel().select(newValue.intValue());

    public DockingAreaPaneSkin(DockingAreaPane control) {
        this.control = control;
//        Tab tab = new Tab();
//        tab.setText("Test");
//        tab.setContent(new Label("Hello world!"));
//        tabPane.getTabs().add(tab);

        control.getDockables().addListener(dockablesChangeListener);

        tabPane.getTabs().addListener(tabsChangeListener);

        for (PositionableAdapter<DockablePane> dockable : control.getDockables()) {
            addTab(dockable);
        }

        control.getSelectionModel().selectedIndexProperty().addListener(dockableSelectedIndexChangeListener);

        tabPane.getSelectionModel().selectedIndexProperty().addListener(tabSelectedIndexChangeListener);

        tabPane.getSelectionModel().select(control.getSelectionModel().getSelectedIndex());

//        tabPane.focusedProperty().addListener(new ChangeListener<Boolean>() {
//
//            @Override
//            public void changed(ObservableValue<? extends Boolean> ov, Boolean oldValue, Boolean newVlaue) {
//                if (newVlaue) {
//                    Tab tab = tabPane.getSelectionModel().getSelectedItem();
//                    if (tab != null) {
//                        System.out.println("Selected Tab Changed: " + tab.getText());
//                    } else {
//                        System.out.println("Selected Tab Changed: empty");
//                    }
//                }
//            }
//        });
    }

    @Override
    public DockingAreaPane getSkinnable() {
        return control;
    }

    @Override
    public Node getNode() {
        return tabPane;
    }

    @Override
    public void dispose() {
        control.getDockables().removeListener(dockablesChangeListener);
        tabPane.getTabs().removeListener(tabsChangeListener);
        control.getSelectionModel().selectedIndexProperty().removeListener(dockableSelectedIndexChangeListener);
        tabPane.getSelectionModel().selectedIndexProperty().removeListener(tabSelectedIndexChangeListener);

        control = null;
        tabPane = null;
    }

    private void addTab(PositionableAdapter<DockablePane> dockable) {
        Tab tab = new Tab();
        tab.textProperty().bind(dockable.getAdapted().titleProperty());
        tab.graphicProperty().bind(dockable.getAdapted().graphicProperty());
        tab.contextMenuProperty().bind(dockable.getAdapted().contextMenuProperty());
        tab.setContent(dockable.getAdapted());
        tabPane.getTabs().add(control.getDockables().indexOf(dockable), tab);
    }
}
