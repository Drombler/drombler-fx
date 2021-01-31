/*
 *         COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Notice
 *
 * The contents of this file are subject to the COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL)
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.opensource.org/licenses/cddl1.txt
 *
 * The Original Code is provided by Drombler GmbH. The Initial Developer of the
 * Original Code is Florian Brunner (GitHub user: puce77).
 * Copyright 2021 Drombler GmbH. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.fx.startup.main;

import javafx.application.Application;

/**
 * This launcher allows a JavaFX 11+ based Drombler FX application to be launched from classpath
 * rather than the module-path.
 *
 * @author puce
 */
public class DromblerFXClasspathLauncher {

    /**
     * Runs the Drombler FX application.
     *
     * @param args the command line args
     */
    public static void main(String... args) {
        Application.launch(DromblerFXApplication.class, args);
    }
}
