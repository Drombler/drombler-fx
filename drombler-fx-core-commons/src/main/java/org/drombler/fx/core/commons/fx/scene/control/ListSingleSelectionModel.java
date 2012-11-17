/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drombler.fx.core.commons.fx.scene.control;

import java.util.List;
import javafx.scene.control.SingleSelectionModel;

/**
 * A {@link List} based {@link SingleSelectionModel} implementation.
 *
 * @author puce
 */
public class ListSingleSelectionModel<T> extends SingleSelectionModel<T> {

    private final List<? extends T> list;

    /**
     * Creates a new instance of this class.
     *
     * @param list the list providing the items.
     */
    public ListSingleSelectionModel(List<? extends T> list) {
        this.list = list;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected T getModelItem(int index) {
        if (index < 0 || index > getItemCount()) {
            return null;
        } else {
            return list.get(index);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected int getItemCount() {
        return list.size();
    }
}
