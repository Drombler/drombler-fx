/*
 *         COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Notice
 *
 * The contents of this file are subject to the COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL)
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.opensource.org/licenses/cddl1.txt
 *
 * The Original Code is Drombler.org. The Initial Developer of the
 * Original Code is Florian Brunner (GitHub user: puce77).
 * Copyright 2016 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.fx.core.data.impl;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.lang3.SystemUtils;
import org.drombler.acp.core.data.spi.FileExtensionDescriptorRegistryProvider;
import org.drombler.acp.startup.main.MainWindowProvider;
import org.drombler.commons.client.dialog.FileChooserProvider;
import org.drombler.commons.client.util.ResourceBundleUtils;
import org.drombler.commons.data.file.FileExtensionDescriptor;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.softsmithy.lib.util.SetChangeEvent;
import org.softsmithy.lib.util.SetChangeListener;

import java.io.File;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * @author puce
 */
@Component
public class FXFileChooserProvider implements FileChooserProvider {

    private static final String WILDCARD = "*";
    private static final String FILE_NAME_SEPARATOR = ".";

    @Reference
    private MainWindowProvider<Stage> mainWindowProvider;
    @Reference
    private FileExtensionDescriptorRegistryProvider fileExtensionDescriptorRegistryProvider;

    private final SetChangeListener<FileExtensionDescriptor> fileExtensionListener = new FileExtensionFilterListener();
    private final FileChooser fileChooser = new FileChooser();

    private final ResourceBundle resourceBundle;
    private final MessageFormat extensionFilterFormat;

    public FXFileChooserProvider() {
        this.resourceBundle = ResourceBundleUtils.getClassResourceBundle(FXFileChooserProvider.class);
        this.extensionFilterFormat = new MessageFormat(resourceBundle.getString("fileChooser.extensionFilter.format"));

        this.fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(resourceBundle.getString("fileChooser.extensionFilters.allFiles"), "*.*"));
        // TODO: synchronize initialDirectory with FXDirectoryChooserProvider?
        this.fileChooser.setInitialDirectory(SystemUtils.getUserHome());
    }

    @Activate
    protected void activate(ComponentContext context) {
        fileExtensionDescriptorRegistryProvider.getFileExtensionDescriptorRegistry().addFileExtensionListener(fileExtensionListener);
        fileExtensionDescriptorRegistryProvider.getFileExtensionDescriptorRegistry().getAllFileExtensionDescriptors().forEach(this::addFileExtensionFilter);
    }

    @Deactivate
    protected void deactivate(ComponentContext context) {
        fileExtensionDescriptorRegistryProvider.getFileExtensionDescriptorRegistry().removeFileExtensionListener(fileExtensionListener);
    }

    private void addFileExtensionFilter(FileExtensionDescriptor fileExtensionDescriptor) {
        List<String> fileExtensions = fileExtensionDescriptor.getFileExtensions().stream()
                .map(fileExtension -> WILDCARD + FILE_NAME_SEPARATOR + fileExtension.toLowerCase())
                .collect(Collectors.toList());

// TODO: sort
        final FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter(
                extensionFilterFormat.format(new Object[]{fileExtensionDescriptor.getDisplayName(), fileExtensions.toString()}),
                fileExtensions.toArray(new String[fileExtensions.size()]));
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

    private class FileExtensionFilterListener implements SetChangeListener<FileExtensionDescriptor> {

        @Override
        public void elementAdded(SetChangeEvent<FileExtensionDescriptor> event) {
            addFileExtensionFilter(event.getElement());
        }

        @Override
        public void elementRemoved(SetChangeEvent<FileExtensionDescriptor> event) {
            removeFileExtensionFilter(event.getElement());
        }

    }
}
