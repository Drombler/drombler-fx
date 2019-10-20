package tutorial.extension;

import tutorial.extension.jaxb.FoosType;
import org.drombler.acp.core.application.ExtensionPoint;
import org.osgi.service.component.annotations.Component;

@Component
public class FooExtensionPoint implements ExtensionPoint<FoosType> {

    @Override
    public Class<FoosType> getJAXBRootClass() {
        return FoosType.class;
    }

}
