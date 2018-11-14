package org.drombler.fx.core.standard.desktop.classic.impl;

import java.io.IOException;
import javafx.scene.Parent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import org.drombler.acp.core.action.spi.ApplicationToolBarContainerProvider;
import org.drombler.acp.core.action.spi.MenuBarMenuContainerProvider;
import org.drombler.acp.core.action.spi.MenuMenuItemContainerFactory;
import org.drombler.acp.core.action.spi.SeparatorMenuItemFactory;
import org.drombler.acp.core.status.spi.StatusBarElementContainerProvider;
import org.drombler.fx.core.application.MainSceneRootProvider;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author puce
 */
@Component
public class ClassicDesktopRootNodeProvider implements MainSceneRootProvider {

    private static final Logger LOG = LoggerFactory.getLogger(ClassicDesktopRootNodeProvider.class);

    @Reference
    private MenuMenuItemContainerFactory<MenuItem, Menu> menuMenuItemContainerFactory;

    @Reference
    private SeparatorMenuItemFactory<SeparatorMenuItem> separatorMenuItemFactory;

    private ClassicDesktopApplicationPane root;

    @Activate
    protected void activate(ComponentContext context) {
        try {
            root = new ClassicDesktopApplicationPane(menuMenuItemContainerFactory, separatorMenuItemFactory);
            context.getBundleContext().registerService(
                    new String[]{
                        MenuBarMenuContainerProvider.class.getName(),
                        ContentPaneProvider.class.getName(),
                        ApplicationToolBarContainerProvider.class.getName(),
                        StatusBarElementContainerProvider.class.getName()
                    }, root, null);
        } catch (IOException ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }

    @Deactivate
    protected void deactivate(ComponentContext context) {
        unregisterService(context, MenuBarMenuContainerProvider.class);
        unregisterService(context, ContentPaneProvider.class);
        unregisterService(context, ApplicationToolBarContainerProvider.class);
        unregisterService(context, StatusBarElementContainerProvider.class);
        root = null;
    }

    private void unregisterService(ComponentContext context, Class<?> type) {
        // TODO: this does not unregister the services, does it? Is explicit unregister needed?
        ServiceReference<?> serviceReference = context.getBundleContext().getServiceReference(type);
        context.getBundleContext().ungetService(serviceReference);
    }

    @Override
    public Parent getRoot() {
        return root;
    }

}
