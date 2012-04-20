/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.startup.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;
import org.javafxplatform.core.util.javafx.fxml.application.PlatformUtils;
import org.richclientplatform.core.action.spi.ToolBarContainer;
import org.richclientplatform.core.action.spi.ToolBarContainerListener;
import org.richclientplatform.core.action.spi.ToolBarContainerToolBarButtonEvent;
import org.richclientplatform.core.action.spi.ToolBarContainerToolBarEvent;
import org.richclientplatform.core.lib.util.PositionableAdapter;
import org.richclientplatform.core.lib.util.Positionables;

/**
 *
 * @author puce
 */
public class ToolBarContainerPane extends HBox implements ToolBarContainer<ToolBar, Button> {

    private final Map<String, ToolBar> toolBarsMap = new HashMap<>();
    private final List<PositionableAdapter<ToolBar>> toolBars = new ArrayList<>();
    private final List<ToolBarContainerListener<ToolBar, Button>> containerListeners = Collections.synchronizedList(
            new ArrayList<ToolBarContainerListener<ToolBar, Button>>()); // TODO: synchronized needed?
    private final ConcurrentMap<String, List<ToolBarContainerListener<ToolBar, Button>>> containerListenerMap = new ConcurrentHashMap<>(); // TODO: synchronized needed?

    @Override
    public void addToolBar(final String toolBarId, final PositionableAdapter<ToolBar> toolBarAdapter) {
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                int insertionPoint = Positionables.getInsertionPoint(toolBars, toolBarAdapter);
                getChildren().add(insertionPoint, toolBarAdapter.getAdapted());
                toolBars.add(insertionPoint, toolBarAdapter);
                toolBarsMap.put(toolBarId, toolBarAdapter.getAdapted());
                fireToolBarAddedEvent(toolBarAdapter, toolBarId);
            }
        };
        PlatformUtils.runOnFxApplicationThread(runnable); // TODO: needed?

    }

    @Override
    public void addToolBarButton(final String toolBarId, final PositionableAdapter<Button> toolBarButtonAdapter) {
        PlatformUtils.runOnFxApplicationThread(new Runnable() {

            @Override
            public void run() {
                ToolBar toolBar = toolBarsMap.get(toolBarId);
                // TODO: respect position
                toolBar.getItems().add(toolBarButtonAdapter.getAdapted());
                fireToolBarButtonAddedEvent(toolBarButtonAdapter, toolBarId);
            }
        }); // TODO: needed?

    }

    @Override
    public boolean isToolBarVisible(String toolBarId) {
        return toolBarsMap.get(toolBarId).isVisible();
    }

    @Override
    public void setToolBarVisible(final String toolBarId, final boolean visible) {
        PlatformUtils.runOnFxApplicationThread(new Runnable() {

            @Override
            public void run() {
                toolBarsMap.get(toolBarId).setVisible(visible);
            }
        }); // TODO: needed?

    }

    @Override
    public void addToolBarContainerListener(ToolBarContainerListener<ToolBar, Button> containerListener) {
        containerListeners.add(containerListener);
    }

    @Override
    public void removeToolBarContainerListener(ToolBarContainerListener<ToolBar, Button> containerListener) {
        containerListeners.remove(containerListener);
    }

    private void fireToolBarAddedEvent(PositionableAdapter<? extends ToolBar> toolBar, String toolBarId) {
        ToolBarContainerToolBarEvent<ToolBar, Button> event = new ToolBarContainerToolBarEvent<>(this, toolBar);
        for (ToolBarContainerListener<ToolBar, Button> containerListener : containerListeners) {
            containerListener.toolBarAdded(event);
        }

        if (containerListenerMap.containsKey(toolBarId)) {
            for (ToolBarContainerListener<ToolBar, Button> containerListener : containerListenerMap.get(toolBarId)) {
                containerListener.toolBarAdded(event);
            }
        }
    }

    private void fireToolBarButtonAddedEvent(PositionableAdapter<? extends Button> toolBarButton, String toolBarId) {
        ToolBarContainerToolBarButtonEvent<ToolBar, Button> event = new ToolBarContainerToolBarButtonEvent<>(this,
                toolBarButton);
        for (ToolBarContainerListener<ToolBar, Button> containerListener : containerListeners) {
            containerListener.toolBarButtonAdded(event);
        }

        if (containerListenerMap.containsKey(toolBarId)) {
            for (ToolBarContainerListener<ToolBar, Button> containerListener : containerListenerMap.get(toolBarId)) {
                containerListener.toolBarButtonAdded(event);
            }
        }
    }

    @Override
    public void addToolBarContainerListener(String toolBarId, ToolBarContainerListener<ToolBar, Button> containerListener) {
        containerListenerMap.putIfAbsent(toolBarId,
                Collections.synchronizedList(new ArrayList<ToolBarContainerListener<ToolBar, Button>>()));
        containerListenerMap.get(toolBarId).add(containerListener);
    }

    @Override
    public void removeToolBarContainerListener(String toolBarId, ToolBarContainerListener<ToolBar, Button> containerListener) {
        List<ToolBarContainerListener<ToolBar, Button>> listeners = containerListenerMap.get(toolBarId);
        if (listeners != null) {
            listeners.remove(containerListener);
        }
    }
}
