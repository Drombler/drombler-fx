package tutorial.extension.foo.impl;

import org.drombler.acp.core.commons.util.concurrent.ApplicationThreadExecutorProvider;
import org.drombler.acp.core.context.ContextManagerProvider;
import org.drombler.commons.context.ContextInjector;
import org.drombler.commons.context.Contexts;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.softsmithy.lib.util.PositionableAdapter;
import tutorial.extension.foo.FooDescriptor;
import tutorial.extension.foo.jaxb.FooType;
import tutorial.extension.foo.jaxb.FoosType;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@Component
public class FooHandler<T> {
    private static final Logger LOG = LoggerFactory.getLogger(FooHandler.class);

    private final List<FooDescriptor<? extends T>> unresolvedFooDescriptors
            = new ArrayList<>();

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
        //...
    }

    @Reference(cardinality = ReferenceCardinality.MULTIPLE,
            policy = ReferencePolicy.DYNAMIC)
    public void bindFooDescriptor(FooDescriptor<? extends T> fooDescriptor) {
        registerFoo(fooDescriptor);
    }

    public void unbindFooDescriptor(FooDescriptor<? extends T> fooDescriptor) {
        //...
    }

    @Activate
    protected void activate(ComponentContext context) {
        contextInjector = new ContextInjector(contextManagerProvider.getContextManager());
        resolveUnresolvedFooDescriptors();
    }

    @Deactivate
    protected void deactivate(ComponentContext context) {
        contextInjector = null;
    }

    private boolean isInitialized() {
        return applicationThreadExecutorProvider != null
                && contextManagerProvider != null
                && contextInjector != null;
    }

    private void registerFoos(FoosType foosType, BundleContext context) {
        foosType.getFoo().forEach(fooType
                -> registerFoo(fooType, context));
    }

    private void registerFoo(FooType fooType, BundleContext context) {
        try {
            FooDescriptor<?> fooDescriptor
                    = FooDescriptor.createFooDescriptor(fooType, context.getBundle());
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
        // this uses the GUI-toolkit agnostic applicationThreadExecutorProvider
        applicationThreadExecutorProvider.getApplicationThreadExecutor().execute(() -> {
            try {
                T foo = createFoo(fooDescriptor);
                PositionableAdapter<? extends T> positionableFoo
                        = new PositionableAdapter<>(foo, fooDescriptor.getPosition());
                // do something on the application thread
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException ex) {
                LOG.error(ex.getMessage(), ex);
            }
        });
    }

    private T createFoo(FooDescriptor<? extends T> fooDescriptor)
            throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        T foo = fooDescriptor.getFooClass().getDeclaredConstructor().newInstance();
        Contexts.configureObject(foo,
                contextManagerProvider.getContextManager(),
                contextInjector);
        return foo;
    }


    private void resolveUnresolvedFooDescriptors() {
        List<FooDescriptor<? extends T>> unresolvedFooDescriptorsCopy
                = new ArrayList<>(unresolvedFooDescriptors);
        unresolvedFooDescriptors.clear();
        unresolvedFooDescriptorsCopy.forEach(this::registerFoo);
    }
}
