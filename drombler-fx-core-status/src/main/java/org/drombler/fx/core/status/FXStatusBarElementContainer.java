package org.drombler.fx.core.status;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import org.drombler.acp.core.status.spi.StatusBarElementContainer;
import org.drombler.commons.fx.scene.control.StatusBar;
import org.softsmithy.lib.util.PositionableAdapter;

/**
 *
 * @author puce
 */
public class FXStatusBarElementContainer implements StatusBarElementContainer {

    private final StatusBar statusBar;

    public FXStatusBarElementContainer(StatusBar statusBar) {
        this.statusBar = statusBar;
    }

    @Override
    public void addLeftStatusBarElement(PositionableAdapter<? extends Node> statusBarElement) {
        addStatusBarElement(statusBar.getLeftEntries(), statusBarElement);
    }

    @Override
    public void addCenterStatusBarElement(PositionableAdapter<? extends Node> statusBarElement) {
        addStatusBarElement(statusBar.getCenterEntries(), statusBarElement);
    }

    @Override
    public void addRightStatusBarElement(PositionableAdapter<? extends Node> statusBarElement) {
        addStatusBarElement(statusBar.getRightEntries(), statusBarElement);
    }

    private void addStatusBarElement(ObservableList<Node> entries, PositionableAdapter<? extends Node> statusBarElement) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
