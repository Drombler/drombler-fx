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

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.drombler.acp.core.action.ToggleActionListener;
import org.drombler.acp.core.action.spi.ActionDescriptor;
import org.drombler.acp.core.action.spi.ToggleActionFactory;
import org.drombler.fx.core.action.FXToggleAction;

/**
 *
 * @author puce
 */
@Component
@Service
public class FXToggleActionFactory implements ToggleActionFactory<FXToggleAction> {

    @Override
    @SuppressWarnings("unchecked")
    public FXToggleAction createToggleAction(ActionDescriptor actionDescriptor) {
        Object listener = actionDescriptor.getListener();
        FXToggleAction fxToggleAction = null;
        if (listener instanceof FXToggleAction) {
            fxToggleAction = (FXToggleAction) listener;
        } else {
            if (listener instanceof ToggleActionListener) {
                fxToggleAction = new ToggleActionListenerAdapter((ToggleActionListener) listener);
            }
        }
        if (fxToggleAction != null) {
            FXActionUtils.configureAction(fxToggleAction, actionDescriptor);
        }
        return fxToggleAction;
    }

    @Override
    public Class<FXToggleAction> getToggleActionClass() {
        return FXToggleAction.class;
    }
}
