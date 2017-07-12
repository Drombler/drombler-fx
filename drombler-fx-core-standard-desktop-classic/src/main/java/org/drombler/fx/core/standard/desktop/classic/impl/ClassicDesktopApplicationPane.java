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
package org.drombler.fx.core.standard.desktop.classic.impl;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import org.drombler.acp.core.action.spi.ApplicationToolBarContainerProvider;
import org.drombler.acp.core.action.spi.MenuBarMenuContainerProvider;
import org.drombler.acp.core.action.spi.MenuMenuItemContainerFactory;
import org.drombler.acp.core.action.spi.SeparatorMenuItemFactory;
import org.drombler.acp.core.action.spi.ToolBarContainer;
import org.drombler.acp.core.status.spi.StatusBarElementContainer;
import org.drombler.acp.core.status.spi.StatusBarElementContainerProvider;
import org.drombler.commons.fx.fxml.FXMLLoaders;
import org.drombler.commons.fx.scene.control.StatusBar;
import org.drombler.fx.core.action.FXMenuBarMenuContainer;
import org.drombler.fx.core.action.ToolBarContainerPane;
import org.drombler.fx.core.status.FXStatusBarElementContainer;

/**
 *
 * @author puce
 */
public class ClassicDesktopApplicationPane extends GridPane implements MenuBarMenuContainerProvider<MenuItem, Menu>,
        ContentPaneProvider, ApplicationToolBarContainerProvider<ToolBar, Node>, StatusBarElementContainerProvider, Initializable {

    private final FXMenuBarMenuContainer menuBarMenuContainer;
    private final FXStatusBarElementContainer statusBarElementContainer;

    @FXML
    private MenuBar menuBar;
    @FXML
    private ToolBarContainerPane toolBarContainerPane;
    @FXML
    private BorderPane contentPane;
    @FXML
    private StatusBar statusBar;

    public ClassicDesktopApplicationPane(MenuMenuItemContainerFactory<MenuItem, Menu> menuMenuItemContainerFactory, SeparatorMenuItemFactory<SeparatorMenuItem> separatorMenuItemFactory) throws
            IOException {
        load();
        menuBarMenuContainer = new FXMenuBarMenuContainer(menuBar, menuMenuItemContainerFactory, separatorMenuItemFactory);
        statusBarElementContainer = new FXStatusBarElementContainer(statusBar);
    }

    private void load() throws IOException {
        FXMLLoaders.loadRoot(this);
    }

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        menuBar.setUseSystemMenuBar(true);
    }

    @Override
    public BorderPane getContentPane() {
        return contentPane;
    }

    @Override
    public FXMenuBarMenuContainer getMenuBarMenuContainer() {
        return menuBarMenuContainer;
    }

    @Override
    public ToolBarContainer<ToolBar, Node> getApplicationToolBarContainer() {
        return toolBarContainerPane;
    }

    @Override
    public StatusBarElementContainer getStatusBarElementContainer() {
        return statusBarElementContainer;
    }

}
