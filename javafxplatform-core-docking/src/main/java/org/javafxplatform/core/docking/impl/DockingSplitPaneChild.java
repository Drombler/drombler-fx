/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.docking.impl;

import org.richclientplatform.core.lib.util.Positionable;

/**
 *
 * @author puce
 */
interface DockingSplitPaneChild extends Positionable {

    void setParentSplitPane(DockingSplitPane parentSplitPane);

    boolean isSplitPane();
}
