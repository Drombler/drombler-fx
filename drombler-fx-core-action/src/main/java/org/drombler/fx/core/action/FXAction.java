/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drombler.fx.core.action;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;

/**
 *
 * @author puce
 */
public interface FXAction extends EventHandler<ActionEvent> {

    StringProperty displayNameProperty();

    ObjectProperty<KeyCombination> acceleratorProperty();

    StringProperty iconProperty();

    ReadOnlyBooleanProperty disabledProperty();

    String getDisplayName();

    KeyCombination getAccelerator();

    String getIcon();

    boolean isDisabled();

    Image getIconImage(int size);

    public void setAccelerator(KeyCombination keyCombination);

    public void setDisplayName(String displayName);

    public void setIcon(String icon);
}
