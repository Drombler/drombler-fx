/*
 *         COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Notice
 *
 * The contents of this file are subject to the COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL)
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.opensource.org/licenses/cddl1.txt
 *
 * The Original Code is Drombler.org. The Initial Developer of the
 * Original Code is Florian Brunner (Sourceforge.net user: puce).
 * Copyright 2012 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
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
 * Checks if the listener class implements {@link EventHandler<ActionEvent>}.<br>
 * <br>
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
