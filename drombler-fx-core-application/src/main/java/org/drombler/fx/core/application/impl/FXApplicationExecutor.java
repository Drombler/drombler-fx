/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drombler.fx.core.application.impl;

import java.util.concurrent.Executor;
import org.drombler.fx.core.commons.javafx.application.PlatformUtils;

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
