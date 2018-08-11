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
 * Copyright 2018 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.fx.core.data.impl;

import java.io.File;
import java.nio.file.Path;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.apache.commons.lang3.SystemUtils;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.drombler.acp.startup.main.MainWindowProvider;
import org.drombler.commons.client.dialog.DirectoryChooserProvider;

/**
 *
 * @author puce
 */
@Component
@Service
public class FXDirectoryChooserProvider implements DirectoryChooserProvider {

    @Reference
    private MainWindowProvider<Stage> mainWindowProvider;

    private final DirectoryChooser directoryChooser = new DirectoryChooser();


    public FXDirectoryChooserProvider() {
        // TODO: synchronize initialDirectory with FXFileChooserProvider?
        this.directoryChooser.setInitialDirectory(SystemUtils.getUserHome());
    }

    @Override
    public Path showDialog() {
        File directory = directoryChooser.showDialog(mainWindowProvider.getMainWindow());
        if (directory != null) {
            directoryChooser.setInitialDirectory(directory);
            return directory.toPath();
        } else {
            return null;
        }
    }

}
