/*
 *         COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Notice
 *
 * The contents of this file are subject to the COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL)
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.opensource.org/licenses/cddl1.txt
 *
 * The Original Code is Drombler.org. The Initial Developer of the
 * Original Code is Florian Brunner (Sourceforge.net user: puce).
 * Copyright 2012 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
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
