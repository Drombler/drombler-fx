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
@ViewDocking(areaId = "right", position = 10, displayName = "Right",
menuEntry =
@WindowMenuEntry(path = "Other", position = 40))
public class RightTestPane extends DockablePane{

    public RightTestPane() {
        setContent(new Label("right"));
    }
    
}
