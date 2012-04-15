/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.docking.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import org.richclientplatform.core.lib.util.PositionableComparator;
import org.richclientplatform.core.lib.util.Positionables;

/**
 *
 * @author puce
 */
class DockingSplitPane extends SplitPane implements DockingSplitPaneChild {

    private final ObjectProperty<DockingSplitPane> parentSplitPane = new SimpleObjectProperty<>(this,
            "parentSplitPane", null);
    private final int position;
    private final Map<Integer, DockingSplitPane> splitPanes = new HashMap<>();
    private final Map<Integer, DockingAreaPane> areaPanes = new HashMap<>();
    private final List<DockingSplitPaneChild> children = new ArrayList<>();
    private List<Integer> skippedPath = new ArrayList<>();

    public DockingSplitPane(int position, Orientation orientation) {
        this.position = position;
        setOrientation(orientation);
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

    private <T extends Node & DockingSplitPaneChild> void add(T child) {
        child.setParentSplitPane(this);
        int insertionPoint = Positionables.getInsertionPoint(children, child);
        getItems().add(insertionPoint, child);
        children.add(insertionPoint, child);

    }

    private void addSplitPane(DockingSplitPane splitPane) {
        Integer pos = splitPane.getPosition();
        splitPanes.put(pos, splitPane);
        if (areaPanes.containsKey(pos)) {
            DockingAreaPane areaPane = areaPanes.remove(pos);
            int index = Collections.binarySearch(children, areaPane, new PositionableComparator());
            children.remove(index);
            getItems().remove(index);
            splitPane.addDockingArea(areaPane);
        }
        add(splitPane);
    }
//    public <T extends Node & DockingSplitPaneChild> void add(int currentIndex, T child) {
//        child.setParentSplitPane(this);
//        BorderPane pane = new BorderPane();
//        pane.setCenter(child);
//        getItems().add(pane);
//    }
//
//    public <T extends Node & DockingSplitPaneChild> void addAll(Collection<? extends T> children) {
//        for (T child : children) {
//            child.setParentSplitPane(this);
//        }
//        getItems().addAll(children);
//    }
//
//    public <T extends Node & DockingSplitPaneChild> void set(int currentIndex, T child) {
//        child.setParentSplitPane(this);
//        getItems().set(currentIndex, child);
//    }

    @Override
    public boolean isSplitPane() {
        return true;
    }

    @Override
    public int getPosition() {
        return position;
    }

    public void addDockingArea(DockingAreaPane dockingAreaPane) {
        // TODO: handle situatation if another child has the same position
        areaPanes.put(dockingAreaPane.getPosition(), dockingAreaPane);
        add(dockingAreaPane);
    }

    public DockingSplitPane getSplitPane(int position) {
        Integer pos = position;
        if (!splitPanes.containsKey(pos)) {
            if (!skippedPath.isEmpty()) {
                addSkippedSplitPane();
            }
            DockingSplitPane splitPane = new DockingSplitPane(position, getChildOrientation());
            addSplitPane(splitPane);
        }
        return splitPanes.get(pos);
    }

    private void addSkippedSplitPane() {
        DockingSplitPane skippedSplitPane = new DockingSplitPane(skippedPath.get(0), getChildOrientation());
        if (skippedPath.size() > 1) {
            skippedSplitPane.setSkippedPath(skippedPath.subList(1, skippedPath.size()));
        }
        adjustOrientation();
        skippedPath.clear();
        for (DockingSplitPane splitPane : splitPanes.values()) {
            skippedSplitPane.addSplitPane(splitPane);
        }
        splitPanes.clear();
        for (DockingAreaPane areaPane : areaPanes.values()) {
            skippedSplitPane.addDockingArea(areaPane);
        }
        areaPanes.clear();
        getItems().clear();
        addSplitPane(skippedSplitPane);
    }

    private void adjustOrientation() {
        if (skippedPath.size() % 2 == 1){
            setOrientation(getChildOrientation());
        }
    }

    private Orientation getChildOrientation() {
        return getOrientation().equals(Orientation.HORIZONTAL) ? Orientation.VERTICAL : Orientation.HORIZONTAL;
    }

    public boolean isEmpty() {
        return getItems().isEmpty();
    }

    public void setSkippedPath(List<Integer> skippedPath) {
        if (!skippedPath.isEmpty()) {
            throw new IllegalArgumentException(
                    "Already skipped path: " + this.skippedPath + "! Cannot set skippedPath to:" + skippedPath);
        }
        this.skippedPath = skippedPath;
        adjustOrientation();
    }
}
