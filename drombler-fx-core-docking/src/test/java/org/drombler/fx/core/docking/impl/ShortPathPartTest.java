/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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

    public ShortPathPartTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

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
