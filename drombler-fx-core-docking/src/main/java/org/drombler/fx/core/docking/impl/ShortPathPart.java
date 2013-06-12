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

/**
 *
 * @author puce
 */
public class ShortPathPart {

    private final int position;
    private final SplitLevel inActualLevel;

    /**
     * {@code inActualLevel} refers to the parent, so read it as: The {@code position} in the split pane at the actual
     * (not shortened) level {@code inActualLevel}.
     *
     * @param position
     * @param inActualLevel
     */
    public ShortPathPart(int position, int inActualLevel) {
        this(position, SplitLevel.valueOf(inActualLevel));
    }

    public ShortPathPart(int position, SplitLevel inActualLevel) {
        this.position = position;
        this.inActualLevel = inActualLevel;
    }

    /**
     * @return the pathPart
     */
    public int getPosition() {
        return position;
    }

    /**
     * @return the level
     */
    public SplitLevel getInActualLevel() {
        return inActualLevel;
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, inActualLevel);
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

        return position == other.position
                && Objects.equals(inActualLevel, other.inActualLevel);
    }

    @Override
    public String toString() {
        return "ShortPathPart[" + "position=" + position + ", inActualLevel=" + inActualLevel + ']';
    }
}
