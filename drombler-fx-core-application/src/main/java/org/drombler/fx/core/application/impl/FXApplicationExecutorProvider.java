/*
 *         COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Notice
 *
 * The contents of this file are subject to the COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL)
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.opensource.org/licenses/cddl1.txt
 *
 * The Original Code is Drombler.org. The Initial Developer of the
 * Original Code is Florian Brunner (Sourceforge.net user: puce).
 * Copyright 2012 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.fx.core.application.impl;

import java.util.concurrent.Executor;
import org.drombler.acp.core.commons.util.concurrent.ApplicationExecutorProvider;
import org.osgi.service.component.annotations.Component;

/**
 *
 * @author puce
 */
@Component
public class FXApplicationExecutorProvider implements ApplicationExecutorProvider {

    private final Executor executor = new FXApplicationExecutor();

    @Override
    public Executor getApplicationExecutor() {
        return executor;
    }
}
