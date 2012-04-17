/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.startup.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;
import org.javafxplatform.core.util.javafx.fxml.application.PlatformUtils;
import org.richclientplatform.core.action.spi.ToolBarContainer;
import org.richclientplatform.core.lib.util.PositionableAdapter;
import org.richclientplatform.core.lib.util.Positionables;

/**
 *
 * @author puce
 */
public class ToolBarContainerPane extends HBox implements ToolBarContainer<ToolBar, Button> {

    private final Map<String, ToolBar> toolBarsMap = new HashMap<>();
    private final List<PositionableAdapter<ToolBar>> toolBars = new ArrayList<>();

    @Override
    public void addToolBar(final String toolBarId, final PositionableAdapter<ToolBar> toolBarAdapter) {
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                int insertionPoint = Positionables.getInsertionPoint(toolBars, toolBarAdapter);
                getChildren().add(insertionPoint, toolBarAdapter.getAdapted());
                toolBars.add(insertionPoint, toolBarAdapter);
                toolBarsMap.put(toolBarId, toolBarAdapter.getAdapted());
            }
        };
        PlatformUtils.runOnFxApplicationThread(runnable); // TODO: needed?

    }

    @Override
    public void addToolBarButton(final String toolBarId, final PositionableAdapter<Button> toolBarButtonAdapter) {
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                ToolBar toolBar = toolBarsMap.get(toolBarId);
                // TODO: respect position
                toolBar.getItems().add(toolBarButtonAdapter.getAdapted());
            }
        };
        PlatformUtils.runOnFxApplicationThread(runnable); // TODO: needed?

    }
}
