/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drombler.fx.core.action;

import javafx.beans.property.BooleanProperty;

/**
 *
 * @author puce
 */
public interface FXToggleAction extends FXAction {

    BooleanProperty selectedProperty();

    boolean isSelected();

    void setSelected(boolean selected);
}
