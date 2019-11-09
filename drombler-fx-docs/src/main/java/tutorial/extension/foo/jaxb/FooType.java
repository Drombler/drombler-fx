package tutorial.extension.foo.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FooType", propOrder = {
    "bar",
    "position",
    "fooClass"
})
public class FooType {

    @XmlElement(required = true)
    protected String bar;
    protected int position;
    @XmlElement(required = true)
    protected String fooClass;

    public String getBar() {
        return bar;
    }

    public void setBar(String value) {
        this.bar = value;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int value) {
        this.position = value;
    }

    public String getFooClass() {
        return fooClass;
    }

    public void setFooClass(String value) {
        this.fooClass = value;
    }

}
