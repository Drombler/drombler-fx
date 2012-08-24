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
@ToggleAction(id = "redCircle", category = "test", displayName = "#redCircle.displayName", accelerator = "Shortcut+D",
icon = "red-circle.png")
@ToggleMenuEntry(path = "Custom/Sub", position = 10, toggleGroupId="circle")
@ToolBarToggleEntry(toolBarId = "circle", position = 10, toggleGroupId="circle")
public class RedCircleAction extends AbstractToggleActionListener<Object> {

    @Override
    public void onSelectionChanged(boolean oldValue, boolean newValue) {
        System.out.println("Red Circle: " + newValue);
    }
}
