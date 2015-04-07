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

import org.drombler.acp.core.docking.spi.DockableFactory;
import org.drombler.acp.core.docking.spi.ViewDockingDescriptor;
import org.ops4j.pax.cdi.api.OsgiServiceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author puce
 */
@OsgiServiceProvider
//@ApplicationScoped
public class FXDockableFactory implements DockableFactory {//<Object> {

    private static final Logger LOG = LoggerFactory.getLogger(FXDockableFactory.class);

//    @Inject
//    @OsgiService
//    private Logger LOG;
    @Override
    public Object createDockable(ViewDockingDescriptor dockingDescriptor) {
        try {
            return dockingDescriptor.getDockableClass().newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            LOG.error(ex.getMessage(), ex);
        }
        return null;
    }

//        @Override
//    public Object createDockable(ViewDockingDescriptor dockingDescriptor) {
//        Unmanaged<?> unmanagedView = new Unmanaged<>(dockingDescriptor.getDockableClass());
//        Unmanaged.UnmanagedInstance<?> viewInstance = unmanagedView.newInstance();
//        return viewInstance.produce().inject().postConstruct().get();
//    }
}
