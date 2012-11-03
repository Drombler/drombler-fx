/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drombler.fx.core.action.impl;

import java.util.Set;
import javafx.event.EventHandler;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import org.drombler.acp.core.action.Action;

/**
 * Checks if the listener class implements {@link EventHandler<ActionEvent>}.<br/>
 * <br/>
 * Note: Not used/ implemented yet. Useful?
 * @author puce
 */
@SupportedAnnotationTypes("org.drombler.acp.core.action.Action")
public class ActionAnnotationProcessor extends AbstractProcessor {



    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        for (TypeElement annotation : annotations) {
            for (Element element : roundEnv.getElementsAnnotatedWith(annotation)) {

                Action actionAnnotation = element.getAnnotation(Action.class);
                if (actionAnnotation != null) {
//                   if (! EnumSet.of(ElementKind.CLASS, ElementKind.ENUM).contains(element.getKind())){
//                       processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Action annoation must be declared on a class or enum+", element, annotation.);
//                   }
//
                }
            }
        }
        
        return false;
    }
}
