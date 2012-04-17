/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.util.javafx.fxml.application;

import javafx.application.Platform;

/**
 *
 * @author puce
 */
public class PlatformUtils {

    private PlatformUtils() {
    }

    public static void runOnFxApplicationThread(Runnable runnable) {
        if (Platform.isFxApplicationThread()) {
            runnable.run();
        } else {
            Platform.runLater(runnable);
        }
    }
}
