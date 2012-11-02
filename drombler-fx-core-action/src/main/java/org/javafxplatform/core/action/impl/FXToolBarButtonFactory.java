/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.action.impl;

import javafx.scene.control.Button;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.drombler.acp.core.action.spi.ToolBarButtonFactory;
import org.drombler.acp.core.action.spi.ToolBarEntryDescriptor;
import org.javafxplatform.core.action.FXAction;

/**
 *
 * @author puce
 */
@Component
@Service
public class FXToolBarButtonFactory implements ToolBarButtonFactory<Button, FXAction> {

    @Override
    public Button createToolBarButton(ToolBarEntryDescriptor toolBarEntryDescriptor, FXAction action, int iconSize) {
        Button button = new Button();
        ButtonUtils.configureButton(button, toolBarEntryDescriptor, action, iconSize);
        return button;
    }
}
