/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.startup;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ObjectProperty;
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
    private final StringProperty icon = new SimpleStringProperty(this, "icon", null);
    private final Map<Integer, Image> images = new HashMap<>();

    @Override
    public StringProperty displayNameProperty() {
        return displayName;
    }

    @Override
    public ObjectProperty<KeyCombination> acceleratorProperty() {
        return accelerator;
    }

    @Override
    public StringProperty iconProperty() {
        return icon;
    }

    @Override
    public final String getDisplayName() {
        return displayNameProperty().get();
    }

    @Override
    public final void setDisplayName(String displayName) {
        displayNameProperty().set(displayName);
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
    public final String getIcon() {
        return iconProperty().get();
    }

    @Override
    public final void setIcon(String icon) {
        iconProperty().set(icon);
    }

    @Override
    public Image getIconImage(int size) {
        if (!images.containsKey(size)) {
            Image image = loadImage(size);
            if (image != null) {
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
        if (getIcon() != null) {
            String currentIcon = getIcon();
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
            return getImageInputStream(currentIcon);
        } else {
            return null;
        }
    }

    protected abstract InputStream getImageInputStream(String icon);
}
