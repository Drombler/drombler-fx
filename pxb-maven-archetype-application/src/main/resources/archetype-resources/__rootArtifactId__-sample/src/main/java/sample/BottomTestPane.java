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
@ViewDocking(areaId = "bottom", position = 10, displayName = "Bottom",
menuEntry =
@WindowMenuEntry(path = "", position = 30))
public class BottomTestPane extends DockablePane{

    public BottomTestPane() {
        setContent(new Label("bottom"));
    }
    
}
