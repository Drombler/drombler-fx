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

import org.apache.commons.lang3.StringUtils;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.drombler.acp.core.docking.spi.DockableDataFactory;
import org.drombler.acp.core.docking.spi.ViewDockingDescriptor;
import org.drombler.commons.docking.fx.FXDockableData;
import org.drombler.commons.fx.scene.image.IconFactory;
import org.softsmithy.lib.util.ResourceLoader;

/**
 *
 * @author puce
 */
@Component
@Service
public class FXDockableDataFactory implements DockableDataFactory<FXDockableData> {

    private static final int ICON_SIZE = 16;

    @Override
    public FXDockableData createDockableData(ViewDockingDescriptor<?> dockingDescriptor) {
        FXDockableData dockableData = createCommonDockableData(dockingDescriptor.getIcon(), dockingDescriptor.
                getResourceLoader());
        dockableData.setTitle(dockingDescriptor.getDisplayName());
        return dockableData;
    }

    private FXDockableData createCommonDockableData(String icon, ResourceLoader resourceLoader) {
        FXDockableData dockableData = new FXDockableData();
        if (!StringUtils.isBlank(icon)) {
            // TODO: reuse IconFactory of FXActionUtils if possible
            IconFactory iconFactory = new IconFactory(icon, resourceLoader, false);
            dockableData.setGraphicFactory(iconFactory);
            dockableData.setGraphic(iconFactory.createGraphic(ICON_SIZE));
        }
        return dockableData;
    }

    @Override
    public FXDockableData createDockableData(String icon, ResourceLoader resourceLoader) {
        return createCommonDockableData(icon, resourceLoader);
    }

}
