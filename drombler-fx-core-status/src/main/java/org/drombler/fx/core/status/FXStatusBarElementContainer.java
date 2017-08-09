package org.drombler.fx.core.status;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javafx.collections.ObservableList;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Separator;
import org.drombler.acp.core.status.spi.StatusBarElementContainer;
import org.drombler.commons.fx.scene.control.StatusBar;
import org.softsmithy.lib.util.PositionableAdapter;
import org.softsmithy.lib.util.Positionables;

/**
 *
 * @author puce
 */
public class FXStatusBarElementContainer implements StatusBarElementContainer<Node> {

    private static final int SEPARATOR_STEPS = 1000;

    private final List<PositionableAdapter<? extends Node>> leftStatusBarElementList = Collections.synchronizedList(new ArrayList<>());// TODO: synchronized needed?
    private final List<PositionableAdapter<? extends Node>> centerStatusBarElementList = Collections.synchronizedList(new ArrayList<>());// TODO: synchronized needed?
    private final List<PositionableAdapter<? extends Node>> statusStatusBarElementList = Collections.synchronizedList(new ArrayList<>());// TODO: synchronized needed?

    private final StatusBar statusBar;

    public FXStatusBarElementContainer(StatusBar statusBar) {
        this.statusBar = statusBar;
    }

    @Override
    public void addLeftStatusBarElement(PositionableAdapter<? extends Node> statusBarElement) {
        addStatusBarElement(statusBar.getLeftEntries(), leftStatusBarElementList, statusBarElement);
    }

    @Override
    public void addCenterStatusBarElement(PositionableAdapter<? extends Node> statusBarElement) {
        addStatusBarElement(statusBar.getCenterEntries(), centerStatusBarElementList, statusBarElement);
    }

    @Override
    public void addRightStatusBarElement(PositionableAdapter<? extends Node> statusBarElement) {
        addStatusBarElement(statusBar.getRightEntries(), statusStatusBarElementList, statusBarElement);
    }

    private void addStatusBarElement(ObservableList<Node> entries, List<PositionableAdapter<? extends Node>> elements, PositionableAdapter<? extends Node> statusBarElementAdapter) {
        int insertionPoint = Positionables.getInsertionPoint(elements, statusBarElementAdapter);
        entries.add(insertionPoint, statusBarElementAdapter.getAdapted());
        elements.add(insertionPoint, statusBarElementAdapter);

        Optional<Integer> separatorInsertionPoint = getSeparatorInsertionPoint(insertionPoint, entries, elements, statusBarElementAdapter);

        if (separatorInsertionPoint != null && separatorInsertionPoint.isPresent()) {
            entries.add(separatorInsertionPoint.get(), new Separator(Orientation.VERTICAL));
        }

    }

    private Optional<Integer> getSeparatorInsertionPoint(int insertionPoint, ObservableList<Node> entries, List<PositionableAdapter<? extends Node>> elements,
            PositionableAdapter<? extends Node> statusBarElementAdapter) {
        if (insertionPoint < elements.size() - 1
                && ((elements.get(insertionPoint + 1).getPosition() / SEPARATOR_STEPS) - (statusBarElementAdapter.getPosition() / SEPARATOR_STEPS)) >= 1
                && !(entries.get(insertionPoint + 1) instanceof Separator)) {
            return Optional.of(insertionPoint + 1);

        }

        if (insertionPoint > 0
                && ((statusBarElementAdapter.getPosition() / SEPARATOR_STEPS) - (elements.get(insertionPoint - 1).getPosition() / SEPARATOR_STEPS)) >= 1
                && !(entries.get(insertionPoint - 1) instanceof Separator)) {
            return Optional.of(insertionPoint);

        }

        return Optional.empty();

    }

    @Override
    public boolean isLeftToRight() {
        return statusBar.getEffectiveNodeOrientation() == NodeOrientation.LEFT_TO_RIGHT;
    }

    @Override
    public boolean isMirroringEnabled() {
        return statusBar.usesMirroring();
    }

}
