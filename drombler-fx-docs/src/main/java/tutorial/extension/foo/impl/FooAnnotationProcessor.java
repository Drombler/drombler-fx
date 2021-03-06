package tutorial.extension.foo.impl;

import org.drombler.acp.core.application.processing.AbstractApplicationAnnotationProcessor;
import tutorial.extension.foo.Foo;
import tutorial.extension.foo.jaxb.FooType;
import tutorial.extension.foo.jaxb.FoosType;

import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.Set;

@SupportedAnnotationTypes({"tutorial.extension.Foo"})
@SupportedSourceVersion(SourceVersion.RELEASE_11)
public class FooAnnotationProcessor extends AbstractApplicationAnnotationProcessor {

    private FoosType foos;

    @Override
    protected boolean handleProcess(Set<? extends TypeElement> annotations,
            RoundEnvironment roundEnv) {
        roundEnv.getElementsAnnotatedWith(Foo.class).forEach(element -> {
            Foo fooAnnotation = element.getAnnotation(Foo.class);
            if (fooAnnotation != null) {
                registerFoo(fooAnnotation, element);
            }
        });
        return false;
    }

    private void registerFoo(Foo fooAnnotation, Element element) {
        init(element);

        FooType foo = new FooType();
        foo.setBar(fooAnnotation.bar());
        foo.setPosition(fooAnnotation.position());
        foo.setFooClass(element.asType().toString());
        foos.getFoo().add(foo);
    }

    private void init(Element element) {
        if (foos == null) {
            foos = new FoosType();
            addExtensionConfiguration(foos);
            addJAXBRootClass(FoosType.class);
        }
        addOriginatingElements(element);
    }

}
