#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ${package}.sample.impl;

import ${package}.sample.Sample;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.drombler.acp.core.docking.ViewDocking;
import org.drombler.acp.core.docking.WindowMenuEntry;
import org.drombler.acp.core.docking.spi.Dockables;
import org.drombler.commons.fx.docking.DockablePane;
import org.drombler.commons.fx.fxml.FXMLLoaders;


@ViewDocking(areaId = "left", position = 10, displayName = "%LeftTestPane.displayName", icon = "left-test-pane.png",
accelerator = "Shortcut+3",
menuEntry =
@WindowMenuEntry(path = "", position = 30))
public class LeftTestPane extends DockablePane {

    private int sampleCounter = 0;

    public LeftTestPane() throws IOException {
        load();
    }

    private void load() throws IOException {
        FXMLLoaders.loadRoot(this);
    }

    @FXML
    private void onNewSampleAction(ActionEvent event) throws IOException {
        sampleCounter++;
        Sample sample = new Sample("Sample " + sampleCounter);
        SampleEditorPane sampleEditorPane = new SampleEditorPane(sample);
        Dockables.open(sampleEditorPane);
    }
}
