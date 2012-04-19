/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.action.impl;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.javafxplatform.core.action.FXCheckAction;
import org.richclientplatform.core.action.CheckActionListener;
import org.richclientplatform.core.action.spi.ActionDescriptor;
import org.richclientplatform.core.action.spi.CheckActionFactory;

/**
 *
 * @author puce
 */
@Component
@Service
public class FXCheckActionFactory implements CheckActionFactory<FXCheckAction> {

    @Override
    @SuppressWarnings("unchecked")
    public FXCheckAction createCheckAction(ActionDescriptor actionDescriptor) {
        Object listener = actionDescriptor.getListener();
        FXCheckAction fxCheckAction = null;
        if (listener instanceof FXCheckAction) {
            fxCheckAction = (FXCheckAction) listener;
        } else {
            if (listener instanceof CheckActionListener) {
                fxCheckAction = new CheckActionListenerAdapter((CheckActionListener) listener);
            }
        }
        if (fxCheckAction != null) {
            ActionUtils.configureAction(fxCheckAction, actionDescriptor);
        }
        return fxCheckAction;
    }

    @Override
    public Class<FXCheckAction> getCheckActionClass() {
        return FXCheckAction.class;
    }
}
