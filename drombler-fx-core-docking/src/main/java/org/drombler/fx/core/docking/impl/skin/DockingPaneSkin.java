/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drombler.fx.core.docking.impl.skin;

import java.util.Objects;
import javafx.collections.ListChangeListener;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Skin;
import javafx.scene.layout.BorderPane;
import org.drombler.acp.core.docking.spi.DockingAreaContainerDockingAreaEvent;
import org.drombler.acp.core.docking.spi.DockingAreaContainerListener;
import org.drombler.fx.core.docking.DockablePane;
import org.drombler.fx.core.docking.impl.DockingAreaPane;
import org.drombler.fx.core.docking.impl.DockingPane;
import org.drombler.fx.core.docking.impl.DockingSplitPane;
import org.softsmithy.lib.util.PositionableAdapter;

/**
 *
 * @author puce
 */
public class DockingPaneSkin implements Skin<DockingPane> {

    private DockingPane control;
    private BorderPane pane = new BorderPane();
    private DockingSplitPane rootSplitPane = new DockingSplitPane(0, 0, Orientation.VERTICAL);

    public DockingPaneSkin(DockingPane control) {
        this.control = control;
        pane.setCenter(rootSplitPane);
        this.control.addDockingAreaContainerListener(new DockingAreaContainerListener<DockingAreaPane, DockablePane>() {

            @Override
            public void dockingAreaAdded(DockingAreaContainerDockingAreaEvent<DockingAreaPane, DockablePane> event) {
                handleDockingArea(event.getDockingArea());
            }
        });
        for (DockingAreaPane dockingArea : this.control.getAllDockingAreas()) {
            handleDockingArea(dockingArea);
        }
//        this.control.getDockingAreaPanes().addListener(new MapChangeListener<String, DockingPane.DockingAreaEntry>() {
//
//            @Override
//            public void onChanged(Change<? extends String, ? extends DockingAreaEntry> change) {
//                if (change.wasAdded()) {
//                    DockingAreaEntry dockingAreaEntry = change.getValueAdded();
//                    DockingAreaPane dockingAreaPane = dockingAreaEntry.getDockingArea();
//                    if (dockingAreaPane.isPermanent()) {
//                        addDockingArea(dockingAreaEntry.getPreferredPath(), dockingAreaPane);
//                    } else {
//                        dockingAreaPane.getDockables().addListener(new DockingAreaChangeListener(
//                                dockingAreaPane.getAreaId()));
//                        if (!dockingAreaPane.getDockables().isEmpty()) {
//                            addDockingArea(dockingAreaEntry.getPreferredPath(), dockingAreaPane);
//                        }
//                    }
//                }
//                if (change.wasRemoved()) {
//                    DockingAreaPane dockingAreaPane = change.getValueRemoved().getDockingArea();
//                    if (!dockingAreaPane.isPermanent()) {
//                        dockingAreaPane.getDockables().removeListener(new DockingAreaChangeListener(
//                                dockingAreaPane.getAreaId()));
//                    }
//                    removeDockingArea(dockingAreaPane.getAreaId());
//                }
//            }
//        });
    }

    @Override
    public DockingPane getSkinnable() {
        return control;
    }

    @Override
    public Node getNode() {
        return pane;
    }

    @Override
    public void dispose() {
        control = null;
        pane = null;
        rootSplitPane = null;
    }

    private void handleDockingArea(DockingAreaPane dockingArea) {
//        System.out.println(DockingPaneSkin.class.getName() + ": adding docking area: " + dockingArea.getAreaId());
        if (!dockingArea.isPermanent()) {
            dockingArea.getDockables().addListener(new DockingAreaChangeListener(dockingArea));
        }
        if (dockingArea.isVisualizable()) {
            rootSplitPane.addDockingArea(dockingArea);
        }
    }

    private class DockingAreaChangeListener implements ListChangeListener<PositionableAdapter<DockablePane>> {

        private final DockingAreaPane dockingArea;

        public DockingAreaChangeListener(DockingAreaPane dockingArea) {
            this.dockingArea = dockingArea;
        }

        @Override
        public void onChanged(ListChangeListener.Change<? extends PositionableAdapter<DockablePane>> change) {
            if (dockingArea.isVisualizable() && !dockingArea.isVisualized()) {
                rootSplitPane.addDockingArea(dockingArea);
            } else if (!dockingArea.isVisualizable() && dockingArea.isVisualized()) {
                rootSplitPane.removeDockingArea(dockingArea);
            }
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof DockingAreaChangeListener)) {
                return false;
            }
            DockingAreaChangeListener listener = (DockingAreaChangeListener) obj;
            return Objects.equals(dockingArea, listener.dockingArea);
        }

        @Override
        public int hashCode() {
            return Objects.hash(dockingArea);
        }
    }
}