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
package tutorial.action.sample.impl;

import org.drombler.acp.core.action.ToggleAction;
import org.drombler.acp.core.action.ToggleMenuEntry;
import org.drombler.acp.core.action.ToolBarToggleEntry;
import org.drombler.commons.action.AbstractToggleActionListener;

@ToggleAction(id = "test1", category = "test", displayName = "%test1.displayName",
        accelerator = "Shortcut+T", icon = "test1.png")
@ToggleMenuEntry(path = "Custom/Sub", position = 30, toggleGroupId = "test")
@ToolBarToggleEntry(toolBarId = "test", position = 30, toggleGroupId = "test")
public class MyToggleAction extends AbstractToggleActionListener<Object> {

    @Override
    public void onSelectionChanged(boolean oldValue, boolean newValue) {
        System.out.println("Test1 Toggle Action selection changed: " + newValue);
    }
}
