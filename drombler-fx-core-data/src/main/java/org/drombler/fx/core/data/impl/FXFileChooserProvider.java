package org.drombler.fx.core.data.impl;

import java.io.File;
import java.nio.file.Path;
import java.text.MessageFormat;
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
import org.drombler.acp.core.data.FileChooserProvider;
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
    private final MessageFormat extensionFilterFormat;

    public FXFileChooserProvider() {
        this.resourceBundle = ResourceBundleUtils.getClassResourceBundle(FXFileChooserProvider.class);
        this.extensionFilterFormat = new MessageFormat(resourceBundle.getString("fileChooser.extensionFilter.format"));

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
                extensionFilterFormat.format(
                        new Object[]{fileExtensionDescriptor.getDisplayName(),
                            fileExtensionDescriptor.getFileExtensions().toString()}),
                fileExtensionDescriptor.getFileExtensions().stream()
                .map(fileExtension -> WILDCARD + fileExtension.toLowerCase())
                .toArray(String[]::new)
        );
        fileChooser.getExtensionFilters().add(extensionFilter);
//        fileChooser.setSelectedExtensionFilter(fxmlExtensionFilter);
    }

    private void removeFileExtensionFilter(FileExtensionDescriptor fileExtensionDescriptor) {
// TODO
    }

    @Override
    public List<Path> showOpenMultipleDialog() {
        fileChooser.setTitle(resourceBundle.getString("fileChooser.open.title"));
        List<File> filesToOpen = fileChooser.showOpenMultipleDialog(mainWindowProvider.getMainWindow());
        if (filesToOpen != null && !filesToOpen.isEmpty()) {
            List<Path> filePathsToOpen = filesToOpen.stream().map(File::toPath).collect(Collectors.toList());
            setInitialDirectory(filePathsToOpen);
            return filePathsToOpen;
        } else {
            return null;
        }
    }

    @Override
    public Path showOpenDialog() {
        fileChooser.setTitle(resourceBundle.getString("fileChooser.open.title"));
        File file = fileChooser.showOpenDialog(mainWindowProvider.getMainWindow());
        if (file != null) {
            Path filePath = file.toPath();
            setInitialDirectory(filePath);
            return filePath;
        } else {
            return null;
        }
    }

    @Override
    public Path showSaveAsDialog(String initialFileName) {
        fileChooser.setTitle(resourceBundle.getString("fileChooser.saveAs.title"));
        fileChooser.setInitialFileName(initialFileName);
        File file = fileChooser.showSaveDialog(mainWindowProvider.getMainWindow());
        if (file != null) {
            Path filePath = file.toPath();
            setInitialDirectory(filePath);
            return filePath;
        } else {
            return null;
        }
    }

    private void setInitialDirectory(List<Path> filesToOpen) {
        final Path fileToOpen = filesToOpen.get(0);
        setInitialDirectory(fileToOpen);
    }

    private void setInitialDirectory(Path path) {
        Path parent = path.getParent();
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
