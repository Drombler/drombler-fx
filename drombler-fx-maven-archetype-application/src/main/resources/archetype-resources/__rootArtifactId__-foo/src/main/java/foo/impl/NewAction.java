#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )


package ${package}.foo.impl;

import org.drombler.acp.core.action.Action;
import org.drombler.acp.core.action.MenuEntry;
import org.drombler.commons.action.AbstractActionListener;
import org.drombler.commons.data.Openable;

@Action(id = "new", category = "core", displayName = "%new.displayName", accelerator = "Shortcut+N")
@MenuEntry(path = "File", position = 10)
public class NewAction extends AbstractActionListener<Object> {

    @Override
    public void onAction(Object event) {
        FooHandler fooHandler = new FooHandler();
        Openable openable = fooHandler.getLocalContext().find(Openable.class);
        if (openable != null) {
            openable.open();
        }
    }

}
