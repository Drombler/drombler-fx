#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ${package}.sample.impl;

import org.drombler.acp.core.action.AbstractToggleActionListener;
import org.drombler.acp.core.action.ToggleAction;
import org.drombler.acp.core.action.ToggleMenuEntry;
import org.drombler.acp.core.action.ToolBarToggleEntry;

/**
 *
 * @author puce
 */
@ToggleAction(id = "redRectangle", category = "test", displayName = "#redRectangle.displayName", accelerator = "Shortcut+R",
icon = "red-rectangle.png")
@ToggleMenuEntry(path = "Custom", position = 1200)
@ToolBarToggleEntry(toolBarId = "rectangle", position = 150)
public class RedRectangleAction extends AbstractToggleActionListener<Object> {

    @Override
    public void onSelectionChanged(boolean oldValue, boolean newValue) {
        System.out.println("Red Rectangle: " + newValue);
    }
}
