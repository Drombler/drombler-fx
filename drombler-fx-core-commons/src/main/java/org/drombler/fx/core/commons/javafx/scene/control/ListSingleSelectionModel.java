/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drombler.fx.core.commons.javafx.scene.control;

import java.util.List;
import javafx.scene.control.SingleSelectionModel;

/**
 *
 * @author puce
 */
public class ListSingleSelectionModel<T> extends SingleSelectionModel<T> {

    private final List<? extends T> list;

    public ListSingleSelectionModel(List<? extends T> list) {
        this.list = list;
    }

    @Override
    protected T getModelItem(int index) {
        if (index < 0 || index > getItemCount()) {
            return null;
        } else {
            return list.get(index);
        }
    }

    @Override
    protected int getItemCount() {
        return list.size();
    }
}
