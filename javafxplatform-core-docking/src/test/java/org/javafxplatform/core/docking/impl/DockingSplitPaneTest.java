/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.docking.impl;

import java.util.Arrays;
import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javax.xml.bind.DatatypeConverter;
import org.javafxplatform.core.docking.DockablePane;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.richclientplatform.core.lib.util.PositionableAdapter;

/**
 *
 * @author puce
 */
public class DockingSplitPaneTest {

    private static final String TEST1 = "test1";
    private static final String TEST2 = "test2";
    private static final String TEST3 = "test3";
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
        assertEquals(0, parentSplitPane.getActualLevel());
        assertEquals(Orientation.VERTICAL, parentSplitPane.getOrientation());
        assertNull(parentSplitPane.getParentSplitPane());

        DockingAreaPane test2 = createDockingArea(40, TEST2, 10);
        rootSplitPane.addDockingArea(test2);
        parentSplitPane = test2.getParentSplitPane();
        assertNotNull(parentSplitPane);
        assertEquals(1, parentSplitPane.getActualLevel());
        assertEquals(Orientation.HORIZONTAL, parentSplitPane.getOrientation());
        assertNull(parentSplitPane.getParentSplitPane());

        parentSplitPane = test1.getParentSplitPane();
        assertNotNull(parentSplitPane);
        assertEquals(1, parentSplitPane.getActualLevel());
        assertEquals(Orientation.HORIZONTAL, parentSplitPane.getOrientation());
        assertNull(parentSplitPane.getParentSplitPane());
        

    }

    @Test
    @Ignore
    public void testAddDockingArea3() {
        System.out.println("addDockingArea3");
        DockingAreaPane test1 = createDockingArea(10, TEST1, 20);



        DockingAreaPane test2 = createDockingArea(10, TEST2, 40);


    }

    @Test
    @Ignore
    public void testAddDockingArea4() {
        System.out.println("addDockingArea4");
        DockingAreaPane test1 = createDockingArea(20, TEST1, 10);


        DockingAreaPane test2 = createDockingArea(40, TEST2, 10);


        DockingAreaPane test3 = createDockingArea(20, TEST3, 30);


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
