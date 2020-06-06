package tutorial.extension.foo.jaxb;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "foo"
})
@XmlRootElement(name = "foos", namespace = "http://www.mycompany.com/schema/myapp/foos")
public class FoosType {

    private List<FooType> foo;

    public List<FooType> getFoo() {
        if (foo == null) {
            foo = new ArrayList<>();
        }
        return this.foo;
    }

}
