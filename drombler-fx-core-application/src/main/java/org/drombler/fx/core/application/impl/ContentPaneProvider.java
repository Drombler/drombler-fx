/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drombler.fx.core.application.impl;

import javafx.scene.layout.BorderPane;

/**
 *
 * @author puce
 */
public interface ContentPaneProvider {
// TODO: replace BorderPane with a custom layout pane similar to DockablePane

    BorderPane getContentPane();
}
