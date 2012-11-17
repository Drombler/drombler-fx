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

import javafx.beans.property.ReadOnlyBooleanPropertyBase;

/**
 *
 * @author puce
 */
public class FixedBooleanProperty extends ReadOnlyBooleanPropertyBase {
    private final Object bean;
    private final String name;
    private final boolean value;

    public FixedBooleanProperty(Object bean, String name, boolean value) {
        this.bean = bean;
        this.name = name;
        this.value = value;
    }

    @Override
    public final boolean get() {
        return value;
    }

    @Override
    public Object getBean() {
        return bean;
    }

    @Override
    public String getName() {
        return name;
    }
    
}
