package tutorial.extension.foo.impl;

import java.util.ArrayList;
import java.util.List;
import org.drombler.acp.core.commons.util.concurrent.ApplicationThreadExecutorProvider;
import org.drombler.acp.core.context.ContextManagerProvider;
import org.drombler.commons.context.ContextInjector;
import org.drombler.commons.context.Contexts;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.softsmithy.lib.util.PositionableAdapter;
import tutorial.extension.foo.FooDescriptor;
import tutorial.extension.foo.FooDescriptor;
import tutorial.extension.foo.jaxb.FooType;
import tutorial.extension.foo.jaxb.FoosType;

/**
 *
 * @author puce
 */
@Component
public class FooHandler<T> {
    private static final Logger LOG = LoggerFactory.getLogger(FooHandler.class);

    private final List<FooDescriptor<? extends T>> unresolvedFooDescriptors = new ArrayList<>();

    @Reference
    private ApplicationThreadExecutorProvider applicationThreadExecutorProvider;

    @Reference
    private ContextManagerProvider contextManagerProvider;

    private ContextInjector contextInjector;

    @Reference(cardinality = ReferenceCardinality.MULTIPLE,
            policy = ReferencePolicy.DYNAMIC)
    public void bindFoosType(ServiceReference<FoosType> serviceReference) {
        BundleContext context = serviceReference.getBundle().getBundleContext();
        FoosType foosType = context.getService(serviceReference);
        registerFoos(foosType, context);
    }

    public void unbindFoosType(FoosType foosType) {
        // TODO
    }

    @Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
    public void bindFooDescriptor(FooDescriptor<? extends T> fooDescriptor) {
        registerFoo(fooDescriptor);
    }

    public void unbindFooDescriptor(FooDescriptor<? extends T> fooDescriptor) {
        // TODO
    }

    @Activate
    protected void activate(ComponentContext context) {
        contextInjector = new ContextInjector(contextManagerProvider.getContextManager());
        resolveUnresolvedFooDescriptors();
    }

    @Deactivate
    protected void deactivate(ComponentContext context) {
    }

    private boolean isInitialized() {
        return applicationThreadExecutorProvider != null && contextManagerProvider != null && contextInjector != null;
    }

    private void registerFoos(FoosType foosType, BundleContext context) {
        foosType.getFoo().forEach(fooType
                -> registerFoo(fooType, context));
    }

    private void registerFoo(FooType fooType, BundleContext context) {
        try {
            FooDescriptor<?> fooDescriptor = FooDescriptor.createFooDescriptor(fooType, context.getBundle());
            context.registerService(FooDescriptor.class, fooDescriptor, null);
        } catch (ClassNotFoundException ex) {
            LOG.error(ex.getMessage(), ex);
        }

    }

    private void registerFoo(FooDescriptor<? extends T> fooDescriptor) {
        if (isInitialized()) {
            registerFooInitialized(fooDescriptor);
        } else {
            unresolvedFooDescriptors.add(fooDescriptor);
        }
    }

    private void registerFooInitialized(FooDescriptor<? extends T> fooDescriptor) {
        applicationThreadExecutorProvider.getApplicationThreadExecutor().execute(() -> {
            try {
                T foo = createFoo(fooDescriptor);
                PositionableAdapter<? extends T> positionableFoo = new PositionableAdapter<>(foo, fooDescriptor.getPosition());
// do something
            } catch (InstantiationException | IllegalAccessException ex) {
                LOG.error(ex.getMessage(), ex);
            }
        });
    }

    private T createFoo(FooDescriptor<? extends T> fooDescriptor) throws InstantiationException, IllegalAccessException {
        T foo = fooDescriptor.getFooClass().newInstance();
        Contexts.configureObject(foo, contextManagerProvider.getContextManager(), contextInjector);
        return foo;
    }


    private void resolveUnresolvedFooDescriptors() {
        List<FooDescriptor<? extends T>> unresolvedFooDescriptorsCopy = new ArrayList<>(unresolvedFooDescriptors);
        unresolvedFooDescriptors.clear();
        unresolvedFooDescriptorsCopy.forEach(this::registerFoo);
    }
}
