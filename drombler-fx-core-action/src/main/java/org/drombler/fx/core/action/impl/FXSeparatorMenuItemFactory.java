package org.drombler.fx.core.action.impl;

import javafx.scene.control.SeparatorMenuItem;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.drombler.acp.core.action.spi.SeparatorMenuItemFactory;

/**
 *
 * @author puce
 */
@Component
@Service
public class FXSeparatorMenuItemFactory implements SeparatorMenuItemFactory<SeparatorMenuItem> {

    @Override
    public SeparatorMenuItem createSeparatorMenuItem() {
        return new SeparatorMenuItem();
    }

}
