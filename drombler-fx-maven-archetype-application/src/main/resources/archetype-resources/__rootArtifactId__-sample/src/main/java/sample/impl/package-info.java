#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

@Menu(id = "Custom", displayName = "%customMenu.displayName", position = 110)
@Menu(id = "Sub", displayName = "%subMenu.displayName", path = "Custom", position = 30)
@ToolBar(id = "rectangle", displayName = "%rectangleToolBar.displayName", position = 50)
@ToolBar(id = "circle", displayName = "%circleToolBar.displayName", position = 100)
@DockingArea(id = "test1", kind = DockingAreaKind.VIEW, position = 20,
        path = {20, 40, 50, 10}, layoutConstraints = @LayoutConstraints(prefWidth = 200))
@DockingArea(id = "test2", kind = DockingAreaKind.VIEW, position = 20,
        path = {20, 40, 50, 30}, layoutConstraints = @LayoutConstraints(prefWidth = 200))
package ${package}.sample.impl;

import org.drombler.acp.core.action.Menu;
import org.drombler.acp.core.action.ToolBar;
import org.drombler.acp.core.docking.DockingArea;
import org.drombler.acp.core.docking.LayoutConstraints;
import org.drombler.commons.docking.DockingAreaKind;
