package org.drombler.fx.core.action.impl;

import javafx.scene.control.SeparatorMenuItem;
import org.drombler.acp.core.action.spi.SeparatorMenuItemFactory;
import org.osgi.service.component.annotations.Component;

/**
 *
 * @author puce
 */
@Component
public class FXSeparatorMenuItemFactory implements SeparatorMenuItemFactory<SeparatorMenuItem> {

    @Override
    public SeparatorMenuItem createSeparatorMenuItem() {
        return new SeparatorMenuItem();
    }

}
