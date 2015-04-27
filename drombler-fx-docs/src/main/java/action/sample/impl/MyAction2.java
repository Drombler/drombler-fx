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

import action.sample.MyCommand;
import org.drombler.acp.core.action.Action;
import org.drombler.acp.core.action.MenuEntry;
import org.drombler.acp.core.action.ToolBarEntry;
import org.drombler.commons.action.AbstractActionListener;
import org.drombler.commons.context.ActiveContextSensitive;
import org.drombler.commons.context.Context;

@Action(id = "myaction", category = "mycategory", displayName = "%myaction.displayName",
        accelerator = "Shortcut+M", icon = "myaction.gif")
@MenuEntry(path = "File", position = 3200)
@ToolBarEntry(toolBarId = "file", position = 42)
public class MyAction2 extends AbstractActionListener<Object> implements ActiveContextSensitive {

    private MyCommand myCommand;
    private Context activeContext;

    public MyAction2() {
        setEnabled(false);
    }

    @Override
    public void onAction(Object event) {
        myCommand.doSomething();
    }

    @Override
    public void setActiveContext(Context activeContext) {
        this.activeContext = activeContext;
        this.activeContext.addContextListener(MyCommand.class, event -> contextChanged());
        contextChanged();
    }

    private void contextChanged() {
        myCommand = activeContext.find(MyCommand.class);
        setEnabled(myCommand != null);
    }

}
