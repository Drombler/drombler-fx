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
import javafx.scene.Node;
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
public class ToolBarContainerPane extends HBox implements ToolBarContainer<ToolBar, Node> {

    private final Map<String, PositionableAdapter<ToolBar>> toolBarsMap = new ConcurrentHashMap<>();// TODO: synchronized needed?
    private final List<PositionableAdapter<ToolBar>> toolBars = Collections.synchronizedList(
            new ArrayList<PositionableAdapter<ToolBar>>());// TODO: synchronized needed?
    private final ConcurrentMap<String, List<PositionableAdapter<? extends Node>>> toolBarButtonsMap = new ConcurrentHashMap<>();// TODO: synchronized needed?
    private final List<ToolBarContainerListener<ToolBar, Node>> containerListeners = Collections.synchronizedList(
            new ArrayList<ToolBarContainerListener<ToolBar, Node>>()); // TODO: synchronized needed?
    private final ConcurrentMap<String, List<ToolBarContainerListener<ToolBar, Node>>> containerListenerMap = new ConcurrentHashMap<>(); // TODO: synchronized needed?

    @Override
    public void addToolBar(final String toolBarId, final PositionableAdapter<ToolBar> toolBarAdapter) {
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                toolBarsMap.put(toolBarId, toolBarAdapter);
                toolBarButtonsMap.putIfAbsent(toolBarId,
                        Collections.synchronizedList(new ArrayList<PositionableAdapter<? extends Node>>()));
                if (toolBarAdapter.getAdapted().isVisible()) {
                    positionToolBar(toolBarAdapter);
                }
                fireToolBarAddedEvent(toolBarAdapter, toolBarId);
            }
        };
        PlatformUtils.runOnFxApplicationThread(runnable); // TODO: needed?

    }

    private void positionToolBar(PositionableAdapter<ToolBar> toolBarAdapter) {
        int insertionPoint = Positionables.getInsertionPoint(toolBars, toolBarAdapter);
        getChildren().add(insertionPoint, toolBarAdapter.getAdapted());
        toolBars.add(insertionPoint, toolBarAdapter);
    }

    private void hideToolBar(PositionableAdapter<ToolBar> toolBarAdapter) {
        int index = toolBars.indexOf(toolBarAdapter);
        getChildren().remove(index);
        toolBars.remove(index);
    }

    @Override
    public void addToolBarButton(final String toolBarId, final PositionableAdapter<? extends Node> toolBarButtonAdapter) {
        PlatformUtils.runOnFxApplicationThread(new Runnable() {

            @Override
            public void run() {
                List<PositionableAdapter<? extends Node>> toolBarButtons = toolBarButtonsMap.get(toolBarId);
                int insertionPoint = Positionables.getInsertionPoint(toolBarButtons, toolBarButtonAdapter);
                ToolBar toolBar = toolBarsMap.get(toolBarId).getAdapted();
                toolBar.getItems().add(insertionPoint, toolBarButtonAdapter.getAdapted());
                toolBarButtons.add(insertionPoint, toolBarButtonAdapter);
                fireToolBarButtonAddedEvent(toolBarButtonAdapter, toolBarId);
            }
        }); // TODO: needed?

    }

    @Override
    public boolean isToolBarVisible(String toolBarId) {
        return toolBarsMap.get(toolBarId).getAdapted().isVisible();
    }

    @Override
    public void setToolBarVisible(final String toolBarId, final boolean visible) {
        PlatformUtils.runOnFxApplicationThread(new Runnable() {

            @Override
            public void run() {
                if (toolBarsMap.containsKey(toolBarId)) {
                    PositionableAdapter<ToolBar> toolBar = toolBarsMap.get(toolBarId);
                    if (toolBar.getAdapted().isVisible() && !visible) {
                        hideToolBar(toolBar);
                    } else if (!toolBar.getAdapted().isVisible() && visible) {
                        positionToolBar(toolBar);
                    }
                    toolBar.getAdapted().setVisible(visible);
                }
            }
        }); // TODO: needed?

    }

    @Override
    public void addToolBarContainerListener(ToolBarContainerListener<ToolBar, Node> containerListener) {
        containerListeners.add(containerListener);
    }

    @Override
    public void removeToolBarContainerListener(ToolBarContainerListener<ToolBar, Node> containerListener) {
        containerListeners.remove(containerListener);
    }

    private void fireToolBarAddedEvent(PositionableAdapter<? extends ToolBar> toolBar, String toolBarId) {
        ToolBarContainerToolBarEvent<ToolBar, Node> event = new ToolBarContainerToolBarEvent<>(this, toolBar);
        for (ToolBarContainerListener<ToolBar, Node> containerListener : containerListeners) {
            containerListener.toolBarAdded(event);
        }

        if (containerListenerMap.containsKey(toolBarId)) {
            for (ToolBarContainerListener<ToolBar, Node> containerListener : containerListenerMap.get(toolBarId)) {
                containerListener.toolBarAdded(event);
            }
        }
    }

    private void fireToolBarButtonAddedEvent(PositionableAdapter<? extends Node> toolBarButton, String toolBarId) {
        ToolBarContainerToolBarButtonEvent<ToolBar, Node> event = new ToolBarContainerToolBarButtonEvent<>(this,
                toolBarButton);
        for (ToolBarContainerListener<ToolBar, Node> containerListener : containerListeners) {
            containerListener.toolBarButtonAdded(event);
        }

        if (containerListenerMap.containsKey(toolBarId)) {
            for (ToolBarContainerListener<ToolBar, Node> containerListener : containerListenerMap.get(toolBarId)) {
                containerListener.toolBarButtonAdded(event);
            }
        }
    }

    @Override
    public void addToolBarContainerListener(String toolBarId, ToolBarContainerListener<ToolBar, Node> containerListener) {
        containerListenerMap.putIfAbsent(toolBarId,
                Collections.synchronizedList(new ArrayList<ToolBarContainerListener<ToolBar, Node>>()));
        containerListenerMap.get(toolBarId).add(containerListener);
    }

    @Override
    public void removeToolBarContainerListener(String toolBarId, ToolBarContainerListener<ToolBar, Node> containerListener) {
        List<ToolBarContainerListener<ToolBar, Node>> listeners = containerListenerMap.get(toolBarId);
        if (listeners != null) {
            listeners.remove(containerListener);
        }
    }
}
