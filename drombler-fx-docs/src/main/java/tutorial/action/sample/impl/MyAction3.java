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

import tutorial.action.sample.MyCommand;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.drombler.acp.core.action.Action;
import org.drombler.acp.core.action.MenuEntry;
import org.drombler.acp.core.action.ToolBarEntry;
import org.drombler.commons.action.AbstractActionListener;
import org.drombler.commons.context.ApplicationContextSensitive;
import org.drombler.commons.context.Context;

@Action(id = "myaction", category = "mycategory", displayName = "%myaction.displayName",
        accelerator = "Shortcut+Shift+M", icon = "myaction.png")
@MenuEntry(path = "File", position = 3210)
@ToolBarEntry(toolBarId = "file", position = 72)
public class MyAction3 extends AbstractActionListener<Object> implements ApplicationContextSensitive {

    private Collection<? extends MyCommand> myCommands = Collections.emptyList();
    private Context applicationContext;

    public MyAction3() {
        setEnabled(false);
    }

    @Override
    public void onAction(Object event) {
        // protect against modification during iteration TODO: needed?
        List<MyCommand> currentMyCommands = new ArrayList<>(myCommands);
        currentMyCommands.forEach(myCommand -> myCommand.doSomething());
    }

    @Override
    public void setApplicationContext(Context applicationContext) {
        this.applicationContext = applicationContext;
        this.applicationContext.addContextListener(MyCommand.class,
                event -> contextChanged());
        contextChanged();
    }

    private void contextChanged() {
        myCommands = applicationContext.findAll(MyCommand.class);
        setEnabled(!myCommands.isEmpty());
    }
}
