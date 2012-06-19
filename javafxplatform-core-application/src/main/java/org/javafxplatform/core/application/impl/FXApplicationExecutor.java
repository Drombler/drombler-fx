/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.application.impl;

import java.util.concurrent.Executor;
import org.javafxplatform.core.util.javafx.application.PlatformUtils;

/**
 *
 * @author puce
 */
public class FXApplicationExecutor implements Executor {

    @Override
    public void execute(Runnable command) {
//        command.run();
        PlatformUtils.runOnFxApplicationThread(command);
    }
}
