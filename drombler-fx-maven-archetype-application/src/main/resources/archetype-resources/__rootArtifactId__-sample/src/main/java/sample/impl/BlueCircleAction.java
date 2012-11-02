#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ${package}.sample.impl;

import org.richclientplatform.core.action.AbstractToggleActionListener;
import org.richclientplatform.core.action.ToggleAction;
import org.richclientplatform.core.action.ToggleMenuEntry;
import org.richclientplatform.core.action.ToolBarToggleEntry;

/**
 *
 * @author puce
 */
@ToggleAction(id = "blueCircle", category = "test", displayName = "#blueCircle.displayName", accelerator = "Shortcut+U",
icon = "blue-circle.png")
@ToggleMenuEntry(path = "Custom/Sub", position = 30, toggleGroupId="circle")
@ToolBarToggleEntry(toolBarId = "circle", position = 30, toggleGroupId="circle")
public class BlueCircleAction extends AbstractToggleActionListener<Object> {

    @Override
    public void onSelectionChanged(boolean oldValue, boolean newValue) {
        System.out.println("Blue Circle: " + newValue);
    }
}
