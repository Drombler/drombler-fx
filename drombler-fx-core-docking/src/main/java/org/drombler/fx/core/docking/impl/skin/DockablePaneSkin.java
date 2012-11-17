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
