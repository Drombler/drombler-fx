/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.docking.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import org.javafxplatform.core.docking.impl.skin.Stylesheets;
import org.richclientplatform.core.lib.util.Positionable;
import org.richclientplatform.core.lib.util.PositionableAdapter;
import org.richclientplatform.core.lib.util.PositionableComparator;
import org.richclientplatform.core.lib.util.Positionables;

/**
 *
 * @author puce
 */
public class DockingSplitPane extends DockingSplitPaneChildBase {

    private static final String DEFAULT_STYLE_CLASS = "docking-split-pane";
    private final int position;
    private int actualLevel;
    private final ObjectProperty<Orientation> orientation = new SimpleObjectProperty<>(this,
            "orientation", null);
//    private final Map<Integer, ShortPathPart> shortPathParts = new HashMap<>();
    private final Map<Integer, DockingSplitPane> splitPanes = new HashMap<>();
    private final Map<Integer, PositionableAdapter<DockingAreaPane>> areaPanes = new HashMap<>();
    private final ObservableList<DockingSplitPaneChildBase> dockingSplitPaneChildren = FXCollections.observableArrayList();
    private final List<Positionable> positionableChildren = new ArrayList<>();

    public DockingSplitPane(int position, int actualLevel, Orientation orientation) {
        super(true);
        setOrientation(orientation);
        this.position = position;
        this.actualLevel = actualLevel;
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
     * @return the actualLevel
     */
    public int getActualLevel() {
        return actualLevel;
    }

//    public <T extends Node & DockingSplitPaneChildBase> void add(int currentIndex, T child) {
//        child.setParentSplitPane(this);
//        BorderPane pane = new BorderPane();
//        pane.setCenter(child);
//        getItems().add(pane);
//    }
//
//    public <T extends Node & DockingSplitPaneChildBase> void addAll(Collection<? extends T> children) {
//        for (T child : children) {
//            child.setParentSplitPane(this);
//        }
//        getItems().addAll(children);
//    }
//
//    public <T extends Node & DockingSplitPaneChildBase> void set(int currentIndex, T child) {
//        child.setParentSplitPane(this);
//        getItems().set(currentIndex, child);
//    }
    public void addDockingArea(DockingAreaPane dockingArea) {
        if (dockingArea.isVisualizable()) {
            System.out.println(DockingSplitPane.class.getName() + ": adding docking area: " + dockingArea.getAreaId());
            List<PositionableAdapter<DockingAreaPane>> removedDockingAreas = new ArrayList<>();
            addDockingArea(dockingArea.getShortPath().iterator(), dockingArea, removedDockingAreas);
            for (PositionableAdapter<DockingAreaPane> removedDockingAreaAdapter : removedDockingAreas) {
                DockingAreaPane removedDockingArea = removedDockingAreaAdapter.getAdapted();
                List<PositionableAdapter<DockingAreaPane>> areas = new ArrayList<>();
                addDockingArea(removedDockingArea.getShortPath().iterator(), removedDockingArea, areas);
                if (!areas.isEmpty()) {
                    // TODO: should not happen (?) -> log?
                }
            }
            removeEmptySplitPanes();
        }
    }

    private void addDockingArea(Iterator<ShortPathPart> path, DockingAreaPane dockingAreaPane, List<PositionableAdapter<DockingAreaPane>> removedDockingAreas) {
        ShortPathPart pathPart = path.next();
        adjustLevel(pathPart, removedDockingAreas);
        if (path.hasNext()) {
            DockingSplitPane splitPane = getSplitPane(pathPart, removedDockingAreas);
            splitPane.addDockingArea(path, dockingAreaPane, removedDockingAreas);

        } else {
            addDockingArea(pathPart, dockingAreaPane);
        }
    }

    private void addDockingArea(ShortPathPart pathPart, DockingAreaPane dockingArea) {
        // TODO: handle situatation if another child has the same position
        areaPanes.put(pathPart.getPosition(), new PositionableAdapter<>(dockingArea, pathPart.getPosition()));
        add(pathPart, dockingArea);
        dockingArea.setVisualized(true);
        System.out.println(DockingSplitPane.class.getName() + ": added docking area: " + dockingArea.getAreaId());
    }

    private void addSplitPane(ShortPathPart pathPart, DockingSplitPane splitPane) {
        splitPanes.put(splitPane.getPosition(), splitPane);
//        if (areaPanes.containsKey(pos)) {
//            DockingAreaPane areaPane = areaPanes.remove(pos);
//            int index = Collections.binarySearch(dockingSplitPaneChildren, areaPane, new PositionableComparator());
//            dockingSplitPaneChildren.remove(index);
//            splitPane.addDockingArea(pathPart, areaPane);
//        }
        add(pathPart, splitPane);
    }

    private DockingSplitPane getSplitPane(ShortPathPart shortPathPart, List<PositionableAdapter<DockingAreaPane>> removedDockingAreas) {
        if (!splitPanes.containsKey(shortPathPart.getPosition())) {
            DockingSplitPane splitPane = new DockingSplitPane(shortPathPart.getPosition(), getActualLevel() + 1,
                    getChildOrientation());
            addSplitPane(shortPathPart, splitPane);
            if (areaPanes.containsKey(shortPathPart.getPosition())) {
                PositionableAdapter<DockingAreaPane> areaPane = areaPanes.remove(shortPathPart.getPosition());
                removeDockingAreaOnly(areaPane);
                removedDockingAreas.add(areaPane);
            }
        }
        return splitPanes.get(shortPathPart.getPosition());
    }

    private void add(ShortPathPart pathPart, DockingSplitPaneChildBase child) {
//        shortPathParts.put(pathPart.getPosition(), pathPart);
        child.setParentSplitPane(this);
        PositionableAdapter<DockingSplitPaneChildBase> positionableChild =
                new PositionableAdapter<>(child, pathPart.getPosition());
        int insertionPoint = Positionables.getInsertionPoint(positionableChildren, positionableChild);
        positionableChildren.add(insertionPoint, positionableChild);
        dockingSplitPaneChildren.add(insertionPoint, child);
    }
//    private void addSkippedSplitPane(ShortPathPart pathPart) {
//        DockingSplitPane skippedSplitPane = new DockingSplitPane(pathPart.getPosition(), getActualLevel() + 1,
//                getChildOrientation());
//        for (Map.Entry<Integer, DockingSplitPane> entry : splitPanes.entrySet()) {
//            skippedSplitPane.addSplitPane(shortPathParts.get(entry.getKey()), entry.getValue());
//        }
//        splitPanes.clear();
//        for (Map.Entry<Integer, DockingAreaPane> entry : areaPanes.entrySet()) {
//            skippedSplitPane.addDockingArea(shortPathParts.get(entry.getKey()), entry.getValue());
//        }
//        areaPanes.clear();
//        dockingSplitPaneChildren.clear();
//        addSplitPane(pathPart, skippedSplitPane);
//    }

    private Orientation getChildOrientation() {
        return getOrientation().equals(Orientation.HORIZONTAL) ? Orientation.VERTICAL : Orientation.HORIZONTAL;
    }

    public boolean containsAnyDockingAreas() {
        boolean contains = false;
        if (splitPanes.isEmpty()) {
            contains = !areaPanes.isEmpty();
        } else {
            if (areaPanes.isEmpty()) {
                for (DockingSplitPane splitPane : splitPanes.values()) {
                    contains = splitPane.containsAnyDockingAreas();
                    if (contains) {
                        break;
                    }
                }
            }
        }
        return contains;
    }

    public boolean isEmpty() {
        return dockingSplitPaneChildren.isEmpty();
    }

    public void removeDockingArea(DockingAreaPane dockingArea) {
        removeDockingArea(dockingArea.getShortPath().iterator());
    }

    private void removeDockingArea(Iterator<ShortPathPart> path) {
        ShortPathPart pathPart = path.next();
        if (path.hasNext()) {
            DockingSplitPane splitPane = splitPanes.get(pathPart.getPosition());
            splitPane.removeDockingArea(path);
            if (!splitPane.containsAnyDockingAreas()) {
                removeSplitPane(splitPane);
            }
        } else {
            PositionableAdapter<DockingAreaPane> dockingArea = areaPanes.remove(pathPart.getPosition());
            removeDockingAreaOnly(dockingArea);
        }
    }

    /**
     * @return the children
     */
    public ObservableList<DockingSplitPaneChildBase> getDockingSplitPaneChildren() {
        return FXCollections.unmodifiableObservableList(dockingSplitPaneChildren);
    }

//    private void split(List<Integer> splitPah, Orientation splitOrientation) {
//        throw new UnsupportedOperationException("Not yet implemented");
//    }
    private void adjustLevel(ShortPathPart pathPart, List<PositionableAdapter<DockingAreaPane>> removedDockingAreas) {
        if (getActualLevel() != pathPart.getInActualLevel()) {
            if (isEmpty()) {
                adjustLevelOnly(pathPart);
            } else {
                adjustLevelOnly(pathPart);
                removeAllDockingAreas(removedDockingAreas);
//                if (getActualLevel() < pathPart.getInActualLevel()) {
//                    adjustLevelOnly(pathPart);
//                    removeAllDockingAreas(removedDockingAreas);
//                } else if (getActualLevel() > pathPart.getInActualLevel()) {
//                    DockingSplitPane splitPane = new DockingSplitPane(getPosition(), getActualLevel(), getOrientation());
//                    moveContentTo(splitPane);
//                    adjustLevelOnly(pathPart);
//                    addSplitPane(new ShortPathPart(getPosition(), getActualLevel(), getOrientation()), splitPane);
//                }
            }
        }
    }

    private void adjustLevelOnly(ShortPathPart pathPart) {
        actualLevel = pathPart.getInActualLevel();
        setOrientation(pathPart.getInOrientation());
    }

//    private void moveContentTo(DockingSplitPane splitPane) {
//        copyContentTo(splitPane);
//        clearContent();
//    }

//    private void copyContentTo(DockingSplitPane splitPane) {
////        splitPane.shortPathParts.putAll(shortPathParts);
//        splitPane.areaPanes.putAll(areaPanes);
//        for (PositionableAdapter<DockingAreaPane> areaPane : splitPane.areaPanes.values()) {
//            areaPane.getAdapted().setParentSplitPane(splitPane);
//        }
//        splitPane.splitPanes.putAll(splitPanes);
//        for (DockingSplitPane childSplitPane : splitPane.splitPanes.values()) {
//            childSplitPane.setParentSplitPane(splitPane);
//        }
//        splitPane.positionableChildren.addAll(positionableChildren);
//        splitPane.dockingSplitPaneChildren.addAll(dockingSplitPaneChildren);
//    }

//    private void clearContent() {
////        shortPathParts.clear();
//        areaPanes.clear();
//        splitPanes.clear();
//        positionableChildren.clear();
//        dockingSplitPaneChildren.clear();
//    }

    private void removeAllDockingAreas(List<PositionableAdapter<DockingAreaPane>> removedDockingAreas) {
        for (PositionableAdapter<DockingAreaPane> dockingArea : areaPanes.values()) {
            removeDockingAreaOnly(dockingArea);
            removedDockingAreas.add(dockingArea);
        }
        areaPanes.clear();
        for (DockingSplitPane splitPane : splitPanes.values()) {
            splitPane.removeAllDockingAreas(removedDockingAreas);
        }
    }

    private void removeDockingAreaOnly(PositionableAdapter<DockingAreaPane> dockingArea) {
        int index = Collections.binarySearch(positionableChildren, dockingArea, new PositionableComparator());
        positionableChildren.remove(index);
        dockingSplitPaneChildren.remove(index);
        dockingArea.getAdapted().setVisualized(false);
        remove(dockingArea.getAdapted());
    }

    private void removeEmptySplitPanes() {
        List<DockingSplitPane> dockingSplitPanes = new ArrayList<>(splitPanes.values()); // avoid ConcurrentModificationException
        for (DockingSplitPane splitPane : dockingSplitPanes) {
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
        remove(splitPane);
    }

    private void remove(DockingSplitPaneChildBase dockingSplitPaneChild) {
        dockingSplitPaneChild.setParentSplitPane(null);
    }

    /**
     * @return the position
     */
    public int getPosition() {
        return position;
    }
}
