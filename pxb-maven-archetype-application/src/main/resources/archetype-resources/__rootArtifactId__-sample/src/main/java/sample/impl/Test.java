#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ${package}.sample.impl;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.richclientplatform.core.action.Action;
import org.richclientplatform.core.action.MenuEntry;

/**
 *
 * @author puce
 */
@Action(id = "test", category = "test", displayName = "#test.displayName")
@MenuEntry(path = "File", position = 20)
public class Test implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent t) {
        System.out.println("hello world!");
    }
}
