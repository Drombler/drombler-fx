/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.docking.impl.skin;

import java.util.HashMap;
import java.util.Map;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Skin;
import javafx.scene.control.SplitPane;
import org.javafxplatform.core.docking.impl.DockingSplitPane;
import org.javafxplatform.core.docking.impl.DockingSplitPaneChildBase;
import org.richclientplatform.core.docking.spi.LayoutConstraintsDescriptor;

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
        this.control.getDockingSplitPaneChildren().addListener(new ListChangeListener<DockingSplitPaneChildBase>() {

            @Override
            public void onChanged(Change<? extends DockingSplitPaneChildBase> change) {
                recalculateDividerPositions();
            }
        });

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

        control.widthProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
                recalculateDividerPositions();
            }
        });

        control.heightProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
                recalculateDividerPositions();
            }
        });
        recalculateDividerPositions();
    }

    private void recalculateDividerPositions() {
        System.out.println("Split Pane: " + this + ": Orientation: " + control.getOrientation());
        if (control.getOrientation().equals(Orientation.HORIZONTAL)) {
            recalculateDividerPositionsHorizontal();
        } else {
            recalculateDividerPositionsVertical();
        }
    }

    private void recalculateDividerPositionsHorizontal() {
        double[] prefWidths = new double[control.getDockingSplitPaneChildren().size()];
        double[] currentWidths = new double[control.getDockingSplitPaneChildren().size()];


        int i = 0;
        for (DockingSplitPaneChildBase child : control.getDockingSplitPaneChildren()) {
            LayoutConstraintsDescriptor layoutConstraints = child.getLayoutConstraints();
            prefWidths[i] = layoutConstraints.getPrefWidth();
            currentWidths[i] = child.getWidth();
            i++;
        }

        recalculateDividerPositions(prefWidths, currentWidths, control.getWidth());
    }

    private void recalculateDividerPositionsVertical() {
        double[] prefHeights = new double[control.getDockingSplitPaneChildren().size()];
        double[] currentHeights = new double[control.getDockingSplitPaneChildren().size()];

        int i = 0;
        for (DockingSplitPaneChildBase child : control.getDockingSplitPaneChildren()) {
            LayoutConstraintsDescriptor layoutConstraints = child.getLayoutConstraints();
            prefHeights[i] = layoutConstraints.getPrefHeight();
            currentHeights[i] = child.getHeight();
            i++;
        }
        recalculateDividerPositions(prefHeights, currentHeights, control.getHeight());
    }

    private void recalculateDividerPositions(double[] prefs, double[] current, double parentSize) {
        System.out.println("Parent size: " + parentSize);
        double[] relativeSizes = new double[prefs.length];
        double requiredRelativeSize = 0;
        int flexiblePositions = 0;
        if (parentSize > 0) {
            for (int i = 0; i < prefs.length; i++) {
                System.out.println(i + ": " + prefs[i] + " -> " + current[i]);
                if (prefs[i] > 0) {
                    relativeSizes[i] = prefs[i] / parentSize;
                    requiredRelativeSize += relativeSizes[i];
                } else {
                    relativeSizes[i] = prefs[i];
                    flexiblePositions++;
                }
            }

            if (requiredRelativeSize > 1) {
                // TODO: shrink sizes
            }

            if (flexiblePositions > 0) {
                double flexibleRelativeSize = (1 - requiredRelativeSize) / flexiblePositions;
                for (int i = 0; i < prefs.length; i++) {
                    if (prefs[i] < 0) {
                        relativeSizes[i] = flexibleRelativeSize;
                    }
                }
            }

            double currentPosition = 0;
            for (int i = 0; i < relativeSizes.length - 1; i++) {
                if (relativeSizes[i] > 0) {
                    currentPosition += relativeSizes[i];
                    splitPane.setDividerPosition(i, currentPosition);
                    System.out.println("Set divider position: " + i + " to " + currentPosition);
                }
            }
        }

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