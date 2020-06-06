package tutorial.extension.other.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import org.drombler.acp.core.application.processing.AbstractApplicationAnnotationProcessor;
import org.softsmithy.lib.lang.model.type.ModelTypeUtils;
import tutorial.extension.other.SomeOtherAnnotation;

@SupportedAnnotationTypes({"tutorial.extension.SomeOtherAnnotation"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class SomeOtherAnnotationProcessor extends AbstractApplicationAnnotationProcessor {

    @Override
    protected boolean handleProcess(Set<? extends TypeElement> annotations,
            RoundEnvironment roundEnv) {
        roundEnv.getElementsAnnotatedWith(SomeOtherAnnotation.class).forEach(element -> {
            List<? extends AnnotationMirror> annotationMirrors
                    = element.getAnnotationMirrors();
            List<String> classNames
                    = ModelTypeUtils.getTypeMirrorsOfClassArrayAnnotationValue(
                            annotationMirrors,
                            SomeOtherAnnotation.class,
                            "barClasses").stream()
                            .map(TypeMirror::toString)
                    .collect(Collectors.toList());

            //...
        });
        return false;
    }

}
