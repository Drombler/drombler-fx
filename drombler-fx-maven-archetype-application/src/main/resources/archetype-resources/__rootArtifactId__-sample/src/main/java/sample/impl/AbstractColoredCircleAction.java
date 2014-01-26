#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ${package}.sample.impl;

import ${package}.sample.ColoredCircle;
import ${package}.sample.ColoredCircleManager;
import org.drombler.acp.core.action.AbstractToggleActionListener;
import org.drombler.commons.context.ActiveContextSensitive;
import org.drombler.commons.context.Context;
import org.drombler.commons.context.ContextEvent;
import org.drombler.commons.context.ContextListener;


public abstract class AbstractColoredCircleAction extends AbstractToggleActionListener<Object> implements ActiveContextSensitive {

    private ColoredCircleManager coloredCircleManager;
    private Context activeContext;
    private final ColoredCircle coloredCircle;

    public AbstractColoredCircleAction(ColoredCircle coloredCircle) {
        this.coloredCircle = coloredCircle;
    }

    @Override
    public void onSelectionChanged(boolean oldValue, boolean newValue) {
        if (newValue && coloredCircleManager != null) {
            coloredCircleManager.setColoredCircle(coloredCircle);
        }
    }

    @Override
    public void setActiveContext(Context activeContext) {
        this.activeContext = activeContext;
        this.activeContext.addContextListener(ColoredCircleManager.class, new ContextListener() {
            @Override
            public void contextChanged(ContextEvent event) {
                AbstractColoredCircleAction.this.contextChanged();
            }
        });
        contextChanged();
    }

    private void contextChanged() {
        coloredCircleManager = activeContext.find(ColoredCircleManager.class);
        setDisabled(coloredCircleManager == null);
        setSelected(coloredCircleManager != null
                && coloredCircleManager.getColoredCircle() != null
                && coloredCircleManager.getColoredCircle().equals(coloredCircle));
    }
}
