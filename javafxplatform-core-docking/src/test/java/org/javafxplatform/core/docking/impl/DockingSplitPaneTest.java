/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.docking.impl;

import java.util.Arrays;
import javafx.geometry.Orientation;
import org.javafxplatform.core.docking.DockablePane;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.richclientplatform.core.lib.util.PositionableAdapter;

/**
 *
 * @author puce
 */
public class DockingSplitPaneTest {

    private static final String TEST1 = "test1";
    private static final String TEST2 = "test2";
    private static final String TEST3 = "test3";
    private static final String LEFT = "left";
    private static final String RIGHT = "right";
    private static final String TOP = "top";
    private static final String BOTTOM = "bottom";
    private DockingSplitPane rootSplitPane;
    private DockingAreaManager rootManager;

    public DockingSplitPaneTest() {
    }

    @Before
    public void setUp() {
        rootSplitPane = new DockingSplitPane(0, 0, Orientation.VERTICAL);
        rootManager = new DockingAreaManager(null, null, 0, Orientation.VERTICAL);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of addDockingArea method, of class DockingSplitPane.
     */
    @Test
    public void testAddDockingArea1() {
        System.out.println("addDockingArea1");

        DockingAreaPane test1 = createDockingArea(10, TEST1, 20);
        rootSplitPane.addDockingArea(test1);
        DockingSplitPane parentSplitPane = test1.getParentSplitPane();
        assertNotNull(parentSplitPane);
        assertEquals(0, parentSplitPane.getPosition());
        assertEquals(0, parentSplitPane.getActualLevel());
        assertEquals(Orientation.VERTICAL, parentSplitPane.getOrientation());
        assertNull(parentSplitPane.getParentSplitPane());
    }

    @Test
    public void testAddDockingArea2() {
        System.out.println("addDockingArea2");
        DockingAreaPane test1 = createDockingArea(20, TEST1, 10);
        rootSplitPane.addDockingArea(test1);

        DockingSplitPane parentSplitPane = test1.getParentSplitPane();
        assertNotNull(parentSplitPane);
        assertEquals(0, parentSplitPane.getPosition());
        assertEquals(0, parentSplitPane.getActualLevel());
        assertEquals(Orientation.VERTICAL, parentSplitPane.getOrientation());
        assertNull(parentSplitPane.getParentSplitPane());

        DockingAreaPane test2 = createDockingArea(40, TEST2, 10);
        rootSplitPane.addDockingArea(test2);
        parentSplitPane = test2.getParentSplitPane();
        assertNotNull(parentSplitPane);
        assertEquals(0, parentSplitPane.getPosition());
        assertEquals(1, parentSplitPane.getActualLevel());
        assertEquals(Orientation.HORIZONTAL, parentSplitPane.getOrientation());
        assertNull(parentSplitPane.getParentSplitPane());

        parentSplitPane = test1.getParentSplitPane();
        assertNotNull(parentSplitPane);
        assertEquals(0, parentSplitPane.getPosition());
        assertEquals(1, parentSplitPane.getActualLevel());
        assertEquals(Orientation.HORIZONTAL, parentSplitPane.getOrientation());
        assertNull(parentSplitPane.getParentSplitPane());


    }

    @Test
    public void testAddDockingArea3() {
        System.out.println("addDockingArea3");
        DockingAreaPane test1 = createDockingArea(10, TEST1, 20);
        rootSplitPane.addDockingArea(test1);

        DockingSplitPane parentSplitPane = test1.getParentSplitPane();
        assertNotNull(parentSplitPane);
        assertEquals(0, parentSplitPane.getPosition());
        assertEquals(0, parentSplitPane.getActualLevel());
        assertEquals(Orientation.VERTICAL, parentSplitPane.getOrientation());
        assertNull(parentSplitPane.getParentSplitPane());


        DockingAreaPane test2 = createDockingArea(10, TEST2, 40);
        rootSplitPane.addDockingArea(test2);
        parentSplitPane = test2.getParentSplitPane();
        assertNotNull(parentSplitPane);
        assertEquals(0, parentSplitPane.getPosition());
        assertEquals(0, parentSplitPane.getActualLevel());
        assertEquals(Orientation.VERTICAL, parentSplitPane.getOrientation());
        assertNull(parentSplitPane.getParentSplitPane());

        parentSplitPane = test1.getParentSplitPane();
        assertNotNull(parentSplitPane);
        assertEquals(0, parentSplitPane.getPosition());
        assertEquals(0, parentSplitPane.getActualLevel());
        assertEquals(Orientation.VERTICAL, parentSplitPane.getOrientation());
        assertNull(parentSplitPane.getParentSplitPane());

    }

    @Test
    public void testAddDockingArea4() {
        System.out.println("addDockingArea4");
        DockingAreaPane test1 = createDockingArea(20, TEST1, 10);
        rootSplitPane.addDockingArea(test1);

        DockingSplitPane parentSplitPane = test1.getParentSplitPane();
        assertNotNull(parentSplitPane);
        assertEquals(0, parentSplitPane.getPosition());
        assertEquals(0, parentSplitPane.getActualLevel());
        assertEquals(Orientation.VERTICAL, parentSplitPane.getOrientation());
        assertNull(parentSplitPane.getParentSplitPane());

        DockingAreaPane test2 = createDockingArea(40, TEST2, 10);
        rootSplitPane.addDockingArea(test2);
        parentSplitPane = test2.getParentSplitPane();
        assertNotNull(parentSplitPane);
        assertEquals(0, parentSplitPane.getPosition());
        assertEquals(1, parentSplitPane.getActualLevel());
        assertEquals(Orientation.HORIZONTAL, parentSplitPane.getOrientation());
        assertNull(parentSplitPane.getParentSplitPane());

        // test1 
        parentSplitPane = test1.getParentSplitPane();
        assertNotNull(parentSplitPane);
        assertEquals(0, parentSplitPane.getPosition());
        assertEquals(1, parentSplitPane.getActualLevel());
        assertEquals(Orientation.HORIZONTAL, parentSplitPane.getOrientation());
        assertNull(parentSplitPane.getParentSplitPane());

        DockingAreaPane test3 = createDockingArea(20, TEST3, 30);
        rootSplitPane.addDockingArea(test3);
        parentSplitPane = test3.getParentSplitPane();
        assertNotNull(parentSplitPane);
        assertEquals(0, parentSplitPane.getPosition());
        assertEquals(0, parentSplitPane.getActualLevel());
        assertEquals(Orientation.VERTICAL, parentSplitPane.getOrientation());
        assertNull(parentSplitPane.getParentSplitPane());

        // test2
        parentSplitPane = test2.getParentSplitPane();
        assertNotNull(parentSplitPane);
        assertEquals(10, parentSplitPane.getPosition());
        assertEquals(1, parentSplitPane.getActualLevel());
        assertEquals(Orientation.HORIZONTAL, parentSplitPane.getOrientation());

        DockingSplitPane parentParentSplitPane = parentSplitPane.getParentSplitPane();
        assertNotNull(parentParentSplitPane);
        assertEquals(0, parentParentSplitPane.getPosition());
        assertEquals(0, parentParentSplitPane.getActualLevel());
        assertEquals(Orientation.VERTICAL, parentParentSplitPane.getOrientation());
        assertNull(parentParentSplitPane.getParentSplitPane());

        // test1
        parentSplitPane = test1.getParentSplitPane();
        assertNotNull(parentSplitPane);
        assertEquals(10, parentSplitPane.getPosition());
        assertEquals(1, parentSplitPane.getActualLevel());
        assertEquals(Orientation.HORIZONTAL, parentSplitPane.getOrientation());

        parentParentSplitPane = parentSplitPane.getParentSplitPane();
        assertNotNull(parentParentSplitPane);
        assertEquals(0, parentParentSplitPane.getPosition());
        assertEquals(0, parentParentSplitPane.getActualLevel());
        assertEquals(Orientation.VERTICAL, parentParentSplitPane.getOrientation());
        assertNull(parentParentSplitPane.getParentSplitPane());
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
        assertEquals(1, leftParentSplitPane.getActualLevel());
        assertEquals(Orientation.HORIZONTAL, leftParentSplitPane.getOrientation());
        assertNull(leftParentSplitPane.getParentSplitPane());

        // right
        DockingSplitPane rightParentSplitPane = right.getParentSplitPane();
        assertNotNull(rightParentSplitPane);
        assertEquals(0, rightParentSplitPane.getPosition());
        assertEquals(1, rightParentSplitPane.getActualLevel());
        assertEquals(Orientation.HORIZONTAL, rightParentSplitPane.getOrientation());
        assertNull(rightParentSplitPane.getParentSplitPane());

        // top
        DockingSplitPane topParentSplitPane = top.getParentSplitPane();
        assertNotNull(topParentSplitPane);
        assertEquals(40, topParentSplitPane.getPosition());
        assertEquals(2, topParentSplitPane.getActualLevel());
        assertEquals(Orientation.VERTICAL, topParentSplitPane.getOrientation());

        DockingSplitPane topParentParentSplitPane = topParentSplitPane.getParentSplitPane();
        assertNotNull(topParentParentSplitPane);
        assertEquals(0, topParentParentSplitPane.getPosition());
        assertEquals(1, topParentParentSplitPane.getActualLevel());
        assertEquals(Orientation.HORIZONTAL, topParentParentSplitPane.getOrientation());
        assertNull(topParentParentSplitPane.getParentSplitPane());


        //bottom
        DockingSplitPane bottomParentSplitPane = bottom.getParentSplitPane();
        assertNotNull(bottomParentSplitPane);
        assertEquals(40, bottomParentSplitPane.getPosition());
        assertEquals(2, bottomParentSplitPane.getActualLevel());
        assertEquals(Orientation.VERTICAL, bottomParentSplitPane.getOrientation());

        DockingSplitPane bottomParentParentSplitPane = bottomParentSplitPane.getParentSplitPane();
        assertNotNull(bottomParentParentSplitPane);
        assertEquals(0, bottomParentParentSplitPane.getPosition());
        assertEquals(1, bottomParentParentSplitPane.getActualLevel());
        assertEquals(Orientation.HORIZONTAL, bottomParentParentSplitPane.getOrientation());
        assertNull(bottomParentParentSplitPane.getParentSplitPane());

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

    private DockingAreaPane createDockingArea(int position, String id, Integer... path) {
        DockingAreaPane dockingAreaPane = new DockingAreaPane(id, position, false);
        rootManager.addDockingArea(Arrays.asList(path), dockingAreaPane);

        DockablePane dockablePane = new DockablePane();
        dockingAreaPane.addDockable(new PositionableAdapter<>(dockablePane, 10));

        return dockingAreaPane;
    }
}
