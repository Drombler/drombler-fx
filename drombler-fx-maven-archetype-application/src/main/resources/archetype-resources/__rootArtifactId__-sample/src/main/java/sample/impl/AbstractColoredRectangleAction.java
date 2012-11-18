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
import ${package}.sample.ColoredRectangle;
import ${package}.sample.ColoredRectangleManager;
import org.drombler.acp.core.action.AbstractToggleActionListener;
import org.drombler.acp.core.commons.util.context.ActiveContextSensitive;
import org.drombler.acp.core.commons.util.context.Context;
import org.drombler.acp.core.commons.util.context.ContextEvent;
import org.drombler.acp.core.commons.util.context.ContextListener;


public abstract class AbstractColoredRectangleAction extends AbstractToggleActionListener<Object> implements ActiveContextSensitive {

    private ColoredRectangleManager coloredRectangleManager;
    private Context activeContext;
    private final ColoredRectangle coloredRectangle;

    public AbstractColoredRectangleAction(ColoredRectangle coloredRectangle) {
        this.coloredRectangle = coloredRectangle;
    }

    @Override
    public void onSelectionChanged(boolean oldValue, boolean newValue) {
        if (coloredRectangleManager != null) {
            if (newValue) {
                coloredRectangleManager.getColoredRectangles().add(coloredRectangle);
            } else {
                coloredRectangleManager.getColoredRectangles().remove(coloredRectangle);
            }
        }
    }

    @Override
    public void setActiveContext(Context activeContext) {
        this.activeContext = activeContext;
        this.activeContext.addContextListener(ColoredCircleManager.class, new ContextListener() {
            @Override
            public void contextChanged(ContextEvent event) {
                AbstractColoredRectangleAction.this.contextChanged();
            }
        });
        contextChanged();
    }

    private void contextChanged() {
        coloredRectangleManager = activeContext.find(ColoredRectangleManager.class);
        setDisabled(coloredRectangleManager == null);
        setSelected(coloredRectangleManager != null
                && coloredRectangleManager.getColoredRectangles() != null
                && coloredRectangleManager.getColoredRectangles().contains(coloredRectangle));
    }
}
