/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drombler.fx.core.commons.fx.application;

import javafx.application.Platform;

/**
 * Platform Utilities.
 * @author puce
 */
public class PlatformUtils {

    private PlatformUtils() {
    }

    /**
     * Runs a {@link Runnable} on the FX Application Thread.
     * <ul>
     *    <li>If the current thread is the FX Application Thread: just execute the {@link Runnable}
     *    <li>Else: Use {@link Platform#runLater(java.lang.Runnable)} to execute the {@link Runnable}
     * </ul>
     * @param runnable a {@link Runnable}
     */
    public static void runOnFxApplicationThread(Runnable runnable) {
        if (Platform.isFxApplicationThread()) {
            runnable.run();
        } else {
            Platform.runLater(runnable);
        }
    }
}
