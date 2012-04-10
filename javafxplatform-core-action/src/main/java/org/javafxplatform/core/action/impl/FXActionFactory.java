/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.action.impl;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCombination;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.javafxplatform.core.action.FXAction;
import org.richclientplatform.core.action.ActionListener;
import org.richclientplatform.core.action.spi.ActionDescriptor;
import org.richclientplatform.core.action.spi.ActionFactory;


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
        System.out.println(listener);
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
            fxAction.setDisplayName(actionDescriptor.getDisplayName());
            if (actionDescriptor.getAccelerator() != null && !actionDescriptor.getAccelerator().equals("")) {
                fxAction.setAccelerator(KeyCombination.keyCombination(actionDescriptor.getAccelerator()));
            }
            if (actionDescriptor.getIcon() != null && !actionDescriptor.getIcon().equals("")) {
                fxAction.setIcon(actionDescriptor.getIcon());
            }
        }
        System.out.println(fxAction);
        return fxAction;
    }

    @Override
    public Class<FXAction> getActionClass() {
        return FXAction.class;
    }
}
