#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

@Menus({
    @Menu(id = "Custom", displayName = "%customMenu.displayName", position = 110),
    @Menu(id = "Sub", displayName = "%subMenu.displayName", path = "Custom", position = 30)
})
@ToolBars({
    @ToolBar(id = "rectangle", displayName = "%rectangleToolBar.displayName", position = 50),
    @ToolBar(id = "circle", displayName = "%circleToolBar.displayName", position = 100)
})
package ${package}.sample.impl;

import org.drombler.acp.core.action.Menu;
import org.drombler.acp.core.action.Menus;
import org.drombler.acp.core.action.ToolBar;
import org.drombler.acp.core.action.ToolBars;