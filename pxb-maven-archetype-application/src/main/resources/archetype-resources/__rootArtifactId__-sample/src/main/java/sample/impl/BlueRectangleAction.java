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
@ToggleAction(id = "blueRectangle", category = "test", displayName = "#blueRectangle.displayName", accelerator = "Shortcut+B",
icon = "blue-rectangle.png")
@ToggleMenuEntry(path = "Custom", position = 1240)
@ToolBarToggleEntry(toolBarId = "rectangle", position = 170)
public class BlueRectangleAction extends AbstractToggleActionListener<Object> {

    @Override
    public void onSelectionChanged(boolean oldValue, boolean newValue) {
        System.out.println("Blue Rectangle: " + newValue);
    }
}
