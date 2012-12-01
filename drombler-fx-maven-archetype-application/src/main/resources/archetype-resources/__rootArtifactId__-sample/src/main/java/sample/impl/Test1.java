#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ${package}.sample.impl;

import org.drombler.acp.core.action.AbstractActionListener;
import org.drombler.acp.core.action.Action;
import org.drombler.acp.core.action.MenuEntry;

@Action(id = "test1", category = "test", displayName = "#test1.displayName")
@MenuEntry(path = "File", position = 20)
public class Test1 extends AbstractActionListener<Object> {

    @Override
    public void onAction(Object event) {
        System.out.println("Test1 Action!");
    }
}
