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
import javafx.geometry.Orientation;

/**
 *
 * @author puce
 */
public class DockingAreaManager {

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
        return (!isOnlyContent(position)) || (emptyPath && parent == null);
    }

    private boolean isOnlyContent(Integer position) {
        return (containsPosition(position) && getContentSize() == 1)
                || (!containsPosition(position) && getContentSize() == 0);
    }

    private int getContentSize() {
        return dockingAreaManagers.size() + dockingAreas.size();
    }

    private boolean containsPosition(Integer position) {
        return dockingAreaManagers.containsKey(position) || dockingAreas.containsKey(position);
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
        List<ShortPathPart> shortPath = new ArrayList<>();
        Integer currentChildPosition = dockingArea.getPosition();
        for (DockingAreaManager currentParent = this; currentParent != null; currentParent = currentParent.parent) {
            if (currentParent.isShortPathRelevant(currentChildPosition, shortPath.isEmpty())) {
                shortPath.add(currentParent.createShortPathPart(currentChildPosition));
            }
            currentChildPosition = currentParent.position;
        }
        Collections.reverse(shortPath);
        return shortPath;
    }
}
