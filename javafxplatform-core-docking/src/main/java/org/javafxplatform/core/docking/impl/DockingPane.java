/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.docking.impl;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.geometry.Orientation;
import javafx.geometry.Side;
import javafx.scene.layout.BorderPane;
import org.javafxplatform.core.docking.DockablePane;
import org.richclientplatform.core.docking.processing.DockingAreaDescriptor;
import org.richclientplatform.core.docking.processing.DockingAreaPathDescriptor;

/**
 *
 * @author puce
 */
class DockingPane extends BorderPane {// GridPane {

    private static final EnumSet<Side> BEFORE_SIDES = EnumSet.of(Side.LEFT, Side.TOP);
    private final Map<String, DockingAreaPane> dockingAreaPanes = new HashMap<>();
    private final DockingSplitPane rootSplitPane = new DockingSplitPane(0, Orientation.VERTICAL);
//    private final DockingAreaPath rootPath = new DockingAreaPath(Orientation.VERTICAL, 0);

    public DockingPane() {
        setCenter(rootSplitPane);
    }

    public void addDockingArea(DockingAreaDescriptor dockingAreaDescriptor) {
        DockingAreaPane dockingAreaPane = new DockingAreaPane(dockingAreaDescriptor.getPathDescriptors().get(
                dockingAreaDescriptor.getPathDescriptors().size() - 1).getPosition());
        dockingAreaPanes.put(dockingAreaDescriptor.getId(), dockingAreaPane);
        DockingSplitPane currentSplitPane = getParentSplitPane(dockingAreaDescriptor.getPathDescriptors().subList(0,
                dockingAreaDescriptor.getPathDescriptors().size() - 1));
        currentSplitPane.addDockingArea(dockingAreaPane);
    }

    private DockingSplitPane getParentSplitPane(List<DockingAreaPathDescriptor> pathDescriptors) {
        DockingSplitPane currentDockingSplitPane = rootSplitPane;
        for (DockingAreaPathDescriptor pathDescriptor : pathDescriptors){
            currentDockingSplitPane = currentDockingSplitPane.getSplitPane(pathDescriptor.getPosition());
        }
        return currentDockingSplitPane;
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

    public void addDockable(String areaId, DockablePane dockablePane) {
        DockingAreaPane dockingArea = dockingAreaPanes.get(areaId);
        dockingArea.addDockable(dockablePane);
    }
}
