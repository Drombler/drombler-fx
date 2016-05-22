 #set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package $

{package}.sample.impl;

import javafx.scene.layout.BorderPane;
import org.drombler.acp.core.docking.ViewDocking;
import org.drombler.acp.core.docking.WindowMenuEntry;
import org.drombler.commons.fx.fxml.FXMLLoaders;

@ViewDocking(areaId = "test1", position = 10, displayName = "%displayName",
        menuEntry = @WindowMenuEntry(path = "", position = 100))
public class Test1TestPane extends BorderPane {

    public Test1TestPane() {
        loadFXML();
    }

    private void loadFXML() {
        FXMLLoaders.loadRoot(this);
    }
    
}
