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
package org.drombler.fx.core.application;

import javafx.scene.Node;

/**
 * The ApplicationContentProvider service interface provides the application content node. An ApplicationContentProvider can be used by several {@link  MainSceneRootProvider} service implementations.
 */
public interface ApplicationContentProvider {

    /**
     * Gets the application content node.
     *
     * @return the application content node
     */
    Node getContentPane();
}
