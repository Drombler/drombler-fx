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
package tutorial.docking;

import javafx.scene.layout.GridPane;
import org.drombler.acp.core.docking.DockingState;
import org.drombler.acp.core.docking.ViewDocking;
import org.drombler.acp.core.docking.WindowMenuEntry;

@ViewDocking(areaId = "left", position = 50, displayName = "%MyViewPane.displayName",
        accelerator = "Shortcut+4", icon = "myViewPane.png", state = DockingState.DOCKED,
        menuEntry = @WindowMenuEntry(path = "", position = 30))
public class DockableView extends GridPane {

}
