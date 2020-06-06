package org.drombler.fx.core.docking;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import javafx.scene.control.Alert;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.Tooltip;
import org.drombler.acp.core.data.AbstractDocumentHandler;
import org.drombler.acp.core.docking.spi.Dockables;
import org.drombler.commons.action.command.Savable;
import org.drombler.commons.client.dialog.FileChooserProvider;
import org.drombler.commons.client.util.ResourceBundleUtils;
import org.drombler.commons.data.DataHandler;
import org.drombler.commons.docking.fx.FXDockableData;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
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
     * Configures the {@link FXDockableData} with a data handler. If the data does not exist yet ({@code dataHandler.getUniqueKey() == null}), a default title will be set instead.
     *
     * @param dockableData the dockable data
     * @param dataHandler the data handler
     * @see #configureDockableData(org.drombler.commons.docking.fx.FXDockableData, org.drombler.commons.data.DataHandler, java.lang.String)
     */
    public static void configureDockableData(FXDockableData dockableData, DataHandler<?> dataHandler) {
        configureDockableData(dockableData, dataHandler, "Untitled");
    }

    /**
     * Configures the {@link FXDockableData} with a data handler. If the data does not exist yet ({@code dataHandler.getUniqueKey() == null}), a default title will be set instead.
     *
     * @param dockableData the dockable data
     * @param dataHandler the data handler
     * @param defaultTitlePrefix the default title prefix
     * @see #configureDockableDataFromExistingData(org.drombler.commons.docking.fx.FXDockableData, org.drombler.commons.data.DataHandler)
     */
    public static void configureDockableData(FXDockableData dockableData, DataHandler<?> dataHandler, String defaultTitlePrefix) {
        if (dataHandler.getUniqueKey() != null) {
            configureDockableDataFromExistingData(dockableData, dataHandler);
        } else {
            dockableData.setTitle(defaultTitlePrefix + " " + COUNTER.getAndIncrement());
        }
    }

    /**
     * Configures the {@link FXDockableData} with a data handler. The data is expected to exist ({@code dataHandler.getUniqueKey() != null})-
     *
     * @param dockableData the dockable data
     * @param dataHandler the data handler to access the existing data
     */
    // TODO: private?
    public static void configureDockableDataFromExistingData(FXDockableData dockableData, DataHandler<?> dataHandler) { // TODO: rename
        if (dataHandler.getUniqueKey() == null) {
            throw new IllegalArgumentException("The data does not exist yet!");
        }
        dockableData.setTitle(dataHandler.getTitle());
        dataHandler.addPropertyChangeListener(DataHandler.TITLE_PROPERTY_NAME, evt -> dockableData.setTitle(dataHandler.getTitle()));
        String tooltipText = dataHandler.getTooltipText();
        Tooltip tooltip = createTooltip(dockableData, tooltipText);
        dataHandler.addPropertyChangeListener(DataHandler.TOOLTIP_TEXT_PROPERTY_NAME, evt -> tooltip.setText(dataHandler.getTooltipText()));
    }

    private static Tooltip createTooltip(FXDockableData dockableData, final String tooltipText) {
        Tooltip tooltip = dockableData.getTooltip();
        if (tooltip == null) {
            tooltip = new Tooltip(tooltipText);
            tooltip.setTextOverrun(OverrunStyle.CENTER_WORD_ELLIPSIS);
            dockableData.setTooltip(tooltip);
        } else {
            tooltip.setText(tooltipText);
        }
        return tooltip;
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
                        FileChooserProvider fileChooserProvider = getFileChooserProvider();
                        String initialFileName = dockableData.getTitle().toLowerCase().replace(' ', '-') + "." + documentHandler.getDefaultFileExtenion();
                        Path documentPath = fileChooserProvider.showSaveAsDialog(initialFileName);
                        if (documentPath != null) {
                            documentHandler.saveNew(documentPath);
                            configureDockableDataFromExistingData(dockableData, documentHandler);
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

    private static FileChooserProvider getFileChooserProvider() {
        BundleContext bundleContext = FrameworkUtil.getBundle(Dockables.class).getBundleContext();
        ServiceReference<FileChooserProvider> fileChooserProviderServiceReference
                = bundleContext.getServiceReference(FileChooserProvider.class);
        return bundleContext.getService(fileChooserProviderServiceReference);
    }
}
