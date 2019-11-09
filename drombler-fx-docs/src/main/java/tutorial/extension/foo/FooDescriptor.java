package tutorial.extension.foo;

import tutorial.extension.foo.jaxb.FooType;
import org.drombler.acp.core.commons.util.BundleUtils;
import org.osgi.framework.Bundle;

public class FooDescriptor<T> {

    private final Class<T> fooClass;
    private final String bar;
    private final int position;

    public FooDescriptor(Class<T> fooClass, String bar, int position) {
        this.fooClass = fooClass;
        this.bar = bar;
        this.position = position;
    }

    public Class<T> getFooClass() {
        return fooClass;
    }

    public String getBar() {
        return bar;
    }

    public int getPosition() {
        return position;
    }

    /**
     * Creates an instance of a {@link FooDescriptor} from a {@link FooType} unmarshalled from the application.xml.
     *
     * @param foo the unmarshalled status bar element
     * @param bundle the bundle of the application.xml
     * @return a FooDescriptor
     * @throws java.lang.ClassNotFoundException
     */
    public static FooDescriptor<?> createFooDescriptor(FooType foo, Bundle bundle) throws ClassNotFoundException {
        Class<?> fooClass = BundleUtils.loadClass(bundle, foo.getFooClass());
        return createFooDescriptor(foo, fooClass);
    }

    private static <T> FooDescriptor<T> createFooDescriptor(FooType foo, Class<T> fooClass) {
        return new FooDescriptor<>(fooClass, foo.getBar(), foo.getPosition());
    }

}
