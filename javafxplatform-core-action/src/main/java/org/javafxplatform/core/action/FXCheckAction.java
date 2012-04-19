/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.action;

import javafx.beans.property.ReadOnlyBooleanProperty;

/**
 *
 * @author puce
 */
public interface FXCheckAction extends FXAction {

    ReadOnlyBooleanProperty selectedProperty();

    boolean isSelected();
}
