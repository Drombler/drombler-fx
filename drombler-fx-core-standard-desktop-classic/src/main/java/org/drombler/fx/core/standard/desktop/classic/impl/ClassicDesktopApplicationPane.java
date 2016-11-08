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
import org.drombler.acp.core.action.spi.SeparatorMenuItemFactory;
import org.drombler.acp.core.action.spi.ToolBarContainer;
import org.drombler.commons.fx.fxml.FXMLLoaders;
import org.drombler.fx.core.action.MenuBarMenuContainer;

/**
 *
 * @author puce
 */
public class ClassicDesktopApplicationPane extends GridPane implements MenuBarMenuContainerProvider<MenuItem, Menu>,
        ContentPaneProvider, ApplicationToolBarContainerProvider<ToolBar, Node>, Initializable {

    private final MenuBarMenuContainer menuBarMenuContainer;
    @FXML
    private MenuBar menuBar;
    @FXML
    private ToolBarContainerPane toolBarContainerPane;
    @FXML
    private BorderPane contentPane;

    public ClassicDesktopApplicationPane(SeparatorMenuItemFactory<SeparatorMenuItem> separatorMenuItemFactory) throws IOException {
        load();
        menuBarMenuContainer = new MenuBarMenuContainer(menuBar, separatorMenuItemFactory);
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
    public MenuBarMenuContainer getMenuBarMenuContainer() {
        return menuBarMenuContainer;
    }

    @Override
    public ToolBarContainer<ToolBar, Node> getApplicationToolBarContainer() {
        return toolBarContainerPane;
    }

}
