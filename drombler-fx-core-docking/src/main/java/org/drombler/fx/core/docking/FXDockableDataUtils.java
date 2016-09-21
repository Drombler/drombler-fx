package org.drombler.fx.core.docking;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import javafx.scene.control.Alert;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.Tooltip;
import org.drombler.acp.core.data.AbstractDocumentHandler;
import org.drombler.commons.data.DataHandler;
import org.drombler.commons.action.command.Savable;
import org.drombler.commons.client.util.ResourceBundleUtils;
import org.drombler.commons.docking.fx.FXDockableData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for {@link FXDockableData}.
 *
 * @author puce
 */
public final class FXDockableDataUtils {

    private static final Logger LOG = LoggerFactory.getLogger(FXDockableDataUtils.class);
    private static final AtomicInteger COUNTER = new AtomicInteger(1);

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundleUtils.getClassResourceBundle(FXDockableDataUtils.class);

    private FXDockableDataUtils() {
    }

    /**
     * Configures the {@link FXDockableData} with a data handler.
     *
     * @param dockableData the dockable data
     * @param dataHandler the data handler
     */
    public static void configureDockableData(FXDockableData dockableData, DataHandler<?> dataHandler) {
        configureDockableData(dockableData, dataHandler, "Untitled");
    }

    /**
     * Configures the {@link FXDockableData} with a data handler.
     *
     * @param dockableData the dockable data
     * @param dataHandler the data handler
     * @param defaultTitlePrefix the default title prefix
     */
    public static void configureDockableData(FXDockableData dockableData, DataHandler<?> dataHandler, String defaultTitlePrefix) {
        if (dataHandler.getUniqueKey() != null) {
            configureDockableDataExisting(dockableData, dataHandler);
        } else {
            dockableData.setTitle(defaultTitlePrefix + " " + COUNTER.getAndIncrement());
        }
    }

    public static void configureDockableDataExisting(FXDockableData dockableData, DataHandler<?> dataHandler) {
        dockableData.setTitle(dataHandler.getTitle());
        Tooltip tooltip = dockableData.getTooltip();
        if (tooltip == null) {
            tooltip = new Tooltip(dataHandler.getTooltipText());
            tooltip.setTextOverrun(OverrunStyle.CENTER_WORD_ELLIPSIS);
            dockableData.setTooltip(tooltip);
        } else {
            tooltip.setText(dataHandler.getTooltipText());
        }
    }

    /**
     * Creates a {@link Savable} for a document.
     *
     * @param documentHandler the document handler
     * @param dockableData the dockable data
     * @param postSaveHandler the handler, which should be called after the document has been saved
     * @return a {@link Savable} for a document
     */
    public static Savable createDocumentSavable(AbstractDocumentHandler documentHandler, FXDockableData dockableData, Consumer<Savable> postSaveHandler) {
        return new Savable() {
            @Override
            public void save() {
                try {
                    if (documentHandler.getPath() == null) {
                        String initialFileName = dockableData.getTitle().toLowerCase().replace(' ', '-') + "." + documentHandler.getDefaultFileExtenion();
                        if (documentHandler.saveNew(initialFileName)) {
                            configureDockableDataExisting(dockableData, documentHandler);
                            postSaveHandler.accept(this);
                        }
                    } else {
                        documentHandler.save();
                        postSaveHandler.accept(this);
                    }
                } catch (IOException ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText(RESOURCE_BUNDLE.getString("errorAlert.contentText"));
                    alert.showAndWait();
                    LOG.error("Could not write the file: " + documentHandler.getPath(), ex);
                }
            }

            @Override
            public String getDisplayString(Locale inLocale) {
                return dockableData.getTitle();
            }
        };
    }
}
