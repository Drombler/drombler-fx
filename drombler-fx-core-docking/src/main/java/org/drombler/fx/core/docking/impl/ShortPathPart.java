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
package org.drombler.fx.core.docking.impl;

import java.util.Objects;
import javafx.geometry.Orientation;

/**
 *
 * @author puce
 */
public class ShortPathPart {

    private final Integer position;
    private final int inActualLevel;
    private final Orientation inOrientation;

    /**
     * {@code inActualLevel} and {@code inOrientation} refer to the parent, so read it as: The {@code position} in the
     * split pane with orientation {@code inOrientation} and at the actual (not shortened) level {@code inActualLevel}.
     *
     * @param position
     * @param inActualLevel
     * @param inOrientation
     */
    public ShortPathPart(Integer position, int inActualLevel, Orientation inOrientation) {
        this.position = position;
        this.inActualLevel = inActualLevel;
        this.inOrientation = inOrientation;
    }

    /**
     * @return the pathPart
     */
    public Integer getPosition() {
        return position;
    }

    /**
     * @return the level
     */
    public int getInActualLevel() {
        return inActualLevel;
    }

    /**
     * @return the orientation
     */
    public Orientation getInOrientation() {
        return inOrientation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, inActualLevel, inOrientation);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof ShortPathPart)) {
            return false;
        }
        ShortPathPart other = (ShortPathPart) obj;

        return Objects.equals(position, other.position)
                && inActualLevel == other.inActualLevel
                && Objects.equals(inOrientation, other.inOrientation);
    }

    @Override
    public String toString() {
        return "ShortPathPart{" + "position=" + position + ", inActualLevel=" + inActualLevel + ", inOrientation=" + inOrientation + '}';
    }
}
