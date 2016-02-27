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
import org.drombler.acp.core.docking.spi.AbstractDockableDockingDescriptor;
import org.drombler.acp.core.docking.spi.DockableDataFactory;
import org.drombler.acp.core.docking.spi.EditorDockingDescriptor;
import org.drombler.acp.core.docking.spi.ViewDockingDescriptor;
import org.drombler.commons.docking.fx.FXDockableData;
import org.drombler.commons.fx.scene.image.IconFactory;

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
        FXDockableData dockableData = createCommonDockableData(dockingDescriptor);
        dockableData.setTitle(dockingDescriptor.getDisplayName());
        return dockableData;
    }

    private FXDockableData createCommonDockableData(AbstractDockableDockingDescriptor<?> dockingDescriptor) {
        FXDockableData dockableData = new FXDockableData();
        if (!StringUtils.isBlank(dockingDescriptor.getIcon())) {
            // TODO: reuse IconFactory of FXActionUtils if possible
            IconFactory iconFactory = new IconFactory(dockingDescriptor.getIcon(), dockingDescriptor.getResourceLoader(),
                    false);
            dockableData.setGraphicFactory(iconFactory);
            dockableData.setGraphic(iconFactory.createGraphic(ICON_SIZE));
        }
        return dockableData;
    }

    @Override
    public FXDockableData createDockableData(EditorDockingDescriptor<?> dockingDescriptor) {
        return createCommonDockableData(dockingDescriptor);
    }

    @Override
    public FXDockableData copyDockableData(FXDockableData dockableData) {
        FXDockableData copyDockableData = new FXDockableData();
        copyDockableData.setGraphicFactory(dockableData.getGraphicFactory());
        if (copyDockableData.getGraphicFactory() != null) {
            // Don't copy the graphic since a Node can only have one parent. Rather create a new graphic via the GraphicFactory.
            copyDockableData.setGraphic(copyDockableData.getGraphicFactory().createGraphic(ICON_SIZE));
        }
        copyDockableData.setTitle(dockableData.getTitle());
        return copyDockableData;
    }

}
