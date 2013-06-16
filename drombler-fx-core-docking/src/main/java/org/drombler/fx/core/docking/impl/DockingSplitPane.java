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
    private final Map<String, PositionableAdapter<DockingAreaPane>> areaIdsToAreaPaneMap = new HashMap<>();
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

    private void adjust(SplitLevel splitLevel, List<DockingAreaPane> removedDockingAreas) {
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
            List<DockingAreaPane> removedDockingAreas = new ArrayList<>();
            addDockingArea(dockingArea.getShortPath(), dockingArea, removedDockingAreas);

            reAddDockingAreas(removedDockingAreas);
        }
    }

    private void addDockingArea(List<ShortPathPart> path, DockingAreaPane dockingAreaPane,
            List<DockingAreaPane> removedDockingAreas) {
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
        }
    }

    private DockingSplitPane getSplitPane(int position, int childLevel, SplitLevel inActualLevel,
            List<DockingAreaPane> removedDockingAreas) {
        if (!splitPanes.containsKey(position)) {
            if (areaPanes.containsKey(position)) {
                removeDockingArea(position, removedDockingAreas);
            }
            DockingSplitPane splitPane = new DockingSplitPane(position, childLevel, inActualLevel);
            addSplitPane(position, splitPane);
        }
        return splitPanes.get(position);
    }

    private void removeDockingArea(int position, List<DockingAreaPane> removedDockingAreas) {
        PositionableAdapter<DockingAreaPane> areaPane = areaPanes.get(position);
        removeDockingArea(areaPane, removedDockingAreas);
    }

    private void addDockingArea(int position, DockingAreaPane dockingArea) {
        PositionableAdapter<DockingAreaPane> dockingAreaAdapter = new PositionableAdapter<>(dockingArea, position);
        // TODO: handle situatation if another child has the same position
        areaPanes.put(position, dockingAreaAdapter);
        areaIdsToAreaPaneMap.put(dockingArea.getAreaId(), dockingAreaAdapter);
        addChild(position, dockingArea);
        dockingArea.setVisualized(true);
//        System.out.println(DockingSplitPane.class.getName() + ": added docking area: " + dockingArea.getAreaId());
    }

    private void removeDockingAreaOnly(PositionableAdapter<DockingAreaPane> dockingArea) {
        areaPanes.remove(dockingArea.getPosition());
        areaIdsToAreaPaneMap.remove(dockingArea.getAdapted().getAreaId());
        removeChild(dockingArea);
        dockingArea.getAdapted().setVisualized(false);
    }

    private void addSplitPane(int position, DockingSplitPane splitPane) {
        splitPanes.put(position, splitPane);
        addChild(position, splitPane);
    }

    private void removeSplitPane(DockingSplitPane splitPane) {
        splitPanes.remove(splitPane.getPosition());
        removeChild(new PositionableAdapter<>(splitPane, splitPane.getPosition()));
    }

    private void addChild(int position, DockingSplitPaneChildBase child) {
        child.setParentSplitPane(this);
        PositionableAdapter<DockingSplitPaneChildBase> positionableChild =
                new PositionableAdapter<>(child, position);
        int insertionPoint = Positionables.getInsertionPoint(positionableChildren, positionableChild);
        positionableChildren.add(insertionPoint, positionableChild);
        dockingSplitPaneChildren.add(insertionPoint, child);
    }

    private void removeChild(PositionableAdapter<? extends DockingSplitPaneChildBase> dockingSplitPaneChild) {
        dockingSplitPaneChild.getAdapted().setParentSplitPane(null);
        int index = Collections.binarySearch(positionableChildren, dockingSplitPaneChild, new PositionableComparator());
        positionableChildren.remove(index);
        dockingSplitPaneChildren.remove(index);
    }

    public void removeDockingArea(DockingAreaPane dockingArea) {
        if (dockingArea.isVisualized()) {
            List<DockingAreaPane> removedAreaPanes = new ArrayList<>();
            removeDockingArea(dockingArea, removedAreaPanes);

            reAddDockingAreas(removedAreaPanes);
        }
    }

    private void removeDockingArea(DockingAreaPane dockingArea, List<DockingAreaPane> allRemovedAreaPanes) {
        if (areaIdsInSplitPane.containsKey(dockingArea.getAreaId())) {
            DockingSplitPane splitPane = areaIdsInSplitPane.remove(dockingArea.getAreaId());
            List<DockingAreaPane> removedAreaPanes = new ArrayList<>();
            // recursion
            splitPane.removeDockingArea(dockingArea, removedAreaPanes);
            allRemovedAreaPanes.addAll(removedAreaPanes);
            for (DockingAreaPane removedAreaPane : removedAreaPanes) {
                areaIdsInSplitPane.remove(removedAreaPane.getAreaId());
            }
            if (!splitPane.containsAnyDockingAreas()) {
                removeSplitPane(splitPane);
            }
        } else {
            if (!areaIdsToAreaPaneMap.containsKey(dockingArea.getAreaId())) {
                throw new IllegalStateException("No area pane with areaId: " + dockingArea.getAreaId());
            }

            PositionableAdapter<DockingAreaPane> dockingAreaAdapter = areaIdsToAreaPaneMap.get(dockingArea.
                    getAreaId());
            removeDockingAreaOnly(dockingAreaAdapter);

            if (dockingSplitPaneChildren.size() == 1) {
                // short paths might have changed -> re-add
                removeAllDockingAreas(allRemovedAreaPanes);
            }
        }

        if (isRoot() && isEmpty()) {
            adjust(SplitLevel.ROOT);
        }
    }

    private boolean isRoot() {
        return getParentSplitPane() == null;
    }

    private void removeAllDockingAreas(List<DockingAreaPane> removedDockingAreas) {
        for (PositionableAdapter<DockingAreaPane> dockingArea : new ArrayList<>(areaPanes.values())) {
            removeDockingArea(dockingArea, removedDockingAreas);
        }
        for (DockingSplitPane splitPane : new ArrayList<>(splitPanes.values())) {
            // recursion
            splitPane.removeAllDockingAreas(removedDockingAreas);
        }
        areaIdsInSplitPane.clear();
    }

    private void reAddDockingAreas(List<DockingAreaPane> removedDockingAreas) throws IllegalStateException {
        for (DockingAreaPane removedDockingArea : removedDockingAreas) {
            List<DockingAreaPane> areas = new ArrayList<>();
            addDockingArea(removedDockingArea.getShortPath(), removedDockingArea, areas);
            if (!areas.isEmpty()) {
                // TODO: should not happen (?) -> log?
                throw new IllegalStateException();
            }
        }
        removeEmptySplitPanes(); // TODO needed?
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

    private void removeDockingArea(PositionableAdapter<DockingAreaPane> dockingArea,
            List<DockingAreaPane> removedAreaPanes) {
        removeDockingAreaOnly(dockingArea);
        removedAreaPanes.add(dockingArea.getAdapted());
    }

    private boolean isLastPathPart(List<ShortPathPart> path) {
        return level == path.size() - 1;
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
}
