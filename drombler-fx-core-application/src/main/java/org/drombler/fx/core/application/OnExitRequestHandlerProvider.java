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
package org.drombler.fx.core.application;

import org.drombler.commons.fx.stage.OnExitRequestHandler;

/**
 * A service interface for an on application exit request handler.
 *
 * @author puce
 */
public interface OnExitRequestHandlerProvider {

    /**
     * Handles application exit requests.
     *
     * @return true, if the application can exit, false if the request is vetoed.
     */
    OnExitRequestHandler getOnExitRequestHandler();
}
