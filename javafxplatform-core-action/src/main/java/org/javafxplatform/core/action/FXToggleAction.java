/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.action;

import javafx.beans.property.BooleanProperty;

/**
 *
 * @author puce
 */
public interface FXToggleAction extends FXCheckAction {

    @Override
    BooleanProperty selectedProperty();

    void setSelected(boolean selected);
}
