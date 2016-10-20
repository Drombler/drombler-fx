package tutorial.docking;

import org.softsmithy.lib.util.UniqueKeyProvider;

/**
 *
 * @author puce
 */


public class FooHandler implements UniqueKeyProvider<String> {

    private final Foo foo;

    public FooHandler(Foo foo) {
        this.foo = foo;
    }

    @Override
    public String getUniqueKey() {
        return foo.getId();
    }

}
