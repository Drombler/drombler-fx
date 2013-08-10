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

import org.drombler.fx.core.commons.fx.scene.image.IconFactory;
import javafx.scene.input.KeyCombination;
import org.apache.commons.lang3.StringUtils;
import org.drombler.acp.core.action.spi.ActionDescriptor;
import org.drombler.fx.core.action.FXAction;

/**
 *
 * @author puce
 */
public class FXActionUtils {

    private FXActionUtils() {
    }

    public static void configureAction(FXAction fxAction, ActionDescriptor actionDescriptor) {
        fxAction.setDisplayName(actionDescriptor.getDisplayName());
        if (actionDescriptor.getAccelerator() != null && !actionDescriptor.getAccelerator().equals("")) {
            fxAction.setAccelerator(KeyCombination.keyCombination(actionDescriptor.getAccelerator()));
        }
        if (!StringUtils.isBlank(actionDescriptor.getIcon())) {
            IconFactory iconFactory = new IconFactory(actionDescriptor.getIcon(), actionDescriptor.getResourceLoader(),
                    false);
            fxAction.setGraphicFactory(iconFactory);
        }
    }
}
