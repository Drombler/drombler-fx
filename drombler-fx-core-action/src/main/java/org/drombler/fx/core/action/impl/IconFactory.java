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

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.drombler.fx.core.action.AbstractFXAction;
import org.drombler.fx.core.action.GraphicFactory;
import org.softsmithy.lib.util.ResourceLoader;

/**
 *
 * @author puce
 */
public class IconFactory implements GraphicFactory {

    private final Map<Integer, Image> images = new HashMap<>();
    private final String iconResourcePath;
    private final ResourceLoader resourceLoader;
    private final boolean smoothIcon;

    public IconFactory(String icon, ResourceLoader resourceLoader, boolean smoothIcon) {
        this.iconResourcePath = icon;
        this.resourceLoader = resourceLoader;
        this.smoothIcon = smoothIcon;
    }

    @Override
    public ImageView createGraphic(int size) {
        return new ImageView(getIconImage(size));
    }

    private Image getIconImage(int size) {
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
                return new Image(is, size, size, true, smoothIcon);
            } catch (Exception ex) {
                Logger.getLogger(IconFactory.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    private InputStream getImageInputStream(int size) {
        String currentIconResourcePath = getIconResourcePath(size);
        if (currentIconResourcePath != null) {
            return resourceLoader.getResourceAsStream(currentIconResourcePath);
        } else {
            return null;
        }
    }

    private String getIconResourcePath(int size) {
        String currentIconResourcePath = iconResourcePath;
        if (currentIconResourcePath != null) {
            String[] iconNameParts = currentIconResourcePath.split("\\.");
            if (iconNameParts.length > 0) {
                StringBuilder sb = new StringBuilder(iconNameParts[0]);
                sb.append(size);
                for (int i = 1; i < iconNameParts.length; i++) {
                    sb.append(".");
                    sb.append(iconNameParts[i]);
                }
                currentIconResourcePath = sb.toString();
            }
        }
        return currentIconResourcePath;
    }
}
