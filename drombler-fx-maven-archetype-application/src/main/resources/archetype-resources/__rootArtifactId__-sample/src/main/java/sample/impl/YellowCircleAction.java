#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ${package}.sample.impl;

import ${package}.sample.ColoredCircle;
import org.drombler.acp.core.action.ToggleAction;
import org.drombler.acp.core.action.ToggleMenuEntry;
import org.drombler.acp.core.action.ToolBarToggleEntry;


@ToggleAction(id = "yellowCircle", category = "test", displayName = "%displayName", accelerator = "Shortcut+L",
        icon = "yellow-circle.png")
@ToggleMenuEntry(path = "Custom/Sub", position = 20, toggleGroupId = "circle")
@ToolBarToggleEntry(toolBarId = "circle", position = 20, toggleGroupId = "circle")
public class YellowCircleAction extends AbstractColoredCircleAction {

    public YellowCircleAction() {
        super(ColoredCircle.YELLOW);
    }
}
