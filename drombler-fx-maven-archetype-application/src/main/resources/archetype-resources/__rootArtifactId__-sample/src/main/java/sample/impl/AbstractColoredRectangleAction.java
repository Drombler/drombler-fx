#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ${package}.sample.impl;

import ${package}.sample.ColoredRectangle;
import ${package}.sample.ColoredRectangleManager;
import org.drombler.commons.action.context.AbstractActiveContextSensitiveToggleActionListener;
import org.drombler.commons.context.ActiveContextSensitive;
import org.drombler.commons.context.ContextEvent;


public class AbstractColoredRectangleAction extends AbstractActiveContextSensitiveToggleActionListener<ColoredRectangleManager, Object> implements
        ActiveContextSensitive {

    private ColoredRectangleManager coloredRectangleManager;
    private final ColoredRectangle coloredRectangle;

    public AbstractColoredRectangleAction(ColoredRectangle coloredRectangle) {
        super(ColoredRectangleManager.class);
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
    protected void contextChanged(ContextEvent<ColoredRectangleManager> event) {
        coloredRectangleManager = getActiveContext().find(event.getType());
        setEnabled(coloredRectangleManager != null);
        setSelected(coloredRectangleManager != null
                && coloredRectangleManager.getColoredRectangles() != null
                && coloredRectangleManager.getColoredRectangles().contains(coloredRectangle));
    }
}
