#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ${package}.sample.impl;

import ${package}.sample.Sample;
import ${package}.sample.SampleHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import org.drombler.acp.core.commons.util.SimpleServiceTrackerCustomizer;
import org.drombler.acp.core.data.spi.DataHandlerRegistryProvider;
import org.drombler.acp.core.docking.ViewDocking;
import org.drombler.acp.core.docking.WindowMenuEntry;
import org.drombler.commons.data.Openable;
import org.drombler.commons.fx.concurrent.FXConsumer;
import org.drombler.commons.fx.fxml.FXMLLoaders;
import org.osgi.util.tracker.ServiceTracker;



@ViewDocking(areaId = "left", position = 10, displayName = "%displayName", icon = "left-test-pane.png", accelerator = "Shortcut+3",
        menuEntry = @WindowMenuEntry(path = "", position = 40))
public class LeftTestPane extends BorderPane implements AutoCloseable {
    private final ServiceTracker<DataHandlerRegistryProvider, DataHandlerRegistryProvider> dataHandlerRegistryServiceTracker;
    private DataHandlerRegistryProvider dataHandlerRegistryProvider;

    @FXML
    private Button newSampleButton;

    public LeftTestPane() {
        load();
        this.dataHandlerRegistryServiceTracker = SimpleServiceTrackerCustomizer.createServiceTracker(DataHandlerRegistryProvider.class, new FXConsumer<>(this::setDataHandlerRegistryProvider));
        this.dataHandlerRegistryServiceTracker.open(true);
        newSampleButton.setDisable(!isInitialized());
    }

    private void load() {
        FXMLLoaders.loadRoot(this);
    }

    private boolean isInitialized() {
        return dataHandlerRegistryProvider != null;
    }

    @FXML
    private void onNewSampleAction(ActionEvent event) {
        Sample sample = new Sample();
        SampleHandler sampleHandler = new SampleHandler(sample);
        dataHandlerRegistryProvider.getDataHandlerRegistry().registerDataHandler(sampleHandler);
        Openable openable = sampleHandler.getLocalContext().find(Openable.class);
        if (openable != null) {
            openable.open();
        }
    }

    @Override
    public void close() throws Exception {
        dataHandlerRegistryServiceTracker.close();
    }

    /**
     * @return the dataHandlerRegistryProvider
     */
    public DataHandlerRegistryProvider getDataHandlerRegistryProvider() {
        return dataHandlerRegistryProvider;
    }

    /**
     * @param dataHandlerRegistryProvider the dataHandlerRegistryProvider to set
     */
    public void setDataHandlerRegistryProvider(DataHandlerRegistryProvider dataHandlerRegistryProvider) {
        this.dataHandlerRegistryProvider = dataHandlerRegistryProvider;
        newSampleButton.setDisable(!isInitialized());
    }
}
