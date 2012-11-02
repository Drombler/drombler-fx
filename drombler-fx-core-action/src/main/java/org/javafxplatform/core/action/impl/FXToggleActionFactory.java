/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.action.impl;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.javafxplatform.core.action.FXToggleAction;
import org.richclientplatform.core.action.ToggleActionListener;
import org.richclientplatform.core.action.spi.ActionDescriptor;
import org.richclientplatform.core.action.spi.ToggleActionFactory;

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
            ActionUtils.configureAction(fxToggleAction, actionDescriptor);
        }
        return fxToggleAction;
    }

    @Override
    public Class<FXToggleAction> getToggleActionClass() {
        return FXToggleAction.class;
    }
}
