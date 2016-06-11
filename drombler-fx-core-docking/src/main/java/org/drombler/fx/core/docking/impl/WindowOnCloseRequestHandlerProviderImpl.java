package org.drombler.fx.core.docking.impl;

import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.WindowEvent;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.drombler.acp.core.docking.spi.DockingAreaContainerProvider;
import org.drombler.commons.action.command.Savable;
import org.drombler.commons.client.util.ResourceBundleUtils;
import org.drombler.commons.context.Contexts;
import org.drombler.commons.docking.fx.FXDockableEntry;
import org.drombler.fx.core.application.WindowOnCloseRequestHandlerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author puce
 */
@Component
@Service
public class WindowOnCloseRequestHandlerProviderImpl implements WindowOnCloseRequestHandlerProvider {

    private static final Logger LOG = LoggerFactory.getLogger(WindowOnCloseRequestHandlerProviderImpl.class);

    private final ResourceBundle resourceBundle = ResourceBundleUtils.getClassResourceBundle(WindowOnCloseRequestHandlerProviderImpl.class);

    @Reference
    private DockingAreaContainerProvider<Node, FXDockableEntry> dockingAreaContainerProvider;

    protected void bindDockingAreaContainerProvider(DockingAreaContainerProvider dockingAreaContainerProvider) {
        this.dockingAreaContainerProvider = dockingAreaContainerProvider;
    }

    protected void unbindDockingAreaContainerProvider(DockingAreaContainerProvider dockingAreaContainerProvider) {
        this.dockingAreaContainerProvider = null;
    }

    @Override
    public EventHandler<WindowEvent> getWindowOnCloseRequestHandler() {
        return (WindowEvent event) -> {
            Set<FXDockableEntry> dockables = dockingAreaContainerProvider.getDockingAreaContainer().getDockables();
            List<FXDockableEntry> modifiedDockables = filterModifiedDockables(dockables);

            if (!modifiedDockables.isEmpty()) {
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setTitle(resourceBundle.getString("dialog.title"));
                dialog.setResizable(true);
                SaveModifiedDockablesPane saveModifiedDockablesPane = new SaveModifiedDockablesPane(modifiedDockables);
                saveModifiedDockablesPane.emptyProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue) {
                        dialog.setResult(ButtonType.OK);
                    }
                });
                dialog.getDialogPane().setContent(saveModifiedDockablesPane);
                dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
                ((Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL)).setDefaultButton(true);
                dialog.showAndWait()
                        .filter(result -> result == ButtonType.CANCEL)
                        .ifPresent(result -> event.consume());
            }
        };
    }

    private List<FXDockableEntry> filterModifiedDockables(Set<FXDockableEntry> dockableEntries) {
        return dockableEntries.stream()
                .filter(dockableEntry -> Contexts.find(dockableEntry.getDockable(), Savable.class) != null)
                .sorted(Comparator.comparing(dockableEntry -> dockableEntry.getDockableData().getTitle()))
                .collect(Collectors.toList());
    }

}
