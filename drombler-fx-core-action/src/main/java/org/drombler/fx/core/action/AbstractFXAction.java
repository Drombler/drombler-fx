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
package org.drombler.fx.core.action;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanPropertyBase;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;

/**
 *
 * @author puce
 */
public abstract class AbstractFXAction implements FXAction {

    private static final boolean SMOOTH_ICON = false;
    private final StringProperty displayName = new SimpleStringProperty(this, "displayName", null);
    private final ObjectProperty<KeyCombination> accelerator = new SimpleObjectProperty<>(this, "accelerator", null);
    private final DisabledProperty disabled = new DisabledProperty();
    private final StringProperty icon = new SimpleStringProperty(this, "icon", null);
    private final Map<Integer, Image> images = new HashMap<>();

    @Override
    public final String getDisplayName() {
        return displayNameProperty().get();
    }

    @Override
    public final void setDisplayName(String displayName) {
        displayNameProperty().set(displayName);
    }

    @Override
    public StringProperty displayNameProperty() {
        return displayName;
    }

    @Override
    public final KeyCombination getAccelerator() {
        return acceleratorProperty().get();
    }

    @Override
    public final void setAccelerator(KeyCombination keyCombination) {
        acceleratorProperty().set(keyCombination);
    }

    @Override
    public ObjectProperty<KeyCombination> acceleratorProperty() {
        return accelerator;
    }

    @Override
    public final boolean isDisabled() {
        return disabledProperty().get();
    }

    protected void setDisabled(boolean disabled) {
        this.disabled.set(disabled);
    }

    @Override
    public ReadOnlyBooleanProperty disabledProperty() {
        return disabled;
    }

    @Override
    public final String getIcon() {
        return iconProperty().get();
    }

    @Override
    public final void setIcon(String icon) {
        iconProperty().set(icon);
    }

    @Override
    public StringProperty iconProperty() {
        return icon;
    }

    @Override
    public Image getIconImage(int size) {
        if (!images.containsKey(size)) {
            Image image = loadImage(size);
            if (image != null) { // TODO: add null?
                images.put(size, image);
            }
        }
        return images.get(size);

    }

    private Image loadImage(int size) {
        InputStream imageInputStream = getImageInputStream(size);
        if (imageInputStream != null) {
            try (InputStream is = imageInputStream) {

                return new Image(is, size, size, true,
                        SMOOTH_ICON);
            } catch (Exception ex) {
                Logger.getLogger(AbstractFXAction.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    private InputStream getImageInputStream(int size) {
        String currentIcon = getIcon(size);
        if (currentIcon != null) {
            return getImageInputStream(currentIcon);
        } else {
            return null;
        }
    }

    private String getIcon(int size) {
        String currentIcon = getIcon();
        if (currentIcon != null) {
            String[] iconNameParts = getIcon().split("\\.");
            if (iconNameParts.length > 0) {
                StringBuilder sb = new StringBuilder(iconNameParts[0]);
                sb.append(size);
                for (int i = 1; i < iconNameParts.length; i++) {
                    sb.append(".");
                    sb.append(iconNameParts[i]);
                }
                currentIcon = sb.toString();
            }
        }
        return currentIcon;
    }

    protected InputStream getImageInputStream(String icon) {
        return getClass().getResourceAsStream(icon);
    }

    private class DisabledProperty extends ReadOnlyBooleanPropertyBase {

        private boolean disabled = false;

        @Override
        public final boolean get() {
            return disabled;
        }

        private void set(boolean newValue) {
            disabled = newValue;
            fireValueChangedEvent();
        }

        @Override
        public Object getBean() {
            return AbstractFXAction.this;
        }

        @Override
        public String getName() {
            return "disabled";
        }
    }
}
