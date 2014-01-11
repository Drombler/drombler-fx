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
    /**
     * The area ID.
     */
    private final String areaId;
    private final ObservableList<PositionableAdapter<DockablePane>> dockables = FXCollections.observableArrayList();
    private final ObservableList<PositionableAdapter<DockablePane>> unmodifiableDockables = FXCollections.
            unmodifiableObservableList(dockables);
    private final Set<DockablePane> dockableSet = new HashSet<>();
    private final int position;
    /**
     * Flag if the space for this docking area should be preserved, if it's empty, or if it's space should be freed.
     */
    private final boolean permanent;
    private DockingAreaManager parentManager;
    /**
     * Flag if this DockingArea has been added to a {@link DockingSplitPane} already.
     */
    private final BooleanProperty visualized = new SimpleBooleanProperty(this, "visualized", false);
    private final ObjectProperty<SingleSelectionModel<PositionableAdapter<DockablePane>>> selectionModel
            = new SimpleObjectProperty<>(this, "singleSelectionModel", new ListSingleSelectionModel<>(dockables));
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
        return unmodifiableDockables;
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

    /**
     * Gets the short path.
     *
     * The short path is the path without any unnecessary split panes.
     *
     * @return a {@link List} of {@link ShortPathPart}s forming the short path.
     */
    public List<ShortPathPart> getShortPath() {
        return parentManager.getShortPath(this);
    }

    /**
     * Indicates if this DockingAreaPane is either {@link #isPermanent()} or non-empty.
     *
     * @return returns true if permanent or non-empty.
     */
    //TODO: good name?
    public boolean isVisualizable() {
        return isPermanent() || !getDockables().isEmpty();
    }

    // when the last dockable is beeing removed from the docking area, 
    // the docking area is not visualizable (empty) but still visualized 
    // (child of a DockingSplitPane; and thus has to be removed from it)
    //TODO: good name?
    public boolean isVisual() {
        return isVisualizable() || isVisualized();
    }

    @Override
    public LayoutConstraintsDescriptor getLayoutConstraints() {
        return layoutConstraints;
    }

    public void setLayoutConstraints(LayoutConstraintsDescriptor layoutConstraints) {
        this.layoutConstraints = layoutConstraints;
    }

    @Override
    public String toString() {
        return DockingAreaPane.class.getSimpleName() + "[areaId=" + areaId
                + ", position=" + position + ", permanent=" + permanent
                + ", visuablizable=" + isVisualizable()
                + ", visualized=" + isVisualized() + "]";
    }
}
