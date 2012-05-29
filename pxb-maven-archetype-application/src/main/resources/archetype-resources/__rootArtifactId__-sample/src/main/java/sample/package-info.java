#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

@Menus({
    @Menu(id = "Custom", displayName = "#customMenu.displayName", position = 110),
    @Menu(id = "Sub", displayName = "#subMenu.displayName", path = "Custom", position = 30)
})
@ToolBars({
    @ToolBar(id = "myCustom", displayName = "#myCustomToolBar.displayName", position = 50)
})
package ${package}.sample;

import org.richclientplatform.core.action.Menu;
import org.richclientplatform.core.action.Menus;
import org.richclientplatform.core.action.ToolBar;
import org.richclientplatform.core.action.ToolBars;