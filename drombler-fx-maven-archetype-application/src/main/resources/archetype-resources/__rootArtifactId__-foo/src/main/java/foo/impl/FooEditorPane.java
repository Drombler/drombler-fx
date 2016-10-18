#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )


package ${package}.foo.impl;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import org.drombler.acp.core.docking.EditorDocking;
import org.drombler.commons.action.command.Savable;
import org.drombler.commons.context.Context;
import org.drombler.commons.context.LocalContextProvider;
import org.drombler.commons.context.SimpleContext;
import org.drombler.commons.context.SimpleContextContent;
import org.drombler.commons.docking.DockableDataSensitive;
import org.drombler.commons.docking.fx.FXDockableData;
import org.drombler.commons.fx.fxml.FXMLLoaders;
import org.drombler.fx.core.docking.FXDockableDataUtils;

/**
 *
 * @author puce
 */
@EditorDocking(contentType = FooHandler.class)
public class FooEditorPane extends BorderPane implements DockableDataSensitive<FXDockableData>, LocalContextProvider {

    private final SimpleContextContent contextContent = new SimpleContextContent();
    private final Context localContext = new SimpleContext(contextContent);
    private final FooHandler fooHandler;
    private FXDockableData dockableData;
    private Savable fooSavable;

    @FXML
    private TextArea textArea;

    public FooEditorPane(FooHandler fooHandler) {
        this.fooHandler = fooHandler;
        FXMLLoaders.loadRoot(this);
        textArea.textProperty().bindBidirectional(fooHandler.textProperty());
        fooHandler.textProperty().addListener((observable, oldValue, newValue) -> contentModified());
    }

    @Override
    public Context getLocalContext() {
        return localContext;
    }

    @Override
    public void setDockableData(FXDockableData dockableData) {
        this.dockableData = dockableData;
        FXDockableDataUtils.configureDockableData(this.dockableData, fooHandler);
        this.fooSavable = FXDockableDataUtils.createDocumentSavable(fooHandler, dockableData, this::cleanupAfterSave);
        contentModified();
    }

    private void contentModified() {
        if (localContext.find(Savable.class) == null) {
            contextContent.add(fooSavable);
        }
    }

    private void cleanupAfterSave(Savable savable) {
        contextContent.remove(savable);
    }

}
