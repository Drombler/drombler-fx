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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javafx.geometry.Orientation;

/**
 *
 * @author puce
 */
// TODO: check thread safty
class DockingAreaManager {

    private final Map<Integer, DockingAreaPane> dockingAreas = new HashMap<>();
    private final Map<Integer, DockingAreaManager> dockingAreaManagers = new HashMap<>();
    private final DockingAreaManager parent;
    private final Integer position;
    private final int level;
    private final Orientation orientation;

    public DockingAreaManager(DockingAreaManager parent, Integer position, int level, Orientation orientation) {
        this.parent = parent;
        this.position = position;
        this.level = level;
        this.orientation = orientation;
    }

    public void addDockingArea(List<Integer> path, DockingAreaPane dockingArea) {
        addDockingArea(path.iterator(), dockingArea);
    }

    private void addDockingArea(Iterator<Integer> path, DockingAreaPane dockingArea) {
        if (path.hasNext()) {
            Integer childPosition = path.next();
            if (!dockingAreaManagers.containsKey(childPosition)) {
                dockingAreaManagers.put(childPosition, new DockingAreaManager(this, childPosition, level + 1,
                        getChildOrientation()));
            }
            dockingAreaManagers.get(childPosition).addDockingArea(path, dockingArea);
        } else {
            // TODO: handle case where 2 dockingAreas have the same position
            // TODO: handle case where dockingArea and dockingAreaManager have the same position
            dockingArea.setParentManager(this);
            dockingAreas.put(dockingArea.getPosition(), dockingArea);
        }
    }

    private ShortPathPart createShortPathPart(Integer position) {
        return new ShortPathPart(position, level, orientation);
    }

    private boolean isShortPathRelevant(Integer position, boolean emptyPath) {
        return (!isOnlyActualContent(position)) || (emptyPath && parent == null);
    }

    private boolean isOnlyActualContent(Integer position) {
        return isCurrentlyOnlyActualContent(position)
                || isFutureOnlyActualContent(position);
    }

    private boolean isCurrentlyOnlyActualContent(Integer position) {
        return containsActualPosition(position) && getActualContentSize() == 1;
    }

    private boolean isFutureOnlyActualContent(Integer position) {
        return !containsActualPosition(position) && getActualContentSize() == 0;
    }

    private int getActualContentSize() {
        return getNonEmptyAreaManagers().size() + getVisualizableDockingAreas().size();
    }

    private Map<Integer, DockingAreaManager> getNonEmptyAreaManagers() {
        Map<Integer, DockingAreaManager> nonEmptyAreaManagers = new HashMap<>();
        for (Map.Entry<Integer, DockingAreaManager> entry : dockingAreaManagers.entrySet()) {
            if (entry.getValue().getActualContentSize() > 0) {
                nonEmptyAreaManagers.put(entry.getKey(), entry.getValue());
            }
        }
        return nonEmptyAreaManagers;
    }

    private Map<Integer, DockingAreaPane> getVisualizableDockingAreas() {
        Map<Integer, DockingAreaPane> visualizableDockingAreas = new HashMap<>();
        for (Map.Entry<Integer, DockingAreaPane> entry : dockingAreas.entrySet()) {
            if (entry.getValue().isVisual()) {
                visualizableDockingAreas.put(entry.getKey(), entry.getValue());
            }
        }
        return visualizableDockingAreas;
    }

    private boolean containsActualPosition(Integer position) {
        return (dockingAreaManagers.containsKey(position)
                && dockingAreaManagers.get(position).getActualContentSize() > 0)
                || (dockingAreas.containsKey(position)
                && dockingAreas.get(position).isVisual());
    }

    private Orientation getChildOrientation() {
        return orientation.equals(Orientation.HORIZONTAL) ? Orientation.VERTICAL : Orientation.HORIZONTAL;
    }

    List<ShortPathPart> getShortPath(DockingAreaPane dockingArea) {
        if (!(dockingAreas.containsKey(dockingArea.getPosition())
                && dockingAreas.get(dockingArea.getPosition()).equals(dockingArea))) {
            throw new IllegalStateException(
                    "The specified docking area must be a child of this manager: " + dockingArea);
        }

        if (dockingArea.isVisual()) {
            return calculateShortPath(dockingArea);
        } else {
            return Collections.emptyList();
        }
    }

    private List<ShortPathPart> calculateShortPath(DockingAreaPane dockingArea) {
        List<ShortPathPart> shortPath = new ArrayList<>();
        calculateReversedShortPath(dockingArea, shortPath);
        Collections.reverse(shortPath);
        return shortPath;
    }

    private void calculateReversedShortPath(DockingAreaPane dockingArea, List<ShortPathPart> shortPath) {
        Integer currentChildPosition = dockingArea.getPosition();
        for (DockingAreaManager currentParent = this; currentParent != null; currentParent = currentParent.parent) {
            if (currentParent.isShortPathRelevant(currentChildPosition, shortPath.isEmpty())) {
                shortPath.add(currentParent.createShortPathPart(currentChildPosition));
            }
            currentChildPosition = currentParent.position;
        }
    }
}
