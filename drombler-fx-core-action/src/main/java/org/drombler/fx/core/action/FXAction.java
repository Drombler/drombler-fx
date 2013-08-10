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
package org.drombler.fx.core.action;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCombination;
import org.drombler.fx.core.commons.fx.scene.GraphicFactory;

/**
 *
 * @author puce
 */
public interface FXAction extends EventHandler<ActionEvent> {

    String getDisplayName();

    void setDisplayName(String displayName);

    StringProperty displayNameProperty();

    KeyCombination getAccelerator();

    void setAccelerator(KeyCombination keyCombination);

    ObjectProperty<KeyCombination> acceleratorProperty();

    boolean isDisabled();

    ReadOnlyBooleanProperty disabledProperty();
    
    GraphicFactory getGraphicFactory();
    
    void setGraphicFactory(GraphicFactory graphicFactory);
    
    ObjectProperty<GraphicFactory> graphicFactoryProperty();

    
//    void setGraphic(Node graphic);
//    
//    ObjectProperty<Node> graphicProperty();
//
//    String getIcon();
//
//    void setIcon(String icon);
//
//    StringProperty iconProperty();
//
//    Image getIconImage(int size);
}
