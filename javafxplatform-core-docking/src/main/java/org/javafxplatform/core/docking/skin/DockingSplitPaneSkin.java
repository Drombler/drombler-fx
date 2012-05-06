/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.docking.skin;

import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.scene.control.Skin;
import javafx.scene.control.SplitPane;
import org.javafxplatform.core.docking.impl.DockingSplitPane;

/**
 *
 * @author puce
 */
public class DockingSplitPaneSkin implements Skin<DockingSplitPane> {

    private DockingSplitPane control;
    private SplitPane splitPane = new SplitPane();

    public DockingSplitPaneSkin(DockingSplitPane control) {
        this.control = control;
        splitPane.orientationProperty().bind(this.control.orientationProperty());
//        splitPane.getItems().addAll(control.getDockingSplitPaneChildren());
        Bindings.bindContent(splitPane.getItems(), control.getDockingSplitPaneChildren());
//        control.getDockingSplitPaneChildren().addListener(new ListChangeListener<DockingSplitPaneChildBase>() {
//
//            @Override
//            public void onChanged(ListChangeListener.Change<? extends DockingSplitPaneChildBase> change) {
//                while (change.next()) {
//                    for (DockingSplitPaneChildBase child : change.getRemoved()) {
//                        splitPane.getItems().remove(child);
//                    }
//
//                    int index = change.getFrom();
//                    for (DockingSplitPaneChildBase child : change.getAddedSubList()) {
//                        splitPane.getItems().add(index++, child);
//                    }
//                }
//                //TODO enough? wasPermutated? etv. see below also see: https://forums.oracle.com/forums/thread.jspa?threadID=2382997&tstart=0
//
////                while (change.next()) {
////                    if (change.wasPermutated()) {
////                        for (int i = change.getFrom(); i < change.getTo(); ++i) {
////                            // TODO: ???
////                        }
////                    } else if (change.wasUpdated()) {
////                        // TODO: ???
////                    } else if (change.wasRemoved()) {
////                        for (DockablePane remitem : change.getRemoved()) {
////                            // TODO: ???
////                        }
////                    } else if (change.wasAdded()) {
////                        for (int index = change.getFrom(); index < change.getTo(); index++) {
////                            addTab(change.getList().get(index));
////                        }
////                    } else if (change.wasReplaced()) {
////                        // TODO: ???
////                    }
////                }
//            }
//        });
    }

    @Override
    public DockingSplitPane getSkinnable() {
        return control;
    }

    @Override
    public Node getNode() {
        return splitPane;
    }

    @Override
    public void dispose() {
        control = null;
        splitPane = null;
    }
}