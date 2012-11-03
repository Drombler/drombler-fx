/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drombler.fx.core.action.impl;

import javafx.application.Platform;
import javafx.scene.input.KeyCombination;
import org.drombler.acp.core.action.spi.ActionDescriptor;
import org.drombler.fx.core.action.FXAction;

/**
 *
 * @author puce
 */
public class ActionUtils {

    private ActionUtils() {
    }

    public static void configureAction(FXAction fxAction, ActionDescriptor actionDescriptor) {
        System.out.println(ActionUtils.class.getName()+": isFxApplicationThread: "+Platform.isFxApplicationThread());
        fxAction.setDisplayName(actionDescriptor.getDisplayName());
        if (actionDescriptor.getAccelerator() != null && !actionDescriptor.getAccelerator().equals("")) {
            fxAction.setAccelerator(KeyCombination.keyCombination(actionDescriptor.getAccelerator()));
        }
        if (actionDescriptor.getIcon() != null && !actionDescriptor.getIcon().equals("")) {
            fxAction.setIcon(actionDescriptor.getIcon());
        }
    }
}
