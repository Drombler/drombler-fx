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

import javafx.geometry.Orientation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.softsmithy.devlib.junit.Tests;

/**
 *
 * @author puce
 */
public class ShortPathPartTest {

    @Test
    public void testHashCode() {
        // equal
        ShortPathPart part1 = new ShortPathPart(1, 4, Orientation.VERTICAL);
        ShortPathPart part2 = new ShortPathPart(1, 4, Orientation.VERTICAL);

        Tests.testHashCode(part1, part2);
    }

    @Test
    public void testEquals() {
        // equal
        ShortPathPart part1 = new ShortPathPart(1, 4, Orientation.VERTICAL);
        ShortPathPart part2 = new ShortPathPart(1, 4, Orientation.VERTICAL);
        ShortPathPart part3 = new ShortPathPart(1, 4, Orientation.VERTICAL);

        // unequal
        ShortPathPart part4 = new ShortPathPart(2, 4, Orientation.VERTICAL);
        ShortPathPart part5 = new ShortPathPart(1, 5, Orientation.VERTICAL);
        ShortPathPart part6 = new ShortPathPart(1, 4, Orientation.HORIZONTAL);
        
        Tests.testEquals(part1, part2, part3, part4, part5, part6);
    }
}
