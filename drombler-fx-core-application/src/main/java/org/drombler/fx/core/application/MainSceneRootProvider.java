/*
 *         COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Notice
 *
 * The contents of this file are subject to the COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL)
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.opensource.org/licenses/cddl1.txt
 *
 * The Original Code is Drombler.org. The Initial Developer of the
 * Original Code is Florian Brunner (GitHub user: puce77).
 * Copyright 2018 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.fx.core.application;

import javafx.scene.Parent;

/**
 * The MainSceneRootProvider service interface provides the root parent node of the main scene.
 *
 * This root parent node can define the basic application layout.
 *
 * To make such an application layout more reusable it's recommended to get the main application content from an {@link ApplicationContentProvider}.
 */
public interface MainSceneRootProvider {

    /**
     * Gets the root parent node of the main scene.
     *
     * @return the root parent node of the main scene
     */
    Parent getRoot();
}
