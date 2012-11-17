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
package org.drombler.fx.core.action.impl;

import java.io.InputStream;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.drombler.fx.core.action.AbstractFXAction;

/**
 *
 * @author puce
 */
public class ActionEventHandlerAdapter extends AbstractFXAction {

    private final EventHandler<ActionEvent> actionEventHandler;
    private final ReadOnlyBooleanProperty disabled = new FixedBooleanProperty(this, "disabled", false);

    public ActionEventHandlerAdapter(EventHandler<ActionEvent> actionEventHandler) {
        this.actionEventHandler = actionEventHandler;
    }

    @Override
    public ReadOnlyBooleanProperty disabledProperty() {
        return disabled;
    }

    @Override
    public final boolean isDisabled() {
        return disabled.get();
    }

    @Override
    public void handle(ActionEvent e) {
        actionEventHandler.handle(e);
    }

    @Override
    protected InputStream getImageInputStream(String icon) {
        return actionEventHandler.getClass().getResourceAsStream(icon);
    }
}
