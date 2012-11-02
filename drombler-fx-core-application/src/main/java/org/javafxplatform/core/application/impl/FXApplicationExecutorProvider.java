/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.application.impl;

import java.util.concurrent.Executor;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.drombler.acp.core.application.ApplicationExecutorProvider;

/**
 *
 * @author puce
 */
@Component
@Service
public class FXApplicationExecutorProvider implements ApplicationExecutorProvider {

    private final Executor executor = new FXApplicationExecutor();

    @Override
    public Executor getApplicationExecutor() {
        return executor;
    }
}
