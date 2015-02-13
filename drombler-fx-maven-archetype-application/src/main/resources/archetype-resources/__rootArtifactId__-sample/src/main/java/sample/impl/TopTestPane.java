#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ${package}.sample.impl;

import javafx.scene.layout.BorderPane;
import org.drombler.acp.core.docking.ViewDocking;
import org.drombler.acp.core.docking.WindowMenuEntry;
import org.drombler.commons.fx.fxml.FXMLLoaders;

@ViewDocking(areaId = "top", position = 10, displayName = "%displayName", icon = "top-test-pane.png",
accelerator = "Shortcut+1",
menuEntry =
@WindowMenuEntry(path = "", position = 20))
public class TopTestPane extends BorderPane {

    public TopTestPane() {
        loadFXML();
    }

    private void loadFXML() {
        FXMLLoaders.loadRoot(this);
    }
    
}
