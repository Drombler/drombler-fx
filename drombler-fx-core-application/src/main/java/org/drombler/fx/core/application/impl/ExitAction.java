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

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.drombler.acp.core.action.Action;
import org.drombler.acp.core.action.MenuEntry;

/**
 *
 * @author puce
 */
@Action(id = "platform.exit", category = "core", displayName = "#exit.displayName", accelerator = "Shortcut+Q")
@MenuEntry(path = "File", position = 9900)
public class ExitAction implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent t) {
        Platform.exit();
    }
}
