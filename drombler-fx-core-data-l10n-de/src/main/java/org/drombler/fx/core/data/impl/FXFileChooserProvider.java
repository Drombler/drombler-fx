package org.drombler.fx.core.data.impl;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.lang3.SystemUtils;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.drombler.acp.core.data.spi.FileChooserProvider;
import org.drombler.acp.core.data.spi.FileExtensionDescriptor;
import org.drombler.acp.core.data.spi.FileExtensionDescriptorRegistry;
import org.drombler.acp.core.data.spi.FileExtensionEvent;
import org.drombler.acp.core.data.spi.FileExtensionListener;
import org.drombler.acp.startup.main.MainWindowProvider;
import org.drombler.commons.client.util.ResourceBundleUtils;
import org.osgi.service.component.ComponentContext;

/**
 *
 * @author puce
 */
@Component
@Service
public class FXFileChooserProvider implements FileChooserProvider {

    private static final String WILDCARD = "*";

    @Reference
    private MainWindowProvider<Stage> mainWindowProvider;
    @Reference
    private FileExtensionDescriptorRegistry fileExtensionDescriptorRegistry;

    private final FileExtensionListener fileExtensionListener = new FileExtensionFilterListener();
    private final FileChooser fileChooser = new FileChooser();

    private final ResourceBundle resourceBundle;

    public FXFileChooserProvider() {
        this.resourceBundle = ResourceBundleUtils.getClassResourceBundle(FXFileChooserProvider.class);

        this.fileChooser.setTitle(resourceBundle.getString("fileChooser.title"));
        this.fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(resourceBundle.getString("fileChooser.extensionFilters.allFiles"), "*.*"));
        this.fileChooser.setInitialDirectory(SystemUtils.getUserHome());
    }

    @Activate
    protected void activate(ComponentContext context) {
        fileExtensionDescriptorRegistry.registerFileExtensionListener(fileExtensionListener);
        fileExtensionDescriptorRegistry.getAllFileExtensionDescriptors().forEach(this::addFileExtensionFilter);
    }

    @Deactivate
    protected void deactivate(ComponentContext context) {
        fileExtensionDescriptorRegistry.unregisterFileExtensionListener(fileExtensionListener);
    }

    private void addFileExtensionFilter(FileExtensionDescriptor fileExtensionDescriptor) {
// TODO: sort
        final FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter(
                fileExtensionDescriptor.getDisplayName() + " " + fileExtensionDescriptor.getFileExtensions().toString(),
                fileExtensionDescriptor.getFileExtensions().stream()
                .map(fileExtension -> WILDCARD + fileExtension.toLowerCase())
                .toArray(String[]::new));
        fileChooser.getExtensionFilters().add(extensionFilter);
//        fileChooser.setSelectedExtensionFilter(fxmlExtensionFilter);
    }

    private void removeFileExtensionFilter(FileExtensionDescriptor fileExtensionDescriptor) {

    }

    @Override
    public List<Path> getFilesToOpen() {

//        final FileChooser.ExtensionFilter fxmlExtensionFilter = new FileChooser.ExtensionFilter("FXML Document",
//                "*.fxml");
//        fileChooser.getExtensionFilters().add(fxmlExtensionFilter);
//        fileChooser.setSelectedExtensionFilter(fxmlExtensionFilter);
        List<File> filesToOpen = fileChooser.showOpenMultipleDialog(mainWindowProvider.getMainWindow());
        if (filesToOpen == null || filesToOpen.isEmpty()) {
            return null;
        } else {
            List<Path> filePathsToOpen = filesToOpen.stream().map(File::toPath).collect(Collectors.toList());
            setInitialDirectory(filePathsToOpen);
            return filePathsToOpen;
        }
    }

    private void setInitialDirectory(List<Path> filesToOpen) {
        Path parent = filesToOpen.get(0).getParent();
        if (parent != null) { // TODO: can it ever be null here?
            fileChooser.setInitialDirectory(parent.toFile());
        }
    }

    private class FileExtensionFilterListener implements FileExtensionListener {

        @Override
        public void fileExtensionAdded(FileExtensionEvent event) {
            addFileExtensionFilter(event.getFileExtensionDescriptor());
        }

        @Override
        public void fileExtensionRemoved(FileExtensionEvent event) {
            removeFileExtensionFilter(event.getFileExtensionDescriptor());
        }

    }
}
