/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.docking.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.geometry.Orientation;
import javafx.scene.control.Control;
import org.javafxplatform.core.docking.DockablePane;
import org.javafxplatform.core.docking.skin.Stylesheets;
import org.richclientplatform.core.docking.spi.DockingAreaContainer;
import org.richclientplatform.core.docking.spi.DockingAreaContainerDockingAreaEvent;
import org.richclientplatform.core.docking.spi.DockingAreaContainerListener;
import org.richclientplatform.core.lib.util.PositionableAdapter;

/**
 *
 * @author puce
 */
public class DockingPane extends Control implements DockingAreaContainer<DockingAreaPane, DockablePane> {//extends BorderPane {// GridPane {

    private static final String DEFAULT_STYLE_CLASS = "docking-pane";
    private final Map<String, DockingAreaPane> dockingAreaPanes = new HashMap<>();
    private final DockingAreaManager dockingAreaManager = new DockingAreaManager(null, null, 0, Orientation.VERTICAL);
    private final List<DockingAreaContainerListener<DockingAreaPane, DockablePane>> listeners = new ArrayList<>();
    private final Map<String, List<PositionableAdapter<DockablePane>>> unresolvedDockables = new HashMap<>();

    public DockingPane() {
        getStyleClass().setAll(DEFAULT_STYLE_CLASS);
    }

    @Override
    protected String getUserAgentStylesheet() {
        return Stylesheets.getDefaultStylesheet();
    }

    @Override
    public void addDockingArea(List<Integer> path, DockingAreaPane dockingAreaPane) {
        System.out.println(DockingPane.class.getName() + ": added docking area: " + dockingAreaPane.getAreaId());
        dockingAreaPanes.put(dockingAreaPane.getAreaId(), dockingAreaPane);
        dockingAreaManager.addDockingArea(path, dockingAreaPane);
        resolveUnresolvedDockables(dockingAreaPane.getAreaId());
        DockingAreaContainerDockingAreaEvent<DockingAreaPane, DockablePane> event =
                new DockingAreaContainerDockingAreaEvent<>(this, dockingAreaPane.getAreaId(), dockingAreaPane);
        fireDockingPaneChangeEvent(event);
    }

//    private DockingAreaPane getCurrentDockingArea() {
//        DockingSplitPane splitPane = rootSplitPane;
//        DockingSplitPane currentSplitPane = splitPane;
//        while (splitPane != null) {
//            currentSplitPane = splitPane;
//            splitPane = splitPane.getParentSplitPane();
//        }
//        return (DockingAreaPane) ((!currentSplitPane.getItems().isEmpty()) ? currentSplitPane.getItems().get(0) : null);
//    }
//    private void splitArea(DockingAreaPane currentDockingAreaPane, DockingAreaPane dockingAreaPane) {
//        DockingSplitPane currentParentSplitPane = currentDockingAreaPane.getParentSplitPane();
//        Side side = getSide(currentDockingAreaPane, dockingAreaPane);
//        int currentIndex = currentParentSplitPane.getItems().indexOf(currentDockingAreaPane);
//        if ((side.isHorizontal() && currentParentSplitPane.getOrientation().equals(Orientation.HORIZONTAL))
//                || side.isVertical() && currentParentSplitPane.getOrientation().equals(Orientation.VERTICAL)) {
//            if (BEFORE_SIDES.contains(side)) {
//                currentParentSplitPane.add(currentIndex, dockingAreaPane);
//            } else {
//                currentParentSplitPane.add(currentIndex + 1, dockingAreaPane);
//            }
//        } else {
//            DockingSplitPane splitPane = new DockingSplitPane();
//            splitPane.setOrientation(side.isHorizontal() ? Orientation.HORIZONTAL : Orientation.VERTICAL);
//            if (BEFORE_SIDES.contains(side)) {
//                splitPane.addAll(Arrays.asList(dockingAreaPane, currentDockingAreaPane));
//            } else {
//                splitPane.addAll(Arrays.asList(currentDockingAreaPane, dockingAreaPane));
//            }
//            currentParentSplitPane.set(currentIndex, splitPane);
//        }
//    }
//
//    private Side getSide(DockingAreaPane currentDockingAreaPane, DockingAreaPane dockingAreaPane) {
//        return Side.BOTTOM;
//    }
    @Override
    public void addDockable(String areaId, PositionableAdapter<DockablePane> dockablePane) {
        DockingAreaPane dockingArea = getDockingArea(areaId);
        if (dockingArea != null) { // TODO: needed?
            dockingArea.addDockable(dockablePane);
        } else {
            if (!unresolvedDockables.containsKey(areaId)) {
                unresolvedDockables.put(areaId, new ArrayList<PositionableAdapter<DockablePane>>());
            }
            unresolvedDockables.get(areaId).add(dockablePane);
        }
    }

    /*
     * package-private, for unit testing
     */
    DockingAreaPane getDockingArea(String areaId) {
        return dockingAreaPanes.get(areaId);
    }

    @Override
    public void addDockingAreaContainerListener(DockingAreaContainerListener<DockingAreaPane, DockablePane> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeDockingAreaContainerListener(DockingAreaContainerListener<DockingAreaPane, DockablePane> listener) {
        listeners.remove(listener);
    }

    private void fireDockingPaneChangeEvent(DockingAreaContainerDockingAreaEvent<DockingAreaPane, DockablePane> event) {
        for (DockingAreaContainerListener<DockingAreaPane, DockablePane> listener : listeners) {
            listener.dockingAreaAdded(event);
        }
    }

    private void resolveUnresolvedDockables(String areaId) {
        if (unresolvedDockables.containsKey(areaId)) {
            List<PositionableAdapter<DockablePane>> dockables = unresolvedDockables.remove(areaId);
            for (PositionableAdapter<DockablePane> dockable : dockables) {
                addDockable(areaId, dockable);
            }
        }
    }
//    public static class DockingAreaEntry {
//
//        private final DockingAreaPane dockingArea;
//        private final List<Integer> preferredPath;
//
//        public DockingAreaEntry(DockingAreaPane dockingArea, List<Integer> preferredPath) {
//            this.dockingArea = dockingArea;
//            this.preferredPath = preferredPath;
//        }
//
//        /**
//         * @return the dockingArea
//         */
//        public DockingAreaPane getDockingArea() {
//            return dockingArea;
//        }
//
//        /**
//         * @return the preferredPath
//         */
//        public List<Integer> getPreferredPath() {
//            return Collections.unmodifiableList(preferredPath);
//        }
//    }

    public Collection<DockingAreaPane> getAllDockingAreas() {
        return dockingAreaPanes.values();
    }
}
