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
package org.drombler.fx.core.action.impl;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javafx.event.ActionEvent;
import org.drombler.acp.core.action.ActionListener;

/**
 *
 * @author puce
 */
public class ActionListenerAdapter extends AbstractFXActionAdapter<ActionListener<? super ActionEvent>> {

    public ActionListenerAdapter(ActionListener<? super ActionEvent> listener) {
        super(listener);
        listener.addPropertyChangeListener("disabled", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                setDisabled((Boolean) evt.getNewValue());
            }
        });
        setDisabled(listener.isDisabled());
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        getAdapted().onAction(actionEvent);
    }

//    @Override
//    protected InputStream getImageInputStream(String icon) {
//        return getAdapted().getImageInputStream(icon);
//    }
    
}
