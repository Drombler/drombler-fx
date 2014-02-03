#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ${package}.sample.impl;

import java.io.IOException;
import org.drombler.acp.core.docking.ViewDocking;
import org.drombler.acp.core.docking.WindowMenuEntry;
import org.drombler.commons.fx.docking.DockablePane;
import org.drombler.commons.fx.fxml.FXMLLoaders;

@ViewDocking(areaId = "bottom", position = 10, displayName = "%BottomTestPane.displayName", icon = "bottom-test-pane.png",
accelerator = "Shortcut+2",
menuEntry =
@WindowMenuEntry(path = "", position = 30))
public class BottomTestPane extends DockablePane{

    public BottomTestPane() throws IOException {
        loadFXML();
    }

    private void loadFXML() throws IOException {
        FXMLLoaders.loadRoot(this);
    }
    
}
