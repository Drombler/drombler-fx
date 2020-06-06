#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ${package}.sample.impl;

import org.drombler.acp.core.action.Action;
import org.drombler.acp.core.action.MenuEntry;
import org.drombler.commons.action.AbstractActionListener;
import org.drombler.commons.client.util.ResourceBundleUtils;

@Action(id = "test1", category = "test", displayName = "%test1.displayName", resourceBundleBaseName = ResourceBundleUtils.PACKAGE_RESOURCE_BUNDLE_BASE_NAME)
@MenuEntry(path = "File", position = 20)
public class Test1 extends AbstractActionListener<Object> {

    @Override
    public void onAction(Object event) {
        System.out.println("Test1 Action!");
    }
}
