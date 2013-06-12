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
package org.drombler.fx.core.docking.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import org.drombler.acp.core.docking.spi.LayoutConstraintsDescriptor;
import org.drombler.fx.core.docking.impl.skin.Stylesheets;
import org.softsmithy.lib.util.Positionable;
import org.softsmithy.lib.util.PositionableAdapter;
import org.softsmithy.lib.util.PositionableComparator;
import org.softsmithy.lib.util.Positionables;

/**
 *
 * @author puce
 */
public class DockingSplitPane extends DockingSplitPaneChildBase {

    private static final String DEFAULT_STYLE_CLASS = "docking-split-pane";
    private final int position;
    private final int level;
    private int actualLevel;
    private final ObjectProperty<Orientation> orientation = new SimpleObjectProperty<>(this,
            "orientation", null);
    private final Map<String, DockingSplitPane> areaIdsInSplitPane = new HashMap<>();
    private final Map<Integer, DockingSplitPane> splitPanes = new HashMap<>();
    private final Map<String, DockingAreaPane> areaIdsInAreaPane = new HashMap<>();
    private final Map<Integer, PositionableAdapter<DockingAreaPane>> areaPanes = new HashMap<>(); // TODO: PositionableAdapter needed?
    private final ObservableList<DockingSplitPaneChildBase> dockingSplitPaneChildren = FXCollections.
            observableArrayList();
    private final List<Positionable> positionableChildren = new ArrayList<>();

    public DockingSplitPane(int position, int level, SplitLevel actualLevel) {
        super(true);
        this.position = position;
        this.level = level;
        adjust(actualLevel);
        getStyleClass().setAll(DEFAULT_STYLE_CLASS);

    }

    @Override
    protected String getUserAgentStylesheet() {
        return Stylesheets.getDefaultStylesheet();
    }

    public final Orientation getOrientation() {
        return orientationProperty().get();
    }

    public final void setOrientation(Orientation parentSplitPane) {
        orientationProperty().set(parentSplitPane);
    }

    public ObjectProperty<Orientation> orientationProperty() {
        return orientation;
    }

    /**
     * @return the position
     */
    public int getPosition() {
        return position;
    }

    public int getLevel() {
        return level;
    }

    /**
     * @return the actualLevel
     */
    public int getActualLevel() {
        return actualLevel;
    }

    /**
     * @return the children
     */
    public ObservableList<DockingSplitPaneChildBase> getDockingSplitPaneChildren() {
        return FXCollections.unmodifiableObservableList(dockingSplitPaneChildren);
    }

    private void adjust(SplitLevel splitLevel, List<PositionableAdapter<DockingAreaPane>> removedDockingAreas) {
        if (getActualLevel() != splitLevel.getLevel()) {
            if (isEmpty()) {
                adjust(splitLevel);
            } else {
                adjust(splitLevel);
                removeAllDockingAreas(removedDockingAreas);
            }
        }
    }

    private void adjust(SplitLevel splitLevel) {
        actualLevel = splitLevel.getLevel();
        setOrientation(splitLevel.getOrientation());
    }

    /**
     * Checks if this DockingSplitPane contains any children.
     *
     * TODO: needed as public?
     *
     * @return true, if it contains no children, else false.
     */
    public boolean isEmpty() {
        return dockingSplitPaneChildren.isEmpty();
    }

    /**
     * Checks if this DockingSplitPane contains a DockingAreaPane or a child DockingSplitPane, which contains any
     * docking area.
     *
     * TODO: needed as public?
     *
     * @return if this DockingSplitPane directly or indirectly contains any DockingAreaPane, else false.
     */
    public boolean containsAnyDockingAreas() {
        boolean contains = !areaPanes.isEmpty();
        if (!contains) {
            for (DockingSplitPane splitPane : splitPanes.values()) {
                // recursion
                contains = splitPane.containsAnyDockingAreas();
                if (contains) {
                    break;
                }
            }
        }
        return contains;
    }

    public void addDockingArea(DockingAreaPane dockingArea) {
        if (dockingArea.isVisualizable()) {
//            System.out.println(DockingSplitPane.class.getName() + ": adding docking area: " + dockingArea.getAreaId());
            List<PositionableAdapter<DockingAreaPane>> removedDockingAreas = new ArrayList<>();
            addDockingArea(dockingArea.getShortPath(), dockingArea, removedDockingAreas);

            for (PositionableAdapter<DockingAreaPane> removedDockingAreaAdapter : removedDockingAreas) {
                DockingAreaPane removedDockingArea = removedDockingAreaAdapter.getAdapted();
                List<PositionableAdapter<DockingAreaPane>> areas = new ArrayList<>();
                addDockingArea(removedDockingArea.getShortPath(), removedDockingArea, areas);
                if (!areas.isEmpty()) {
                    // TODO: should not happen (?) -> log?
                }
            }
            removeEmptySplitPanes();
        }
    }

    private void addDockingArea(List<ShortPathPart> path, DockingAreaPane dockingAreaPane,
            List<PositionableAdapter<DockingAreaPane>> removedDockingAreas) {
        if (level >= path.size()) {
            throw new IllegalStateException("Level is too high! Level=" + level + ", areaId=" + dockingAreaPane.
                    getAreaId() + ", path=" + path);
        }
        ShortPathPart pathPart = path.get(level);
        adjust(pathPart.getInActualLevel(), removedDockingAreas);
        if (!isLastPathPart(path)) {
            int childLevel = level + 1;
            DockingSplitPane splitPane = getSplitPane(pathPart.getPosition(), childLevel, path.get(childLevel).
                    getInActualLevel(),
                    removedDockingAreas);
            // recursion
            splitPane.addDockingArea(path, dockingAreaPane, removedDockingAreas);
            areaIdsInSplitPane.put(dockingAreaPane.getAreaId(), splitPane);
        } else {
            addDockingArea(pathPart.getPosition(), dockingAreaPane);
            areaIdsInAreaPane.put(dockingAreaPane.getAreaId(), dockingAreaPane);
        }
    }

    private void addDockingArea(int position, DockingAreaPane dockingArea) {
        // TODO: handle situatation if another child has the same position
        areaPanes.put(position, new PositionableAdapter<>(dockingArea, position));
        addChild(position, dockingArea);
        dockingArea.setVisualized(true);
//        System.out.println(DockingSplitPane.class.getName() + ": added docking area: " + dockingArea.getAreaId());
    }

    private DockingSplitPane getSplitPane(int position, int childLevel, SplitLevel inActualLevel,
            List<PositionableAdapter<DockingAreaPane>> removedDockingAreas) {
        if (!splitPanes.containsKey(position)) {
            if (areaPanes.containsKey(position)) {
                removeDockingArea(position, removedDockingAreas);
            }
            DockingSplitPane splitPane = new DockingSplitPane(position, childLevel, inActualLevel);
            addSplitPane(position, splitPane);
        }
        return splitPanes.get(position);
    }

    private void removeDockingArea(int position,
            List<PositionableAdapter<DockingAreaPane>> removedDockingAreas) {
        PositionableAdapter<DockingAreaPane> areaPane = areaPanes.remove(position);
        removeDockingAreaOnly(areaPane);
        removedDockingAreas.add(areaPane);
    }

    private void addSplitPane(int position, DockingSplitPane splitPane) {
        splitPanes.put(position, splitPane);
        addChild(position, splitPane);
    }

    private void addChild(int position, DockingSplitPaneChildBase child) {
        child.setParentSplitPane(this);
        PositionableAdapter<DockingSplitPaneChildBase> positionableChild =
                new PositionableAdapter<>(child, position);
        int insertionPoint = Positionables.getInsertionPoint(positionableChildren, positionableChild);
        positionableChildren.add(insertionPoint, positionableChild);
        dockingSplitPaneChildren.add(insertionPoint, child);
    }

    public void removeDockingArea(DockingAreaPane dockingArea) {
        if (dockingArea.isVisualized()) {
            Map<String, OutdatedDockingAreaShortPath> outdatedDockingAreaShortPaths = new HashMap<>();
            removeDockingArea(dockingArea.getShortPath(), outdatedDockingAreaShortPaths);

            reAddOutdatedDockingAreas(outdatedDockingAreaShortPaths);
        }
    }

    private void reAddOutdatedDockingAreas(Map<String, OutdatedDockingAreaShortPath> outdatedDockingAreaShortPaths) {
        List<DockingAreaPane> dockingAreas = new ArrayList<>(outdatedDockingAreaShortPaths.size());
        for (Map.Entry<String, OutdatedDockingAreaShortPath> nextEntry : outdatedDockingAreaShortPaths.entrySet()) {
            removeDockingArea(nextEntry.getValue().getOutdatedShortPath(), null);
            dockingAreas.add(nextEntry.getValue().getDockingArea());
        }
        addDockingAreas(dockingAreas);
    }

    private void addDockingAreas(List<DockingAreaPane> dockingAreas) {
        for (DockingAreaPane dockingAreaPane : dockingAreas) {
            addDockingArea(dockingAreaPane);
        }
    }

    private OutdatedDockingAreaShortPath removeDockingArea(List<ShortPathPart> path,
            Map<String, OutdatedDockingAreaShortPath> outdatedDockingAreaShortPaths) {
        OutdatedDockingAreaShortPath outdatedDockingAreaShortPath = null;
        ShortPathPart pathPart = path.get(level);
        if (!isLastPathPart(path)) {
            if (!splitPanes.containsKey(pathPart.getPosition())) {
                throw new IllegalStateException("No split pane at position: " + pathPart.getPosition());
            }
            DockingSplitPane splitPane = splitPanes.get(pathPart.getPosition());
            // recursion
            splitPane.removeDockingArea(path, outdatedDockingAreaShortPaths);
            if (!splitPane.containsAnyDockingAreas()) {
                removeSplitPane(splitPane);
            }
        } else {
            if (!areaPanes.containsKey(pathPart.getPosition())) {
                throw new IllegalStateException("No area pane at position: " + pathPart.getPosition());
            }

            if (outdatedDockingAreaShortPaths != null && getContentSize() == 2) {
                putAllOutdatedDockingAreaShortPaths(outdatedDockingAreaShortPaths, pathPart.getPosition());
            }
            
            PositionableAdapter<DockingAreaPane> dockingArea = areaPanes.remove(pathPart.getPosition());
            removeDockingAreaOnly(dockingArea);
        }

        return outdatedDockingAreaShortPath;
    }

    private int getContentSize() {
        return areaPanes.size() + splitPanes.size();
    }

    private void removeAllDockingAreas(List<PositionableAdapter<DockingAreaPane>> removedDockingAreas) {
        for (PositionableAdapter<DockingAreaPane> dockingArea : areaPanes.values()) {
            removeDockingAreaOnly(dockingArea);
            removedDockingAreas.add(dockingArea);
        }
        areaPanes.clear();
        for (DockingSplitPane splitPane : splitPanes.values()) {
            // recursion
            splitPane.removeAllDockingAreas(removedDockingAreas);
        }
    }

    private void removeDockingAreaOnly(PositionableAdapter<DockingAreaPane> dockingArea) {
        int index = Collections.binarySearch(positionableChildren, dockingArea, new PositionableComparator());
        positionableChildren.remove(index);
        dockingSplitPaneChildren.remove(index);
        dockingArea.getAdapted().setVisualized(false);
        removeChild(dockingArea.getAdapted());
    }

    private void removeEmptySplitPanes() {
        List<DockingSplitPane> dockingSplitPanes = new ArrayList<>(splitPanes.values()); // avoid ConcurrentModificationException
        for (DockingSplitPane splitPane : dockingSplitPanes) {
            // recursion
            splitPane.removeEmptySplitPanes();
            if (!splitPane.containsAnyDockingAreas()) {
                removeSplitPane(splitPane);
            }
        }
    }

    private void removeSplitPane(DockingSplitPane splitPane) {
        splitPanes.remove(splitPane.getPosition());
        int index = Collections.binarySearch(positionableChildren,
                new PositionableAdapter<>(splitPane, splitPane.getPosition()), new PositionableComparator());
        positionableChildren.remove(index);
        dockingSplitPaneChildren.remove(index);
        removeChild(splitPane);
    }

    private void removeChild(DockingSplitPaneChildBase dockingSplitPaneChild) {
        dockingSplitPaneChild.setParentSplitPane(null);
    }

    @Override
    public LayoutConstraintsDescriptor getLayoutConstraints() {
        double prefWidth = 0;
        double prefHeight = 0;
        for (DockingSplitPaneChildBase child : dockingSplitPaneChildren) {
            LayoutConstraintsDescriptor childLayoutConstraints = child.getLayoutConstraints();
            if (prefWidth >= 0) {
                if (childLayoutConstraints.getPrefWidth() < 0 || childLayoutConstraints.getPrefWidth() > prefWidth) {
                    prefWidth = childLayoutConstraints.getPrefWidth();
                }
            }
            if (prefHeight >= 0) {
                if (childLayoutConstraints.getPrefHeight() < 0 || childLayoutConstraints.getPrefHeight() > prefHeight) {
                    prefHeight = childLayoutConstraints.getPrefHeight();
                }
            }
            if (prefWidth < 0 && prefHeight < 0) {
                break;
            }
        }
        LayoutConstraintsDescriptor layoutConstraints = new LayoutConstraintsDescriptor();
        layoutConstraints.setPrefWidth(prefWidth);
        layoutConstraints.setPrefHeight(prefHeight);
        return layoutConstraints;
    }

    private DockingAreaPane getOtherDockingArea(int position) {
        if (areaPanes.size() != 2) {
            throw new IllegalStateException("There should be exactly 2 docking "
                    + "areas, but there are: " + areaPanes.size());
        }
        Set<Integer> positions = new HashSet<>(areaPanes.keySet());
        positions.remove(position);
        Integer otherPostion = positions.iterator().next();
        return areaPanes.get(otherPostion).getAdapted();
    }

    private void putAllOutdatedDockingAreaShortPaths(
            Map<String, OutdatedDockingAreaShortPath> outdatedDockingAreaShortPaths, int position) {
        if (areaPanes.size() == 2) {
            putOutdatedDockingAreaShortPath(outdatedDockingAreaShortPaths, position);
        } else { // -> areaPanes.size() == 1 && splitPanes.size() == 1
            putAllOutdatedDockingAreaShortPathsRecursivly(outdatedDockingAreaShortPaths);
        }
    }

    private void putOutdatedDockingAreaShortPath(Map<String, OutdatedDockingAreaShortPath> outdatedDockingAreaShortPaths,
            int position) {
        DockingAreaPane otherDockingArea = getOtherDockingArea(position);
        putOutdatedDockingAreaShortPath(outdatedDockingAreaShortPaths, otherDockingArea);
    }

    private void putAllOutdatedDockingAreaShortPathsRecursivly(
            Map<String, OutdatedDockingAreaShortPath> outdatedDockingAreaShortPaths) {
        for (DockingSplitPane splitPane : splitPanes.values()) {
            splitPane.putAllOutdatedDockingAreaShortPaths(outdatedDockingAreaShortPaths);
            splitPane.putAllOutdatedDockingAreaShortPathsRecursivly(outdatedDockingAreaShortPaths);
        }
    }

    private void putAllOutdatedDockingAreaShortPaths(
            Map<String, OutdatedDockingAreaShortPath> outdatedDockingAreaShortPaths) {
        for (PositionableAdapter<DockingAreaPane> dockingAreaAdapter : areaPanes.values()) {
            putOutdatedDockingAreaShortPath(outdatedDockingAreaShortPaths, dockingAreaAdapter.getAdapted());
        }
    }

    private void putOutdatedDockingAreaShortPath(Map<String, OutdatedDockingAreaShortPath> outdatedDockingAreaShortPaths,
            DockingAreaPane dockingArea) {
        if (!outdatedDockingAreaShortPaths.containsKey(dockingArea.getAreaId())) {
            // calculate the shortPath before the second last docking area 
            // gets removed, as this might change the short path of the last
            // docking area
            List<ShortPathPart> oldOtherShortPath = dockingArea.getShortPath();
            outdatedDockingAreaShortPaths.put(dockingArea.getAreaId(),
                    new OutdatedDockingAreaShortPath(dockingArea, oldOtherShortPath));
        }
    }

    private boolean isLastPathPart(List<ShortPathPart> path) {
        return level == path.size() - 1;
    }

    private static class OutdatedDockingAreaShortPath {

        private final DockingAreaPane dockingArea;
        private final List<ShortPathPart> outdatedShortPath;

        public OutdatedDockingAreaShortPath(DockingAreaPane dockingArea, List<ShortPathPart> outdatedShortPath) {
            this.dockingArea = dockingArea;
            this.outdatedShortPath = outdatedShortPath;
        }

        /**
         * @return the dockingArea
         */
        public DockingAreaPane getDockingArea() {
            return dockingArea;
        }

        /**
         * @return the outdatedShortPath
         */
        public List<ShortPathPart> getOutdatedShortPath() {
            return outdatedShortPath;
        }
    }
}
