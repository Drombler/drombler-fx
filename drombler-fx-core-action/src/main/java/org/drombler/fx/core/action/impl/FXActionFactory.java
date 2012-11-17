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

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.drombler.acp.core.action.ActionListener;
import org.drombler.acp.core.action.spi.ActionDescriptor;
import org.drombler.acp.core.action.spi.ActionFactory;
import org.drombler.fx.core.action.FXAction;

/**
 *
 * @author puce
 */
@Component
@Service
public class FXActionFactory implements ActionFactory<FXAction> {

    @Override
    @SuppressWarnings("unchecked")
    public FXAction createAction(ActionDescriptor actionDescriptor) {
        Object listener = actionDescriptor.getListener();
        FXAction fxAction = null;
        if (listener instanceof FXAction) {
            fxAction = (FXAction) listener;
        } else {
            if (listener instanceof EventHandler) {
                fxAction = new ActionEventHandlerAdapter((EventHandler<ActionEvent>) listener);
            } else if (listener instanceof ActionListener) {
                fxAction = new ActionListenerAdapter((ActionListener) listener);
            }
        }
        if (fxAction != null) {
            ActionUtils.configureAction(fxAction, actionDescriptor);
        }
        return fxAction;
    }

    @Override
    public Class<FXAction> getActionClass() {
        return FXAction.class;
    }
}
