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
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author puce
 */
public class SplitLevelTest {

    @Test
    @Ignore
    public void testGetLevel() {
        System.out.println("getLevel");
        SplitLevel instance = null;
        int expResult = 0;
        int result = instance.getLevel();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    @Ignore
    public void testGetOrientation() {
        System.out.println("getOrientation");
        SplitLevel instance = null;
        Orientation expResult = null;
        Orientation result = instance.getOrientation();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    @Ignore
    public void testValueOf() {
        System.out.println("valueOf");
        int level = 0;
        SplitLevel expResult = null;
        SplitLevel result = SplitLevel.valueOf(level);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    @Ignore
    public void testHashCode() {
        System.out.println("hashCode");
        SplitLevel instance = null;
        int expResult = 0;
        int result = instance.hashCode();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    @Ignore
    public void testEquals() {
        System.out.println("equals");
        Object obj = null;
        SplitLevel instance = null;
        boolean expResult = false;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    @Ignore
    public void testToString() {
        System.out.println("toString");
        SplitLevel instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }
}
