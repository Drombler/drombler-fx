/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.application;

import javafx.beans.value.ChangeListener;
import javafx.scene.Node;

/**
 *
 * @author puce
 */
// TODO: good?
public interface FocusOwnerChangeListenerProvider {

    ChangeListener<Node> getFocusOwnerChangeListener();
}
