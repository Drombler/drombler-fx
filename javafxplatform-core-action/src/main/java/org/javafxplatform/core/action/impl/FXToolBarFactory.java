/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.action.impl;

import javafx.scene.control.ToolBar;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.richclientplatform.core.action.spi.ToolBarDescriptor;
import org.richclientplatform.core.action.spi.ToolBarFactory;

/**
 *
 * @author puce
 */
@Component
@Service
public class FXToolBarFactory implements ToolBarFactory<ToolBar> {

    @Override
    public ToolBar createToolBar(ToolBarDescriptor toolBarDescriptor) {
        return new ToolBar();
    }
}
