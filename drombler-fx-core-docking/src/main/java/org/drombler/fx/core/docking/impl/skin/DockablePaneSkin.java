/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drombler.fx.core.docking.impl.skin;

import javafx.scene.Node;
import javafx.scene.control.Skin;
import javafx.scene.layout.BorderPane;
import org.drombler.fx.core.docking.DockablePane;

/**
 *
 * @author puce
 */
public class DockablePaneSkin implements Skin<DockablePane> {
    private DockablePane control;
    private BorderPane pane = new BorderPane();

    public DockablePaneSkin(DockablePane control) {
        this.control = control;
        pane.centerProperty().bindBidirectional(control.contentProperty());
    }

    
    @Override
    public DockablePane getSkinnable() {
        return control;
    }

    @Override
    public Node getNode() {
        return pane;
    }

    @Override
    public void dispose() {
        control = null;
        pane = null;
    }
    
}
