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
package org.drombler.fx.core.docking.impl;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringUtils;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.drombler.acp.core.docking.spi.DockableFactory;
import org.drombler.acp.core.docking.spi.ViewDockingDescriptor;
import org.drombler.commons.fx.docking.DockablePane;
import org.drombler.commons.fx.scene.image.IconFactory;

/**
 *
 * @author puce
 */
@Component
@Service
public class FXDockableFactory implements DockableFactory<DockablePane> {

    private static final String MNEMONIC_CHAR = "_";
    private static final int ICON_SIZE = 16;

    @Override
    public DockablePane createDockable(ViewDockingDescriptor dockingDescriptor) {
        try {
            DockablePane dockablePane = (DockablePane) dockingDescriptor.getDockableClass().newInstance();
            dockablePane.setTitle(dockingDescriptor.getDisplayName().replace(MNEMONIC_CHAR, ""));
            if (!StringUtils.isBlank(dockingDescriptor.getIcon())) {
                IconFactory iconFactory = new IconFactory(dockingDescriptor.getIcon(), dockingDescriptor.
                        getResourceLoader(), false);
                dockablePane.setGraphic(iconFactory.createGraphic(ICON_SIZE));
            }
            return dockablePane;
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(FXDockableFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
