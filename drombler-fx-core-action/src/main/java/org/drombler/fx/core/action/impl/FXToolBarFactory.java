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
package org.drombler.fx.core.action.impl;

import javafx.scene.control.ToolBar;
import org.drombler.acp.core.action.spi.ToolBarDescriptor;
import org.drombler.acp.core.action.spi.ToolBarFactory;
import org.osgi.service.component.annotations.Component;

/**
 *
 * @author puce
 */
@Component
public class FXToolBarFactory implements ToolBarFactory<ToolBar> {

    @Override
    public ToolBar createToolBar(ToolBarDescriptor toolBarDescriptor) {
        ToolBar toolBar = new ToolBar();
        toolBar.setVisible(toolBarDescriptor.isVisible());
        return toolBar;
    }
}
