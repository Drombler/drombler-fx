package tutorial.extension;

import org.osgi.framework.BundleContext;
import tutorial.extension.jaxb.FooType;
import tutorial.extension.jaxb.FoosType;

/**
 *
 * @author puce
 */
public class ProgrammaticRegistrar {

    private void registerFoos(final BundleContext bundleContext) {
        FoosType foos = createFoos();

        bundleContext.registerService(FoosType.class, foos, null);
    }

    private FoosType createFoos() {
        FoosType foos = new FoosType();
        foos.getFoo().add(createFooSomeComponent1());
        return foos;
    }

    private FooType createFooSomeComponent1() {
        FooType foo = new FooType();
        foo.setBar("a");
        foo.setPosition(10);
        foo.setFooClass(SomeComponent1.class.getName());
        return foo;
    }

    private void registerFooDescriptor(final BundleContext bundleContext) {
        FooDescriptor<SomeComponent1> fooDescriptor
                = new FooDescriptor<>(SomeComponent1.class, "a", 10);
        bundleContext.registerService(FooDescriptor.class, fooDescriptor, null);
    }

}
