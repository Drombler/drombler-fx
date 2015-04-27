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
package action.sample.impl;

import org.drombler.acp.core.action.Action;
import org.drombler.acp.core.action.MenuEntry;
import org.drombler.acp.core.action.ToolBarEntry;
import org.drombler.commons.action.AbstractActionListener;

@Action(id = "test1", category = "test", displayName = "%test1.displayName",
        accelerator = "Shortcut+T", icon = "test1.png")
@MenuEntry(path = "File", position = 20)
@ToolBarEntry(toolBarId = "file", position = 30)

public class MyAction1 extends AbstractActionListener<Object> {

    @Override
    public void onAction(Object event) {
        System.out.println("Test1 Action!");
    }
}
