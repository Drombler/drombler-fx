package tutorial.extension.some.impl;

import org.drombler.acp.core.application.processing.AbstractApplicationAnnotationProcessor;
import org.softsmithy.lib.lang.model.type.ModelTypeUtils;
import tutorial.extension.some.SomeAnnotation;

import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.util.Set;

@SupportedAnnotationTypes({"tutorial.extension.SomeAnnotation"})
@SupportedSourceVersion(SourceVersion.RELEASE_11)
public class SomeAnnotationProcessor extends AbstractApplicationAnnotationProcessor {

    @Override
    protected boolean handleProcess(Set<? extends TypeElement> annotations,
            RoundEnvironment roundEnv) {
        roundEnv.getElementsAnnotatedWith(SomeAnnotation.class).forEach(element -> {
            SomeAnnotation someAnnotation = element.getAnnotation(SomeAnnotation.class);
            if (someAnnotation != null) {
                TypeMirror typeMirror
                        = ModelTypeUtils.getTypeMirror(someAnnotation::someType);

                //...
            }
        });
        return false;
    }

}
