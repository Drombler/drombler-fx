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

import org.drombler.fx.core.action.AbstractFXAction;

/**
 *
 * @author puce
 */
public abstract class AbstractFXActionAdapter<T> extends AbstractFXAction {

    private final T adapted;

    public AbstractFXActionAdapter(T adapted) {
        this.adapted = adapted;
    }

    public T getAdapted() {
        return adapted;
    }

//    @Override
//    protected InputStream getImageInputStream(String icon) {
//        return getAdapted().getClass().getResourceAsStream(icon);
//    }
}
