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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.geometry.Orientation;
import org.drombler.fx.core.docking.DockablePane;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Test;
import org.softsmithy.lib.util.PositionableAdapter;

/**
 *
 * @author puce
 */
public class DockingSplitPaneTest {

    private static final String TEST1 = "test1";
    private static final String TEST2 = "test2";
    private static final String TEST3 = "test3";
    private static final String TEST4 = "test4";
    private static final String LEFT = "left";
    private static final String RIGHT = "right";
    private static final String TOP = "top";
    private static final String BOTTOM = "bottom";
    private static final String CENTER = "center";
    private final DockingSplitPane rootSplitPane = new DockingSplitPane(0, 0, SplitLevel.ROOT);
    private final DockingAreaManager rootManager = new DockingAreaManager(null, 0, SplitLevel.ROOT);

    @Test
    public void testAddDockingArea1() {
        System.out.println("addDockingArea1");

        assertEquals(0, rootSplitPane.getPosition());
        assertEquals(0, rootSplitPane.getLevel());
        assertEquals(0, rootSplitPane.getActualLevel());
        assertEquals(Orientation.VERTICAL, rootSplitPane.getOrientation());
        assertNull(rootSplitPane.getParentSplitPane());

        DockingAreaPane test1 = createDockingArea(10, TEST1, 20);
        rootSplitPane.addDockingArea(test1);

        DockingSplitPane parentSplitPane = test1.getParentSplitPane();
        assertNotNull(parentSplitPane);
        assertEquals(0, parentSplitPane.getPosition());
        assertEquals(0, parentSplitPane.getLevel());
        assertEquals(0, parentSplitPane.getActualLevel());
        assertEquals(Orientation.VERTICAL, parentSplitPane.getOrientation());
        assertNull(parentSplitPane.getParentSplitPane());
        assertEquals(Arrays.asList(test1), parentSplitPane.getDockingSplitPaneChildren());
        assertEquals(rootSplitPane, parentSplitPane);

        removeDockingArea(test1);

        assertNull(test1.getParentSplitPane());

        assertEquals(0, rootSplitPane.getPosition());
        assertEquals(0, rootSplitPane.getLevel());
        assertEquals(0, rootSplitPane.getActualLevel());
        assertEquals(Orientation.VERTICAL, rootSplitPane.getOrientation());
        assertNull(rootSplitPane.getParentSplitPane());
    }

    @Test
    public void testAddDockingArea2() {
        System.out.println("addDockingArea2");
        DockingAreaPane test1 = createDockingArea(20, TEST1, 10);
        rootSplitPane.addDockingArea(test1);

        DockingSplitPane parentSplitPane = test1.getParentSplitPane();
        assertNotNull(parentSplitPane);
        assertEquals(0, parentSplitPane.getPosition());
        assertEquals(0, parentSplitPane.getLevel());
        assertEquals(0, parentSplitPane.getActualLevel());
        assertEquals(Orientation.VERTICAL, parentSplitPane.getOrientation());
        assertNull(parentSplitPane.getParentSplitPane());
        assertEquals(Arrays.asList(test1), parentSplitPane.getDockingSplitPaneChildren());
        assertEquals(rootSplitPane, parentSplitPane);

        DockingAreaPane test2 = createDockingArea(40, TEST2, 10);
        rootSplitPane.addDockingArea(test2);

        parentSplitPane = test2.getParentSplitPane();
        assertNotNull(parentSplitPane);
        assertEquals(0, parentSplitPane.getPosition());
        assertEquals(0, parentSplitPane.getLevel());
        assertEquals(1, parentSplitPane.getActualLevel());
        assertEquals(Orientation.HORIZONTAL, parentSplitPane.getOrientation());
        assertNull(parentSplitPane.getParentSplitPane());
        assertEquals(Arrays.asList(test1, test2), parentSplitPane.getDockingSplitPaneChildren());
        assertEquals(rootSplitPane, parentSplitPane);

        parentSplitPane = test1.getParentSplitPane();
        assertNotNull(parentSplitPane);
        assertEquals(0, parentSplitPane.getPosition());
        assertEquals(0, parentSplitPane.getLevel());
        assertEquals(1, parentSplitPane.getActualLevel());
        assertEquals(Orientation.HORIZONTAL, parentSplitPane.getOrientation());
        assertNull(parentSplitPane.getParentSplitPane());
        assertEquals(Arrays.asList(test1, test2), parentSplitPane.getDockingSplitPaneChildren());
        assertEquals(rootSplitPane, parentSplitPane);

        removeDockingArea(test2);

        assertNull(test2.getParentSplitPane());

        parentSplitPane = test1.getParentSplitPane();
        assertNotNull(parentSplitPane);
        assertEquals(0, parentSplitPane.getPosition());
        assertEquals(0, parentSplitPane.getLevel());
        assertEquals(0, parentSplitPane.getActualLevel());
        assertEquals(Orientation.VERTICAL, parentSplitPane.getOrientation());
        assertNull(parentSplitPane.getParentSplitPane());
        assertEquals(Arrays.asList(test1), parentSplitPane.getDockingSplitPaneChildren());
    }

    @Test
    public void testAddDockingArea3() {
        System.out.println("addDockingArea3");
        DockingAreaPane test1 = createDockingArea(10, TEST1, 20);
        rootSplitPane.addDockingArea(test1);

        DockingSplitPane parentSplitPane = test1.getParentSplitPane();
        assertNotNull(parentSplitPane);
        assertEquals(0, parentSplitPane.getPosition());
        assertEquals(0, parentSplitPane.getLevel());
        assertEquals(0, parentSplitPane.getActualLevel());
        assertEquals(Orientation.VERTICAL, parentSplitPane.getOrientation());
        assertNull(parentSplitPane.getParentSplitPane());
        assertEquals(Arrays.asList(test1), parentSplitPane.getDockingSplitPaneChildren());


        DockingAreaPane test2 = createDockingArea(10, TEST2, 40);
        rootSplitPane.addDockingArea(test2);
        parentSplitPane = test2.getParentSplitPane();
        assertNotNull(parentSplitPane);
        assertEquals(0, parentSplitPane.getPosition());
        assertEquals(0, parentSplitPane.getLevel());
        assertEquals(0, parentSplitPane.getActualLevel());
        assertEquals(Orientation.VERTICAL, parentSplitPane.getOrientation());
        assertNull(parentSplitPane.getParentSplitPane());
        assertEquals(Arrays.asList(test1, test2), parentSplitPane.getDockingSplitPaneChildren());

        parentSplitPane = test1.getParentSplitPane();
        assertNotNull(parentSplitPane);
        assertEquals(0, parentSplitPane.getPosition());
        assertEquals(0, parentSplitPane.getLevel());
        assertEquals(0, parentSplitPane.getActualLevel());
        assertEquals(Orientation.VERTICAL, parentSplitPane.getOrientation());
        assertNull(parentSplitPane.getParentSplitPane());
        assertEquals(Arrays.asList(test1, test2), parentSplitPane.getDockingSplitPaneChildren());

        removeDockingArea(test2);

        assertNull(test2.getParentSplitPane());

        parentSplitPane = test1.getParentSplitPane();
        assertNotNull(parentSplitPane);
        assertEquals(0, parentSplitPane.getPosition());
        assertEquals(0, parentSplitPane.getLevel());
        assertEquals(0, parentSplitPane.getActualLevel());
        assertEquals(Orientation.VERTICAL, parentSplitPane.getOrientation());
        assertNull(parentSplitPane.getParentSplitPane());
        assertEquals(Arrays.asList(test1), parentSplitPane.getDockingSplitPaneChildren());

    }

    @Test
    public void testAddDockingArea4() {
        System.out.println("addDockingArea4");
        DockingAreaPane test1 = createDockingArea(20, TEST1, 10);
        rootSplitPane.addDockingArea(test1);

        DockingSplitPane parentSplitPane = test1.getParentSplitPane();
        assertNotNull(parentSplitPane);
        assertEquals(0, parentSplitPane.getPosition());
        assertEquals(0, parentSplitPane.getLevel());
        assertEquals(0, parentSplitPane.getActualLevel());
        assertEquals(Orientation.VERTICAL, parentSplitPane.getOrientation());
        assertNull(parentSplitPane.getParentSplitPane());
        assertEquals(Arrays.asList(test1), parentSplitPane.getDockingSplitPaneChildren());

        DockingAreaPane test2 = createDockingArea(40, TEST2, 10);
        rootSplitPane.addDockingArea(test2);
        parentSplitPane = test2.getParentSplitPane();
        assertNotNull(parentSplitPane);
        assertEquals(0, parentSplitPane.getPosition());
        assertEquals(0, parentSplitPane.getLevel());
        assertEquals(1, parentSplitPane.getActualLevel());
        assertEquals(Orientation.HORIZONTAL, parentSplitPane.getOrientation());
        assertNull(parentSplitPane.getParentSplitPane());
        assertEquals(Arrays.asList(test1, test2), parentSplitPane.getDockingSplitPaneChildren());

        // test1 
        parentSplitPane = test1.getParentSplitPane();
        assertNotNull(parentSplitPane);
        assertEquals(0, parentSplitPane.getPosition());
        assertEquals(0, parentSplitPane.getLevel());
        assertEquals(1, parentSplitPane.getActualLevel());
        assertEquals(Orientation.HORIZONTAL, parentSplitPane.getOrientation());
        assertNull(parentSplitPane.getParentSplitPane());
        assertEquals(Arrays.asList(test1, test2), parentSplitPane.getDockingSplitPaneChildren());

        DockingAreaPane test3 = createDockingArea(20, TEST3, 30);
        rootSplitPane.addDockingArea(test3);
        parentSplitPane = test3.getParentSplitPane();
        assertNotNull(parentSplitPane);
        assertEquals(0, parentSplitPane.getPosition());
        assertEquals(0, parentSplitPane.getLevel());
        assertEquals(0, parentSplitPane.getActualLevel());
        assertEquals(Orientation.VERTICAL, parentSplitPane.getOrientation());
        assertNull(parentSplitPane.getParentSplitPane());
        assertEquals(2, parentSplitPane.getDockingSplitPaneChildren().size());

        // test2
        parentSplitPane = test2.getParentSplitPane();
        assertNotNull(parentSplitPane);
        assertEquals(10, parentSplitPane.getPosition());
        assertEquals(1, parentSplitPane.getLevel());
        assertEquals(1, parentSplitPane.getActualLevel());
        assertEquals(Orientation.HORIZONTAL, parentSplitPane.getOrientation());
        assertEquals(Arrays.asList(test1, test2), parentSplitPane.getDockingSplitPaneChildren());

        DockingSplitPane parentParentSplitPane = parentSplitPane.getParentSplitPane();
        assertNotNull(parentParentSplitPane);
        assertEquals(0, parentParentSplitPane.getPosition());
        assertEquals(0, parentParentSplitPane.getLevel());
        assertEquals(0, parentParentSplitPane.getActualLevel());
        assertEquals(Orientation.VERTICAL, parentParentSplitPane.getOrientation());
        assertNull(parentParentSplitPane.getParentSplitPane());
        assertEquals(Arrays.asList(parentSplitPane, test3), parentParentSplitPane.getDockingSplitPaneChildren());

        // test1
        parentSplitPane = test1.getParentSplitPane();
        assertNotNull(parentSplitPane);
        assertEquals(10, parentSplitPane.getPosition());
        assertEquals(1, parentSplitPane.getLevel());
        assertEquals(1, parentSplitPane.getActualLevel());
        assertEquals(Orientation.HORIZONTAL, parentSplitPane.getOrientation());
        assertEquals(Arrays.asList(test1, test2), parentSplitPane.getDockingSplitPaneChildren());

        parentParentSplitPane = parentSplitPane.getParentSplitPane();
        assertNotNull(parentParentSplitPane);
        assertEquals(0, parentParentSplitPane.getPosition());
        assertEquals(0, parentParentSplitPane.getLevel());
        assertEquals(0, parentParentSplitPane.getActualLevel());
        assertEquals(Orientation.VERTICAL, parentParentSplitPane.getOrientation());
        assertNull(parentParentSplitPane.getParentSplitPane());
        assertEquals(Arrays.asList(parentSplitPane, test3), parentParentSplitPane.getDockingSplitPaneChildren());

        removeDockingArea(test3);

        assertNull(test3.getParentSplitPane());

        parentSplitPane = test2.getParentSplitPane();
        assertNotNull(parentSplitPane);
        assertEquals(0, parentSplitPane.getPosition());
        assertEquals(0, parentSplitPane.getLevel());
        assertEquals(1, parentSplitPane.getActualLevel());
        assertEquals(Orientation.HORIZONTAL, parentSplitPane.getOrientation());
        assertNull(parentSplitPane.getParentSplitPane());
        assertEquals(Arrays.asList(test1, test2), parentSplitPane.getDockingSplitPaneChildren());

        // test1 
        parentSplitPane = test1.getParentSplitPane();
        assertNotNull(parentSplitPane);
        assertEquals(0, parentSplitPane.getPosition());
        assertEquals(0, parentSplitPane.getLevel());
        assertEquals(1, parentSplitPane.getActualLevel());
        assertEquals(Orientation.HORIZONTAL, parentSplitPane.getOrientation());
        assertNull(parentSplitPane.getParentSplitPane());
        assertEquals(Arrays.asList(test1, test2), parentSplitPane.getDockingSplitPaneChildren());

        removeDockingArea(test1);

        assertNull(test1.getParentSplitPane());

        parentSplitPane = test2.getParentSplitPane();
        assertNotNull(parentSplitPane);
        assertEquals(0, parentSplitPane.getPosition());
        assertEquals(0, parentSplitPane.getLevel());
        assertEquals(0, parentSplitPane.getActualLevel());
        assertEquals(Orientation.VERTICAL, parentSplitPane.getOrientation());
        assertNull(parentSplitPane.getParentSplitPane());
        assertEquals(Arrays.asList(test2), parentSplitPane.getDockingSplitPaneChildren());
    }

    @Test
    public void testAddDockingArea5() {
        System.out.println("addDockingArea5");
        DockingAreaPane left = createDockingArea(20, LEFT, 20, 20);
        rootSplitPane.addDockingArea(left);
        DockingAreaPane right = createDockingArea(20, RIGHT, 20, 80);
        rootSplitPane.addDockingArea(right);
        DockingAreaPane top = createDockingArea(20, TOP, 20, 40, 20);
        rootSplitPane.addDockingArea(top);
        DockingAreaPane bottom = createDockingArea(20, BOTTOM, 20, 40, 80);
        rootSplitPane.addDockingArea(bottom);

        // left
        DockingSplitPane leftParentSplitPane = left.getParentSplitPane();
        assertNotNull(leftParentSplitPane);
        assertEquals(0, leftParentSplitPane.getPosition());
        assertEquals(0, leftParentSplitPane.getLevel());
        assertEquals(1, leftParentSplitPane.getActualLevel());
        assertEquals(Orientation.HORIZONTAL, leftParentSplitPane.getOrientation());
        assertNull(leftParentSplitPane.getParentSplitPane());
        assertEquals(3, leftParentSplitPane.getDockingSplitPaneChildren().size());

        // right
        DockingSplitPane rightParentSplitPane = right.getParentSplitPane();
        assertNotNull(rightParentSplitPane);
        assertEquals(0, rightParentSplitPane.getPosition());
        assertEquals(0, rightParentSplitPane.getLevel());
        assertEquals(1, rightParentSplitPane.getActualLevel());
        assertEquals(Orientation.HORIZONTAL, rightParentSplitPane.getOrientation());
        assertNull(rightParentSplitPane.getParentSplitPane());
        assertEquals(3, rightParentSplitPane.getDockingSplitPaneChildren().size());

        // top
        DockingSplitPane topParentSplitPane = top.getParentSplitPane();
        assertNotNull(topParentSplitPane);
        assertEquals(40, topParentSplitPane.getPosition());
        assertEquals(1, topParentSplitPane.getLevel());
        assertEquals(2, topParentSplitPane.getActualLevel());
        assertEquals(Orientation.VERTICAL, topParentSplitPane.getOrientation());
        assertEquals(2, topParentSplitPane.getDockingSplitPaneChildren().size());

        DockingSplitPane topParentParentSplitPane = topParentSplitPane.getParentSplitPane();
        assertNotNull(topParentParentSplitPane);
        assertEquals(0, topParentParentSplitPane.getPosition());
        assertEquals(0, topParentParentSplitPane.getLevel());
        assertEquals(1, topParentParentSplitPane.getActualLevel());
        assertEquals(Orientation.HORIZONTAL, topParentParentSplitPane.getOrientation());
        assertNull(topParentParentSplitPane.getParentSplitPane());
        assertEquals(Arrays.asList(left, topParentSplitPane, right),
                topParentParentSplitPane.getDockingSplitPaneChildren());


        //bottom
        DockingSplitPane bottomParentSplitPane = bottom.getParentSplitPane();
        assertNotNull(bottomParentSplitPane);
        assertEquals(40, bottomParentSplitPane.getPosition());
        assertEquals(1, bottomParentSplitPane.getLevel());
        assertEquals(2, bottomParentSplitPane.getActualLevel());
        assertEquals(Orientation.VERTICAL, bottomParentSplitPane.getOrientation());
        assertEquals(Arrays.asList(top, bottom), bottomParentSplitPane.getDockingSplitPaneChildren());

        DockingSplitPane bottomParentParentSplitPane = bottomParentSplitPane.getParentSplitPane();
        assertNotNull(bottomParentParentSplitPane);
        assertEquals(0, bottomParentParentSplitPane.getPosition());
        assertEquals(0, bottomParentParentSplitPane.getLevel());
        assertEquals(1, bottomParentParentSplitPane.getActualLevel());
        assertEquals(Orientation.HORIZONTAL, bottomParentParentSplitPane.getOrientation());
        assertNull(bottomParentParentSplitPane.getParentSplitPane());
        assertEquals(Arrays.asList(left, topParentSplitPane, right),
                bottomParentParentSplitPane.getDockingSplitPaneChildren());

        removeDockingArea(top);
        assertNull(top.getParentSplitPane());

        // left
        leftParentSplitPane = left.getParentSplitPane();
        assertNotNull(leftParentSplitPane);
        assertEquals(0, leftParentSplitPane.getPosition());
        assertEquals(0, leftParentSplitPane.getLevel());
        assertEquals(1, leftParentSplitPane.getActualLevel());
        assertEquals(Orientation.HORIZONTAL, leftParentSplitPane.getOrientation());
        assertNull(leftParentSplitPane.getParentSplitPane());
        assertEquals(3, leftParentSplitPane.getDockingSplitPaneChildren().size());

        // right
        rightParentSplitPane = right.getParentSplitPane();
        assertNotNull(rightParentSplitPane);
        assertEquals(0, rightParentSplitPane.getPosition());
        assertEquals(0, rightParentSplitPane.getLevel());
        assertEquals(1, rightParentSplitPane.getActualLevel());
        assertEquals(Orientation.HORIZONTAL, rightParentSplitPane.getOrientation());
        assertNull(rightParentSplitPane.getParentSplitPane());
        assertEquals(3, rightParentSplitPane.getDockingSplitPaneChildren().size());

        // bottom
        bottomParentSplitPane = bottom.getParentSplitPane();
        assertNotNull(bottomParentSplitPane);
        assertEquals(0, bottomParentSplitPane.getPosition());
        assertEquals(0, bottomParentSplitPane.getLevel());
        assertEquals(1, bottomParentSplitPane.getActualLevel());
        assertEquals(Orientation.HORIZONTAL, bottomParentSplitPane.getOrientation());
        assertNull(bottomParentSplitPane.getParentSplitPane());
        assertEquals(Arrays.asList(left, bottom, right),
                bottomParentSplitPane.getDockingSplitPaneChildren());

        removeDockingArea(left);
        assertNull(left.getParentSplitPane());

        // right
        rightParentSplitPane = right.getParentSplitPane();
        assertNotNull(rightParentSplitPane);
        assertEquals(0, rightParentSplitPane.getPosition());
        assertEquals(0, rightParentSplitPane.getLevel());
        assertEquals(1, rightParentSplitPane.getActualLevel());
        assertEquals(Orientation.HORIZONTAL, rightParentSplitPane.getOrientation());
        assertNull(rightParentSplitPane.getParentSplitPane());
        assertEquals(2, rightParentSplitPane.getDockingSplitPaneChildren().size());

        // bottom
        bottomParentSplitPane = bottom.getParentSplitPane();
        assertNotNull(bottomParentSplitPane);
        assertEquals(0, bottomParentSplitPane.getPosition());
        assertEquals(0, bottomParentSplitPane.getLevel());
        assertEquals(1, bottomParentSplitPane.getActualLevel());
        assertEquals(Orientation.HORIZONTAL, bottomParentSplitPane.getOrientation());
        assertNull(bottomParentSplitPane.getParentSplitPane());
        assertEquals(Arrays.asList(bottom, right),
                bottomParentSplitPane.getDockingSplitPaneChildren());

        removeDockingArea(right);
        assertNull(right.getParentSplitPane());

        // bottom
        bottomParentSplitPane = bottom.getParentSplitPane();
        assertNotNull(bottomParentSplitPane);
        assertEquals(0, bottomParentSplitPane.getPosition());
        assertEquals(0, bottomParentSplitPane.getLevel());
        assertEquals(0, bottomParentSplitPane.getActualLevel());
        assertEquals(Orientation.VERTICAL, bottomParentSplitPane.getOrientation());
        assertNull(bottomParentSplitPane.getParentSplitPane());
        assertEquals(Arrays.asList(bottom),
                bottomParentSplitPane.getDockingSplitPaneChildren());

    }

    @Test
    public void testAddDockingArea6() {
        System.out.println("addDockingArea6");
        DockingAreaPane center = createDockingArea(20, CENTER, 20, 40, 50);
        rootSplitPane.addDockingArea(center);
        DockingAreaPane right = createDockingArea(20, RIGHT, 20, 80);
        rootSplitPane.addDockingArea(right);
        DockingAreaPane top = createDockingArea(20, TOP, 20, 40, 20);
        rootSplitPane.addDockingArea(top);

        // center
        DockingSplitPane centerParentSplitPane = center.getParentSplitPane();
        assertNotNull(centerParentSplitPane);
        assertEquals(40, centerParentSplitPane.getPosition());
        assertEquals(1, centerParentSplitPane.getLevel());
        assertEquals(2, centerParentSplitPane.getActualLevel());
        assertEquals(Orientation.VERTICAL, centerParentSplitPane.getOrientation());
        assertEquals(Arrays.asList(top, center), centerParentSplitPane.getDockingSplitPaneChildren());

        DockingSplitPane centerParentParentSplitPane = centerParentSplitPane.getParentSplitPane();
        assertNotNull(centerParentParentSplitPane);
        assertEquals(0, centerParentParentSplitPane.getPosition());
        assertEquals(0, centerParentParentSplitPane.getLevel());
        assertEquals(1, centerParentParentSplitPane.getActualLevel());
        assertEquals(Orientation.HORIZONTAL, centerParentParentSplitPane.getOrientation());
        assertNull(centerParentParentSplitPane.getParentSplitPane());
        assertEquals(Arrays.asList(centerParentSplitPane, right),
                centerParentParentSplitPane.getDockingSplitPaneChildren());

        // right
        DockingSplitPane rightParentSplitPane = right.getParentSplitPane();
        assertNotNull(rightParentSplitPane);
        assertEquals(0, rightParentSplitPane.getPosition());
        assertEquals(0, rightParentSplitPane.getLevel());
        assertEquals(1, rightParentSplitPane.getActualLevel());
        assertEquals(Orientation.HORIZONTAL, rightParentSplitPane.getOrientation());
        assertNull(rightParentSplitPane.getParentSplitPane());
        assertEquals(2, rightParentSplitPane.getDockingSplitPaneChildren().size());

        // top
        DockingSplitPane topParentSplitPane = top.getParentSplitPane();
        assertNotNull(topParentSplitPane);
        assertEquals(40, topParentSplitPane.getPosition());
        assertEquals(1, topParentSplitPane.getLevel());
        assertEquals(2, topParentSplitPane.getActualLevel());
        assertEquals(Orientation.VERTICAL, topParentSplitPane.getOrientation());
        assertEquals(Arrays.asList(top, center), topParentSplitPane.getDockingSplitPaneChildren());

        DockingSplitPane topParentParentSplitPane = topParentSplitPane.getParentSplitPane();
        assertNotNull(topParentParentSplitPane);
        assertEquals(0, topParentParentSplitPane.getPosition());
        assertEquals(0, topParentParentSplitPane.getLevel());
        assertEquals(1, topParentParentSplitPane.getActualLevel());
        assertEquals(Orientation.HORIZONTAL, topParentParentSplitPane.getOrientation());
        assertNull(topParentParentSplitPane.getParentSplitPane());
        assertEquals(Arrays.asList(centerParentSplitPane, right),
                centerParentParentSplitPane.getDockingSplitPaneChildren());

        removeDockingArea(right);

        assertNull(right.getParentSplitPane());

        // center
        centerParentSplitPane = center.getParentSplitPane();
        assertNotNull(centerParentSplitPane);
        assertEquals(0, centerParentSplitPane.getPosition());
        assertEquals(0, centerParentSplitPane.getLevel());
        assertEquals(2, centerParentSplitPane.getActualLevel());
        assertEquals(Orientation.VERTICAL, centerParentSplitPane.getOrientation());
        assertEquals(Arrays.asList(top, center), centerParentSplitPane.getDockingSplitPaneChildren());
        assertNull(centerParentSplitPane.getParentSplitPane());

        // top
        topParentSplitPane = top.getParentSplitPane();
        assertNotNull(topParentSplitPane);
        assertEquals(0, topParentSplitPane.getPosition());
        assertEquals(0, topParentSplitPane.getLevel());
        assertEquals(2, topParentSplitPane.getActualLevel());
        assertEquals(Orientation.VERTICAL, topParentSplitPane.getOrientation());
        assertEquals(Arrays.asList(top, center), topParentSplitPane.getDockingSplitPaneChildren());
        assertNull(topParentSplitPane.getParentSplitPane());


        removeDockingArea(top);

        assertNull(top.getParentSplitPane());

        // center
        centerParentSplitPane = center.getParentSplitPane();
        assertNotNull(centerParentSplitPane);
        assertEquals(0, centerParentSplitPane.getPosition());
        assertEquals(0, centerParentSplitPane.getLevel());
        assertEquals(0, centerParentSplitPane.getActualLevel());
        assertEquals(Orientation.VERTICAL, centerParentSplitPane.getOrientation());
        assertEquals(Arrays.asList(center), centerParentSplitPane.getDockingSplitPaneChildren());
        assertNull(centerParentSplitPane.getParentSplitPane());

    }

    @Test
    public void testAddDockingArea7() {
        System.out.println("addDockingArea7");

        DockingAreaPane test1 = createDockingArea(90, TEST1, 20);
        rootSplitPane.addDockingArea(test1);
        DockingAreaPane test2 = createDockingArea(80, TEST2, 20, 50, 20);
        rootSplitPane.addDockingArea(test2);
        DockingAreaPane test3 = createDockingArea(100, TEST3, 20, 50, 20);
        rootSplitPane.addDockingArea(test3);
        DockingAreaPane test4 = createDockingArea(70, TEST4, 20, 50);
        rootSplitPane.addDockingArea(test4);

        DockingSplitPane test1ParentSplitPane = test1.getParentSplitPane();
        assertNotNull(test1ParentSplitPane);
        assertEquals(0, test1ParentSplitPane.getPosition());
        assertEquals(0, test1ParentSplitPane.getLevel());
        assertEquals(1, test1ParentSplitPane.getActualLevel());
        assertEquals(Orientation.HORIZONTAL, test1ParentSplitPane.getOrientation());
        assertNull(test1ParentSplitPane.getParentSplitPane());
//        assertEquals(Arrays.asList(centerParentSplitPane, right),
//                centerParentParentSplitPane.getDockingSplitPaneChildren());

        DockingSplitPane test2ParentSplitPane = test2.getParentSplitPane();
        assertNotNull(test2ParentSplitPane);
        assertEquals(20, test2ParentSplitPane.getPosition());
        assertEquals(2, test2ParentSplitPane.getLevel());
        assertEquals(3, test2ParentSplitPane.getActualLevel());
        assertEquals(Orientation.HORIZONTAL, test2ParentSplitPane.getOrientation());
        assertNotNull(test2ParentSplitPane.getParentSplitPane());

        DockingSplitPane test3ParentSplitPane = test3.getParentSplitPane();
        assertEquals(test2ParentSplitPane, test3ParentSplitPane);

        DockingSplitPane test4ParentSplitPane = test4.getParentSplitPane();
        assertNotNull(test4ParentSplitPane);
        assertEquals(50, test4ParentSplitPane.getPosition());
        assertEquals(1, test4ParentSplitPane.getLevel());
        assertEquals(2, test4ParentSplitPane.getActualLevel());
        assertEquals(Orientation.VERTICAL, test4ParentSplitPane.getOrientation());
        assertEquals(test1ParentSplitPane, test4ParentSplitPane.getParentSplitPane());
        assertEquals(test2ParentSplitPane.getParentSplitPane(), test4ParentSplitPane);

        removeDockingArea(test4);

        assertNull(test4.getParentSplitPane());

        test1ParentSplitPane = test1.getParentSplitPane();
        assertNotNull(test1ParentSplitPane);
        assertEquals(0, test1ParentSplitPane.getPosition());
        assertEquals(0, test1ParentSplitPane.getLevel());
        assertEquals(1, test1ParentSplitPane.getActualLevel());
        assertEquals(Orientation.HORIZONTAL, test1ParentSplitPane.getOrientation());
        assertNull(test1ParentSplitPane.getParentSplitPane());
//        assertEquals(Arrays.asList(centerParentSplitPane, right),
//                centerParentParentSplitPane.getDockingSplitPaneChildren());

        test2ParentSplitPane = test2.getParentSplitPane();
        assertNotNull(test2ParentSplitPane);
        assertEquals(50, test2ParentSplitPane.getPosition());
        assertEquals(1, test2ParentSplitPane.getLevel());
        assertEquals(3, test2ParentSplitPane.getActualLevel());
        assertEquals(Orientation.HORIZONTAL, test2ParentSplitPane.getOrientation());
        assertNotNull(test2ParentSplitPane.getParentSplitPane());

        test3ParentSplitPane = test3.getParentSplitPane();
        assertEquals(test2ParentSplitPane, test3ParentSplitPane);
        assertEquals(test1ParentSplitPane, test3ParentSplitPane.getParentSplitPane());

        removeDockingArea(test2);

        assertNull(test2.getParentSplitPane());

        test1ParentSplitPane = test1.getParentSplitPane();
        assertNotNull(test1ParentSplitPane);
        assertEquals(0, test1ParentSplitPane.getPosition());
        assertEquals(0, test1ParentSplitPane.getLevel());
        assertEquals(1, test1ParentSplitPane.getActualLevel());
        assertEquals(Orientation.HORIZONTAL, test1ParentSplitPane.getOrientation());
        assertNull(test1ParentSplitPane.getParentSplitPane());
//        assertEquals(Arrays.asList(centerParentSplitPane, right),
//                centerParentParentSplitPane.getDockingSplitPaneChildren());

        test3ParentSplitPane = test3.getParentSplitPane();
        assertNotNull(test3ParentSplitPane);
        assertEquals(0, test3ParentSplitPane.getPosition());
        assertEquals(0, test3ParentSplitPane.getLevel());
        assertEquals(1, test3ParentSplitPane.getActualLevel());
        assertEquals(Orientation.HORIZONTAL, test3ParentSplitPane.getOrientation());
        assertNull(test3ParentSplitPane.getParentSplitPane());

        removeDockingArea(test1);

        assertNull(test1.getParentSplitPane());

        test3ParentSplitPane = test3.getParentSplitPane();
        assertNotNull(test3ParentSplitPane);
        assertEquals(0, test3ParentSplitPane.getPosition());
        assertEquals(0, test3ParentSplitPane.getLevel());
        assertEquals(0, test3ParentSplitPane.getActualLevel());
        assertEquals(Orientation.VERTICAL, test3ParentSplitPane.getOrientation());
        assertNull(test3ParentSplitPane.getParentSplitPane());

    }

    @Test
    public void testAddDockingArea8() {
        System.out.println("addDockingArea8");
        DockingAreaPane test1 = createDockingArea(20, TEST1, 10);
        rootSplitPane.addDockingArea(test1);

        DockingAreaPane test2 = createDockingArea(40, TEST2, 10);
        rootSplitPane.addDockingArea(test2);

        DockingAreaPane test3 = createDockingArea(10, TEST3, 20);
        rootSplitPane.addDockingArea(test3);

        DockingSplitPane parentSplitPane1 = test1.getParentSplitPane();
        assertNotNull(parentSplitPane1);
        assertEquals(10, parentSplitPane1.getPosition());
        assertEquals(1, parentSplitPane1.getLevel());
        assertEquals(1, parentSplitPane1.getActualLevel());
        assertEquals(Orientation.HORIZONTAL, parentSplitPane1.getOrientation());
        assertEquals(rootSplitPane, parentSplitPane1.getParentSplitPane());

        DockingSplitPane parentSplitPane2 = test2.getParentSplitPane();
        assertEquals(parentSplitPane1, parentSplitPane2);

        DockingSplitPane parentSplitPane3 = test3.getParentSplitPane();
        assertNotNull(parentSplitPane3);
        assertEquals(0, parentSplitPane3.getPosition());
        assertEquals(0, parentSplitPane3.getLevel());
        assertEquals(0, parentSplitPane3.getActualLevel());
        assertEquals(Orientation.VERTICAL, parentSplitPane3.getOrientation());
        assertEquals(rootSplitPane, parentSplitPane3);

        removeDockingArea(test2);

        assertNull(test2.getParentSplitPane());

        parentSplitPane1 = test1.getParentSplitPane();
        assertNotNull(parentSplitPane1);
        assertEquals(0, parentSplitPane1.getPosition());
        assertEquals(0, parentSplitPane1.getLevel());
        assertEquals(0, parentSplitPane1.getActualLevel());
        assertEquals(Orientation.VERTICAL, parentSplitPane1.getOrientation());
        assertEquals(rootSplitPane, parentSplitPane1);

        parentSplitPane3 = test3.getParentSplitPane();
        assertNotNull(parentSplitPane3);
        assertEquals(0, parentSplitPane3.getPosition());
        assertEquals(0, parentSplitPane3.getLevel());
        assertEquals(0, parentSplitPane3.getActualLevel());
        assertEquals(Orientation.VERTICAL, parentSplitPane3.getOrientation());
        assertEquals(rootSplitPane, parentSplitPane3);


        removeDockingArea(test1);

        assertNull(test1.getParentSplitPane());

        parentSplitPane3 = test3.getParentSplitPane();
        assertNotNull(parentSplitPane3);
        assertEquals(0, parentSplitPane3.getPosition());
        assertEquals(0, parentSplitPane3.getLevel());
        assertEquals(0, parentSplitPane3.getActualLevel());
        assertEquals(Orientation.VERTICAL, parentSplitPane3.getOrientation());
        assertEquals(rootSplitPane, parentSplitPane3);

    }

    @Test
    public void testAddDockingArea9() {
        System.out.println("addDockingArea9");
        DockingAreaPane test1 = createDockingArea(20, TEST1, 10);
        rootSplitPane.addDockingArea(test1);

        DockingSplitPane parentSplitPane = test1.getParentSplitPane();
        assertNotNull(parentSplitPane);
        assertEquals(0, parentSplitPane.getPosition());
        assertEquals(0, parentSplitPane.getLevel());
        assertEquals(0, parentSplitPane.getActualLevel());
        assertEquals(Orientation.VERTICAL, parentSplitPane.getOrientation());
        assertNull(parentSplitPane.getParentSplitPane());
        assertEquals(Arrays.asList(test1), parentSplitPane.getDockingSplitPaneChildren());
        assertEquals(rootSplitPane, parentSplitPane);

        DockingAreaPane test2 = createDockingArea(40, TEST2, 10);
        rootSplitPane.addDockingArea(test2);

        parentSplitPane = test2.getParentSplitPane();
        assertNotNull(parentSplitPane);
        assertEquals(0, parentSplitPane.getPosition());
        assertEquals(0, parentSplitPane.getLevel());
        assertEquals(1, parentSplitPane.getActualLevel());
        assertEquals(Orientation.HORIZONTAL, parentSplitPane.getOrientation());
        assertNull(parentSplitPane.getParentSplitPane());
        assertEquals(Arrays.asList(test1, test2), parentSplitPane.getDockingSplitPaneChildren());
        assertEquals(rootSplitPane, parentSplitPane);

        parentSplitPane = test1.getParentSplitPane();
        assertNotNull(parentSplitPane);
        assertEquals(0, parentSplitPane.getPosition());
        assertEquals(0, parentSplitPane.getLevel());
        assertEquals(1, parentSplitPane.getActualLevel());
        assertEquals(Orientation.HORIZONTAL, parentSplitPane.getOrientation());
        assertNull(parentSplitPane.getParentSplitPane());
        assertEquals(Arrays.asList(test1, test2), parentSplitPane.getDockingSplitPaneChildren());
        assertEquals(rootSplitPane, parentSplitPane);

        removeDockingArea(test2);
        assertNull(test2.getParentSplitPane());

        parentSplitPane = test1.getParentSplitPane();
        assertNotNull(parentSplitPane);
        assertEquals(0, parentSplitPane.getPosition());
        assertEquals(0, parentSplitPane.getLevel());
        assertEquals(0, parentSplitPane.getActualLevel());
        assertEquals(Orientation.VERTICAL, parentSplitPane.getOrientation());
        assertNull(parentSplitPane.getParentSplitPane());
        assertEquals(Arrays.asList(test1), parentSplitPane.getDockingSplitPaneChildren());
    }

    /**
     * Test of isEmpty method, of class DockingSplitPane.
     */
    @Test
    @Ignore
    public void testIsEmpty() {
        System.out.println("isEmpty");
        DockingSplitPane instance = null;
        boolean expResult = false;
        boolean result = instance.isEmpty();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeDockingArea method, of class DockingSplitPane.
     */
    @Test
    @Ignore
    public void testRemoveDockingArea() {
        System.out.println("removeDockingArea");
        String areaId = "";
        DockingSplitPane instance = null;
//        instance.removeDockingArea(areaId);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testContainsAnyDockingAreas1() throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException {
        System.out.println("containsAnyDockingAreas1");

        assertFalse(rootSplitPane.containsAnyDockingAreas());
        assertTrue(rootSplitPane.isEmpty());

        DockingAreaPane test1 = createDockingArea(90, TEST1, 20);
        rootSplitPane.addDockingArea(test1);

        assertTrue(rootSplitPane.containsAnyDockingAreas());
        assertFalse(rootSplitPane.isEmpty());

        DockingAreaPane test2 = createDockingArea(30, TEST1, 20, 50);
        rootSplitPane.addDockingArea(test2);

        assertTrue(rootSplitPane.containsAnyDockingAreas());
        assertFalse(rootSplitPane.isEmpty());

        DockingAreaPane test3 = createDockingArea(60, TEST1, 20, 50);
        rootSplitPane.addDockingArea(test3);

        assertTrue(rootSplitPane.containsAnyDockingAreas());
        assertFalse(rootSplitPane.isEmpty());
    }

    @Test
    public void testContainsAnyDockingAreas2() throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException {
        System.out.println("containsAnyDockingAreas2");

        assertFalse(rootSplitPane.containsAnyDockingAreas());
        assertTrue(rootSplitPane.isEmpty());

        DockingAreaPane test1 = createDockingArea(90, TEST1, 20);
        rootSplitPane.addDockingArea(test1);

        assertTrue(rootSplitPane.containsAnyDockingAreas());
        assertFalse(rootSplitPane.isEmpty());

        DockingSplitPane splitPane = addEmptySplitPane(rootSplitPane, 50, 1, SplitLevel.valueOf(2));

        assertTrue(rootSplitPane.containsAnyDockingAreas());
        assertFalse(rootSplitPane.isEmpty());

        assertFalse(splitPane.containsAnyDockingAreas());
        assertTrue(splitPane.isEmpty());
    }

    @Test
    public void testContainsAnyDockingAreas3() throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException {
        System.out.println("containsAnyDockingAreas3");

        assertFalse(rootSplitPane.containsAnyDockingAreas());
        assertTrue(rootSplitPane.isEmpty());

        DockingSplitPane splitPane = addEmptySplitPane(rootSplitPane, 50, 1, SplitLevel.valueOf(2));

        assertFalse(rootSplitPane.containsAnyDockingAreas());
        assertFalse(rootSplitPane.isEmpty());

        assertFalse(splitPane.containsAnyDockingAreas());
        assertTrue(splitPane.isEmpty());

        DockingAreaPane test1 = createDockingArea(90, TEST1);
        addDockingArea(splitPane, test1.getPosition(), test1);

        assertTrue(rootSplitPane.containsAnyDockingAreas());
        assertFalse(rootSplitPane.isEmpty());

        assertTrue(splitPane.containsAnyDockingAreas());
        assertFalse(splitPane.isEmpty());
    }

    private DockingAreaPane createDockingArea(int position, String id, Integer... path) {
        DockingAreaPane dockingAreaPane = new DockingAreaPane(id, position, false);
        rootManager.addDockingArea(Arrays.asList(path), dockingAreaPane);

        DockablePane dockablePane = new DockablePane();
        dockingAreaPane.addDockable(new PositionableAdapter<>(dockablePane, 10));

        return dockingAreaPane;
    }

    private DockingSplitPane addEmptySplitPane(DockingSplitPane parentSplitPane, int position, int level,
            SplitLevel actualLevel) throws
            NoSuchMethodException, SecurityException, IllegalArgumentException,
            IllegalAccessException, InvocationTargetException {
        Method getSplitPaneMethod = DockingSplitPane.class.getDeclaredMethod("getSplitPane",
                int.class, int.class, SplitLevel.class, List.class);
        getSplitPaneMethod.setAccessible(true);
        DockingSplitPane splitPane = (DockingSplitPane) getSplitPaneMethod.invoke(parentSplitPane, position, level,
                actualLevel, new ArrayList<>());
        return splitPane;
    }

    private void addDockingArea(DockingSplitPane parentSplitPane, int position,
            DockingAreaPane dockingAreaPane) throws NoSuchMethodException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        Method addDockingAreaMethod = DockingSplitPane.class.getDeclaredMethod("addDockingArea",
                int.class, DockingAreaPane.class);
        addDockingAreaMethod.setAccessible(true);
        addDockingAreaMethod.invoke(parentSplitPane, position, dockingAreaPane);
    }

    private void removeDockingArea(DockingAreaPane dockingArea) {
        while (!dockingArea.getDockables().isEmpty()) {
            dockingArea.removeDockable(0);
        }
        rootSplitPane.removeDockingArea(dockingArea);
    }
}
