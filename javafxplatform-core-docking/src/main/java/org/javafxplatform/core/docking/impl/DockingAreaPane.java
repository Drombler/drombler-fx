/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.docking.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.SingleSelectionModel;
import org.javafxplatform.core.docking.DockablePane;
import org.javafxplatform.core.docking.skin.Stylesheets;
import org.javafxplatform.core.util.javafx.scene.control.ListSingleSelectionModel;
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
    private final Set<DockablePane> dockableSet = new HashSet<>();
    private final int position;
    private final boolean permanent;
    private DockingAreaManager parentManager;
    private final BooleanProperty visualized = new SimpleBooleanProperty(this, "visualized", false);
    private final ObjectProperty<SingleSelectionModel<PositionableAdapter<DockablePane>>> selectionModel =
            new SimpleObjectProperty<SingleSelectionModel<PositionableAdapter<DockablePane>>>(
            this, "singleSelectionModel", new ListSingleSelectionModel<>(dockables));

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

    public final SingleSelectionModel<PositionableAdapter<DockablePane>> getSelectionModel() {
        return singleModelProperty().get();
    }

    public final void setSelectionModel(SingleSelectionModel<PositionableAdapter<DockablePane>> selectionModel) {
        singleModelProperty().set(selectionModel);
    }

    public final ObjectProperty<SingleSelectionModel<PositionableAdapter<DockablePane>>> singleModelProperty() {
        return selectionModel;
    }

    public void addDockable(PositionableAdapter<DockablePane> dockable) {
        if (!dockableSet.contains(dockable.getAdapted())) {
            dockableSet.add(dockable.getAdapted());
            int insertionPoint = Positionables.getInsertionPoint(dockables, dockable);
            dockables.add(insertionPoint, dockable);
        }
    }

    public PositionableAdapter<DockablePane> removeDockable(int index) {
        PositionableAdapter<DockablePane> dockable = dockables.remove(index);
        dockableSet.remove(dockable.getAdapted());
        return dockable;
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

    void setParentManager(DockingAreaManager parentManager) {
        this.parentManager = parentManager;
    }

    public List<ShortPathPart> getShortPath() {
        return parentManager.getShortPath(this);
    }

    public boolean isVisualizable() {
        return isPermanent() || !getDockables().isEmpty();
    }
}
