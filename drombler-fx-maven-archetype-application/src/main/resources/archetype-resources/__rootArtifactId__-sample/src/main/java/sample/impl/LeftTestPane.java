#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ${package}.sample.impl;

import ${package}.sample.Sample;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import org.drombler.acp.core.docking.ViewDocking;
import org.drombler.acp.core.docking.WindowMenuEntry;
import org.drombler.acp.core.docking.spi.Dockables;
import org.drombler.commons.fx.fxml.FXMLLoaders;


@ViewDocking(areaId = "left", position = 10, displayName = "%displayName", icon = "left-test-pane.png",
accelerator = "Shortcut+3",
menuEntry =
 @WindowMenuEntry(path = "", position = 40))
public class LeftTestPane extends BorderPane {

    private int sampleCounter = 0;

    public LeftTestPane() {
        load();
    }

    private void load() {
        FXMLLoaders.loadRoot(this);
    }

    @FXML
    private void onNewSampleAction(ActionEvent event) {
        sampleCounter++;
        Sample sample = new Sample("Sample " + sampleCounter);
        SampleEditorPane sampleEditorPane = new SampleEditorPane(sample);
        Dockables.inject(sampleEditorPane);
        Dockables.open(sampleEditorPane);
    }
}
