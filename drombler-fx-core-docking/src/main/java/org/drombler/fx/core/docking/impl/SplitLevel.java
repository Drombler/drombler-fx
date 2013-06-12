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
public class SplitLevel {

    public static final SplitLevel ROOT = new SplitLevel(0);
    private final int level;
    private final Orientation orientation;

    private SplitLevel(int level) {
        this.level = level;
        this.orientation = calculateOrientation();
    }

    private Orientation calculateOrientation() {
        return getLevel() % 2 == 0 ? Orientation.VERTICAL : Orientation.HORIZONTAL;
    }

    /**
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * @return the orientation
     */
    public Orientation getOrientation() {
        return orientation;
    }

    public static SplitLevel valueOf(int level) {
        if (level == ROOT.level) {
            return ROOT;
        } else {
            return new SplitLevel(level);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(level, orientation);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof SplitLevel)) {
            return false;
        }
        SplitLevel other = (SplitLevel) obj;

        return level == other.level
                && Objects.equals(orientation, other.orientation);
    }

    @Override
    public String toString() {
        return "SplitLevel[" + "level=" + level + ", orientation=" + orientation + ']';
    }
}
