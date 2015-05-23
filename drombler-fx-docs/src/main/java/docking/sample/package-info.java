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
 * Copyright 2015 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
@DockingArea(id = "myEditorArea", kind = DockingAreaKind.EDITOR, position = 25,
        path = {20, 40, 50}, permanent = true)
@DockingArea(id = "myUpperViewArea", kind = DockingAreaKind.VIEW, position = 40,
        path = {20, 40, 20}, layoutConstraints = @LayoutConstraints(prefHeight = 50))
@DockingArea(id = "myLeftViewArea", kind = DockingAreaKind.VIEW, position = 10,
        path = {20, 20}, layoutConstraints = @LayoutConstraints(prefWidth = 200))
package docking.sample;

import org.drombler.acp.core.docking.DockingArea;
import org.drombler.acp.core.docking.DockingAreaKind;
import org.drombler.acp.core.docking.LayoutConstraints;
