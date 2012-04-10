/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.startup.impl.action;

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
