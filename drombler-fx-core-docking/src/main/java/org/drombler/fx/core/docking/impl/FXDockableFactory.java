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

import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Unmanaged;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.drombler.acp.core.docking.spi.DockableFactory;
import org.drombler.acp.core.docking.spi.ViewDockingDescriptor;
import org.ops4j.pax.cdi.spi.CdiContainerFactory;
import org.osgi.framework.FrameworkUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author puce
 */
//@OsgiServiceProvider
//@ApplicationScoped
@Component
@Service
public class FXDockableFactory implements DockableFactory<Object> {

    private static final Logger LOG = LoggerFactory.getLogger(FXDockableFactory.class);

    @Reference
    private CdiContainerFactory cdiContainerFactory;

    protected void bindCdiContainerFactory(CdiContainerFactory cdiContainerFactory) {
        this.cdiContainerFactory = cdiContainerFactory;
    }

    protected void unbindCdiContainerFactory(CdiContainerFactory cdiContainerFactory) {
        this.cdiContainerFactory = null;
    }

//    @Inject
//    @OsgiService
//    private Logger LOG;
    @Override
    public Object createDockable(ViewDockingDescriptor dockingDescriptor) {
        BeanManager beanManager = cdiContainerFactory.getContainer(
                FrameworkUtil.getBundle(dockingDescriptor.getDockableClass())).getBeanManager();
        Unmanaged<?> unmanagedView = new Unmanaged<>(beanManager, dockingDescriptor.getDockableClass());
        Unmanaged.UnmanagedInstance<?> viewInstance = unmanagedView.newInstance();
        return viewInstance.produce().inject().postConstruct().get();
    }
}
