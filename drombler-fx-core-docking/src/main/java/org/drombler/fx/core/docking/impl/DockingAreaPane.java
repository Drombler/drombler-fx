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
import org.drombler.acp.core.docking.spi.LayoutConstraintsDescriptor;
import org.drombler.fx.core.commons.fx.scene.control.ListSingleSelectionModel;
import org.drombler.fx.core.docking.DockablePane;
import org.drombler.fx.core.docking.impl.skin.Stylesheets;
import org.softsmithy.lib.util.PositionableAdapter;
import org.softsmithy.lib.util.Positionables;

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
    private LayoutConstraintsDescriptor layoutConstraints;

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

    public boolean addDockable(PositionableAdapter<DockablePane> dockable) {
        if (!dockableSet.contains(dockable.getAdapted())) {
            dockableSet.add(dockable.getAdapted());
            int insertionPoint = Positionables.getInsertionPoint(dockables, dockable);
            dockables.add(insertionPoint, dockable);
            return true;
        } else {
            return false;
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

    //TODO: good name?
    public boolean isVisualizable() {
        return isPermanent() || !getDockables().isEmpty();
    }

    @Override
    public LayoutConstraintsDescriptor getLayoutConstraints() {
        return layoutConstraints;
    }

    public void setLayoutConstraints(LayoutConstraintsDescriptor layoutConstraints) {
        this.layoutConstraints = layoutConstraints;
    }
}
