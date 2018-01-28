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
import org.drombler.commons.action.context.AbstractActiveContextSensitiveToggleActionListener;
import org.drombler.commons.context.ActiveContextSensitive;
import org.drombler.commons.context.ContextEvent;


public abstract class AbstractColoredCircleAction extends AbstractActiveContextSensitiveToggleActionListener<ColoredCircleManager, Object> implements
        ActiveContextSensitive {

    private ColoredCircleManager coloredCircleManager;
    private final ColoredCircle coloredCircle;

    public AbstractColoredCircleAction(ColoredCircle coloredCircle) {
        super(ColoredCircleManager.class);
        this.coloredCircle = coloredCircle;
    }

    @Override
    public void onSelectionChanged(boolean oldValue, boolean newValue) {
        if (newValue && coloredCircleManager != null) {
            coloredCircleManager.setColoredCircle(coloredCircle);
        }
    }

    @Override
    protected void contextChanged(ContextEvent<ColoredCircleManager> event) {
        coloredCircleManager = getActiveContext().find(event.getType());
        setEnabled(coloredCircleManager != null);
        setSelected(coloredCircleManager != null
                && coloredCircleManager.getColoredCircle() != null
                && coloredCircleManager.getColoredCircle().equals(coloredCircle));
    }
}
