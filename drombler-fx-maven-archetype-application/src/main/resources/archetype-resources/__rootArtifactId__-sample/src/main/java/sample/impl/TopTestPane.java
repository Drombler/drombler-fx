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
import org.drombler.fx.core.docking.DockablePane;
import org.drombler.fx.core.commons.javafx.fxml.FXMLLoaders;
/**
 *
 * @author puce
 */
@ViewDocking(areaId = "top", position = 10, displayName = "#TopTestPane.displayName",
menuEntry =
@WindowMenuEntry(path = "", position = 20))
public class TopTestPane extends DockablePane{

    public TopTestPane() throws IOException {
        load();
    }

    private void load() throws IOException {
        FXMLLoaders.loadRoot(this);
    }
    
}
