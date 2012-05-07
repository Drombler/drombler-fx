/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.docking.impl;

import java.util.Arrays;
import java.util.List;
import javafx.geometry.Orientation;
import org.javafxplatform.core.docking.DockablePane;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.richclientplatform.core.lib.util.PositionableAdapter;

/**
 *
 * @author puce
 */
public class DockingPaneTest {

    private static final String TEST1 = "test1";
    private static final String TEST2 = "test2";
    private static final String TEST3 = "test3";
    private static final String LEFT = "left";
    private static final String RIGHT = "right";
    private static final String TOP = "top";
    private static final String BOTTOM = "bottom";
    private DockingPane dockingPane;

    public DockingPaneTest() {
    }

    @Before
    public void setUp() {
        dockingPane = new DockingPane();
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of addDockingArea method, of class DockingPane.
     */
    @Test
    public void testAddDockingArea1() {
        System.out.println("addDockingArea1");

        addDockingArea(20, TEST1, 10);

        List<ShortPathPart> path = getDockingAreaShortPath(TEST1);
        assertEquals(Arrays.asList(new ShortPathPart(10, 0, Orientation.VERTICAL)), path);
    }

    @Test
    public void testAddDockingArea2() {
        System.out.println("addDockingArea2");
        addDockingArea(20, TEST1, 10);

        List<ShortPathPart> pathTest1 = getDockingAreaShortPath(TEST1);
        assertEquals(Arrays.asList(new ShortPathPart(10, 0, Orientation.VERTICAL)), pathTest1);

        addDockingArea(40, TEST2, 10);

        List<ShortPathPart> pathTest2 = getDockingAreaShortPath(TEST2);
        assertEquals(Arrays.asList(new ShortPathPart(40, 1, Orientation.HORIZONTAL)), pathTest2);

        pathTest1 = getDockingAreaShortPath(TEST1);
        assertEquals(Arrays.asList(new ShortPathPart(20, 1, Orientation.HORIZONTAL)), pathTest1);
    }

    @Test
    public void testAddDockingArea3() {
        System.out.println("addDockingArea3");
        addDockingArea(10, TEST1, 20);

        List<ShortPathPart> pathTest1 = getDockingAreaShortPath(TEST1);
        assertEquals(Arrays.asList(new ShortPathPart(20, 0, Orientation.VERTICAL)), pathTest1);

        addDockingArea(10, TEST2, 40);

        List<ShortPathPart> pathTest2 = getDockingAreaShortPath(TEST2);
        assertEquals(Arrays.asList(new ShortPathPart(40, 0, Orientation.VERTICAL)), pathTest2);

        pathTest1 = getDockingAreaShortPath(TEST1);
        assertEquals(Arrays.asList(new ShortPathPart(20, 0, Orientation.VERTICAL)), pathTest1);
    }

    @Test
    public void testAddDockingArea4() {
        System.out.println("addDockingArea4");
        addDockingArea(20, TEST1, 10);

        List<ShortPathPart> pathTest1 = getDockingAreaShortPath(TEST1);
        assertEquals(Arrays.asList(new ShortPathPart(10, 0, Orientation.VERTICAL)), pathTest1);

        addDockingArea(40, TEST2, 10);

        List<ShortPathPart> pathTest2 = getDockingAreaShortPath(TEST2);
        assertEquals(Arrays.asList(new ShortPathPart(40, 1, Orientation.HORIZONTAL)), pathTest2);

        pathTest1 = getDockingAreaShortPath(TEST1);
        assertEquals(Arrays.asList(new ShortPathPart(20, 1, Orientation.HORIZONTAL)), pathTest1);

        addDockingArea(20, TEST3, 30);

        List<ShortPathPart> pathTest3 = getDockingAreaShortPath(TEST3);
        assertEquals(Arrays.asList(new ShortPathPart(30, 0, Orientation.VERTICAL)), pathTest3);

        pathTest1 = getDockingAreaShortPath(TEST1);
        assertEquals(Arrays.asList(
                new ShortPathPart(10, 0, Orientation.VERTICAL),
                new ShortPathPart(20, 1, Orientation.HORIZONTAL)), pathTest1);

        pathTest2 = getDockingAreaShortPath(TEST2);
        assertEquals(Arrays.asList(
                new ShortPathPart(10, 0, Orientation.VERTICAL),
                new ShortPathPart(40, 1, Orientation.HORIZONTAL)), pathTest2);
    }

    @Test
    public void testAddDockingArea5() {
        System.out.println("addDockingArea5");
        addDockingArea(20, LEFT, 20, 20);
        addDockingArea(20, RIGHT, 20, 80);
        addDockingArea(20, TOP, 20, 40, 20);
        addDockingArea(20, BOTTOM, 20, 40, 80);

        List<ShortPathPart> leftPath = getDockingAreaShortPath(LEFT);
        assertEquals(Arrays.asList(
                new ShortPathPart(20, 1, Orientation.HORIZONTAL)), leftPath);

        List<ShortPathPart> rightPath = getDockingAreaShortPath(RIGHT);
        assertEquals(Arrays.asList(
                new ShortPathPart(80, 1, Orientation.HORIZONTAL)), rightPath);

        List<ShortPathPart> topPath = getDockingAreaShortPath(TOP);
        assertEquals(Arrays.asList(
                new ShortPathPart(40, 1, Orientation.HORIZONTAL),
                new ShortPathPart(20, 2, Orientation.VERTICAL)), topPath);

        List<ShortPathPart> bottomPath = getDockingAreaShortPath(BOTTOM);
        assertEquals(Arrays.asList(
                new ShortPathPart(40, 1, Orientation.HORIZONTAL),
                new ShortPathPart(80, 2, Orientation.VERTICAL)), bottomPath);
    }

    private void addDockingArea(int position, String id, Integer... path) {
        dockingPane.addDockingArea(Arrays.asList(path), new DockingAreaPane(id, position, false));

        DockablePane dockablePane = new DockablePane();
        dockingPane.addDockable(id, new PositionableAdapter<>(dockablePane, 10));
    }

//    private List<Integer> getDockingAreaPath(String dockingAreaId) {
//        DockingAreaPane dockingArea = dockingPane.getDockingArea(dockingAreaId);
//        DockingSplitPane parentSplitPane = dockingArea.getParentSplitPane();
//        List<Integer> path = new ArrayList<>();
//        while (parentSplitPane != null) {
//            path.add(parentSplitPane.getPosition());
//            parentSplitPane = parentSplitPane.getParentSplitPane();
//        }
//        path.remove(path.size() - 1);
//        Collections.reverse(path);
//        return path;
//    }
    private List<ShortPathPart> getDockingAreaShortPath(String dockingAreaId) {
        DockingAreaPane dockingArea = dockingPane.getDockingArea(dockingAreaId);
        return dockingArea.getShortPath();
    }
}
