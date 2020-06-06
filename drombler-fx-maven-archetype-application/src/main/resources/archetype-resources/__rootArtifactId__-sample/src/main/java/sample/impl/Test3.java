#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ${package}.sample.impl;

import javafx.event.ActionEvent;
import org.drombler.acp.core.action.Action;
import org.drombler.acp.core.action.MenuEntry;
import org.drombler.commons.action.fx.AbstractFXAction;
import org.drombler.commons.client.util.ResourceBundleUtils;

@Action(id = "test3", category = "test", displayName = "%test3.displayName", resourceBundleBaseName = ResourceBundleUtils.PACKAGE_RESOURCE_BUNDLE_BASE_NAME)
@MenuEntry(path = "File", position = 1220)
public class Test3 extends AbstractFXAction {

    @Override
    public void handle(ActionEvent t) {
        System.out.println("Test3 Action!");
    }
}
