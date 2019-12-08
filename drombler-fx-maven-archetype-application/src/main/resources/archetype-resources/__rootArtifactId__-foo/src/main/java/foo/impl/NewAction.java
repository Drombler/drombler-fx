#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )


package ${package}.foo.impl;

import javafx.event.ActionEvent;
import org.drombler.acp.core.action.Action;
import org.drombler.acp.core.action.MenuEntry;
import org.drombler.acp.core.commons.util.SimpleServiceTrackerCustomizer;
import org.drombler.acp.core.data.spi.DataHandlerRegistryProvider;
import org.drombler.commons.action.fx.AbstractFXAction;
import org.drombler.commons.data.Openable;
import org.drombler.commons.fx.concurrent.FXConsumer;
import org.osgi.util.tracker.ServiceTracker;


@Action(id = "new", category = "core", displayName = "%displayName", accelerator = "Shortcut+N")
@MenuEntry(path = "File", position = 10)
public class NewAction extends AbstractFXAction implements AutoCloseable {

    private final ServiceTracker<DataHandlerRegistryProvider, DataHandlerRegistryProvider> dataHandlerRegistryServiceTracker;
    private DataHandlerRegistryProvider dataHandlerRegistryProvider;


    public NewAction() {
        this.dataHandlerRegistryServiceTracker = SimpleServiceTrackerCustomizer.createServiceTracker(DataHandlerRegistryProvider.class, new FXConsumer<>(this::setDataHandlerRegistryProvider));
        this.dataHandlerRegistryServiceTracker.open(true);
        setEnabled(isInitialized());
    }

    private boolean isInitialized() {
        return dataHandlerRegistryProvider != null;
    }

    @Override
    public void handle(ActionEvent event) {
        FooHandler fooHandler = new FooHandler();
        fooHandler = dataHandlerRegistryProvider.getDataHandlerRegistry().registerDataHandler(fooHandler);
        Openable openable = fooHandler.getLocalContext().find(Openable.class);
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
        setEnabled(isInitialized());
    }

}
