#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ${package}.sample;

import javafx.scene.control.Label;
import org.javafxplatform.core.docking.DockablePane;
import org.richclientplatform.core.docking.ViewDocking;
import org.richclientplatform.core.docking.WindowMenuEntry;
/**
 *
 * @author puce
 */
@ViewDocking(areaId = "top", position = 10, displayName = "Top",
menuEntry =
@WindowMenuEntry(path = "", position = 20))
public class TopTestPane extends DockablePane{

    public TopTestPane() {
        setContent(new Label("top"));
    }
    
}
