#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ${package}.sample.impl;

import javafx.scene.control.Label;
import org.drombler.acp.core.docking.ViewDocking;
import org.drombler.acp.core.docking.WindowMenuEntry;
import org.drombler.fx.core.docking.DockablePane;
/**
 *
 * @author puce
 */
@ViewDocking(areaId = "bottom", position = 10, displayName = "#BottomTestPane.displayName",
menuEntry =
@WindowMenuEntry(path = "", position = 30))
public class BottomTestPane extends DockablePane{

    public BottomTestPane() {
        setContent(new Label("bottom"));
    }
    
}
