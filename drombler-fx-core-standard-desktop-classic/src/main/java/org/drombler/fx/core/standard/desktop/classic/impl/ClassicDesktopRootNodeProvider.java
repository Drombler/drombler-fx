package org.drombler.fx.core.standard.desktop.classic.impl;

import java.io.IOException;
import java.util.concurrent.Executor;
import javafx.scene.Parent;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.drombler.acp.core.action.spi.ApplicationToolBarContainerProvider;
import org.drombler.acp.core.action.spi.MenuBarMenuContainerProvider;
import org.drombler.acp.startup.main.ApplicationExecutorProvider;
import org.drombler.fx.startup.main.MainSceneRootProvider;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author puce
 */
@Component
@Service
public class ClassicDesktopRootNodeProvider implements MainSceneRootProvider {

    private static final Logger LOG = LoggerFactory.getLogger(ClassicDesktopRootNodeProvider.class);

    @Reference
    private ApplicationExecutorProvider applicationExecutorProvider;

    private ClassicDesktopApplicationPane root;

    protected void bindApplicationExecutorProvider(ApplicationExecutorProvider applicationExecutorProvider) {
        this.applicationExecutorProvider = applicationExecutorProvider;
    }

    protected void unbindApplicationExecutorProvider(ApplicationExecutorProvider applicationExecutorProvider) {
        this.applicationExecutorProvider = null;
    }

    @Activate
    protected void activate(ComponentContext context) {
        getApplicationExecutor().execute(() -> {
            try {
                root = new ClassicDesktopApplicationPane();
                context.getBundleContext().registerService(
                        new String[]{
                            MenuBarMenuContainerProvider.class.getName(),
                            ContentPaneProvider.class.getName(),
                            ApplicationToolBarContainerProvider.class.getName()
                        }, root, null);
            } catch (IOException ex) {
                LOG.error(ex.getMessage(), ex);
            }
        });
    }

    @Deactivate
    protected void deactivate(ComponentContext context) {
        unregisterService(context, MenuBarMenuContainerProvider.class);
        unregisterService(context, ContentPaneProvider.class);
        unregisterService(context, ApplicationToolBarContainerProvider.class);
        root = null;
    }

    private void unregisterService(ComponentContext context, Class<?> type) {
        ServiceReference<?> serviceReference = context.getBundleContext().getServiceReference(type);
        context.getBundleContext().ungetService(serviceReference);
    }

    @Override
    public Parent getRoot() {
        return root;
    }

    private Executor getApplicationExecutor() {
        return applicationExecutorProvider.getApplicationExecutor();
    }

}
