package tutorial.extension.foo;

import org.osgi.framework.BundleContext;
import tutorial.extension.foo.impl.FooComponent1;
import tutorial.extension.foo.jaxb.FooType;
import tutorial.extension.foo.jaxb.FoosType;

public class ProgrammaticFooRegistrar {

    private void registerFoos(final BundleContext bundleContext) {
        FoosType foos = createFoos();

        bundleContext.registerService(FoosType.class, foos, null);
    }

    private FoosType createFoos() {
        FoosType foos = new FoosType();
        foos.getFoo().add(createFoo1());
        return foos;
    }

    private FooType createFoo1() {
        FooType foo = new FooType();
        foo.setBar("a");
        foo.setPosition(10);
        foo.setFooClass(FooComponent1.class.getName());
        return foo;
    }

    private void registerFooDescriptor(final BundleContext bundleContext) {
        FooDescriptor<FooComponent1> fooDescriptor
                = new FooDescriptor<>(FooComponent1.class, "a", 10);
        bundleContext.registerService(FooDescriptor.class, fooDescriptor, null);
    }

}
