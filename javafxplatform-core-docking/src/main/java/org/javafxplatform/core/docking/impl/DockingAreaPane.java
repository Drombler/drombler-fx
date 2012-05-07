/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.docking.impl;

import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.javafxplatform.core.docking.DockablePane;
import org.javafxplatform.core.docking.skin.Stylesheets;
import org.richclientplatform.core.lib.util.PositionableAdapter;
import org.richclientplatform.core.lib.util.Positionables;

/**
 *
 * @author puce
 */
public class DockingAreaPane extends DockingSplitPaneChildBase {

    private static final String DEFAULT_STYLE_CLASS = "docking-area-pane";
    private final String areaId;
    private final ObservableList<PositionableAdapter<DockablePane>> dockables = FXCollections.observableArrayList();
    private final int position;
    private final boolean permanent;
    private DockingAreaManager parentManager;
    private BooleanProperty visualized = new SimpleBooleanProperty(this, "visualized", false);

    public DockingAreaPane(String areaId, int position, boolean permanent) {
        super(false);
        this.areaId = areaId;
        this.position = position;
        this.permanent = permanent;
        getStyleClass().setAll(DEFAULT_STYLE_CLASS);
    }

    @Override
    protected String getUserAgentStylesheet() {
        return Stylesheets.getDefaultStylesheet();
    }

    /**
     * @return the areaId
     */
    public String getAreaId() {
        return areaId;
    }

    public int getPosition() {
        return position;
    }

    public final boolean isVisualized() {
        return visualizedProperty().get();
    }

    public final void setVisualized(boolean visualized) {
        visualizedProperty().set(visualized);
    }

    public BooleanProperty visualizedProperty() {
        return visualized;
    }

    public void addDockable(PositionableAdapter<DockablePane> dockable) {
        int insertionPoint = Positionables.getInsertionPoint(dockables, dockable);
        dockables.add(insertionPoint, dockable);
    }

    public PositionableAdapter<DockablePane> removeDockable(int index) {
        return dockables.remove(index);
    }

    /**
     * @return the dockablePanes
     */
    public ObservableList<PositionableAdapter<DockablePane>> getDockables() {
        return FXCollections.unmodifiableObservableList(dockables);
    }

    /**
     * @return the permanent
     */
    public boolean isPermanent() {
        return permanent;
    }

    public void setParentManager(DockingAreaManager parentManager) {
        this.parentManager = parentManager;
    }

    public List<ShortPathPart> getShortPath() {
        return parentManager.getShortPath(this);
    }

    public boolean isVisualizable() {
        return isPermanent() || !getDockables().isEmpty();
    }
}
