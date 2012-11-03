/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drombler.fx.core.action.impl;

import javafx.scene.control.ToolBar;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.drombler.acp.core.action.spi.ToolBarDescriptor;
import org.drombler.acp.core.action.spi.ToolBarFactory;

/**
 *
 * @author puce
 */
@Component
@Service
public class FXToolBarFactory implements ToolBarFactory<ToolBar> {

    @Override
    public ToolBar createToolBar(ToolBarDescriptor toolBarDescriptor) {
        ToolBar toolBar = new ToolBar();
        toolBar.setVisible(toolBarDescriptor.isVisible());
        return toolBar;
    }
}
